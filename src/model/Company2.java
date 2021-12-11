package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import controller.IController2;

public class Company2 implements IModel2{

	
	private ArrayList<Department2>departments;
	private IController2 listener;
	private int employeeCount;
	
	public Company2()
	{
		departments = new ArrayList<>();
		employeeCount = 0;
	}
	
	public void setListener(IController2 listener)
	{
		this.listener = listener;
	}

	@Override
	public void onRequestDepartments() {
		// TODO Auto-generated method stub
		String[] names = new String[departments.size()];
		boolean[] syncs = new boolean[departments.size()];
		boolean[] changes = new boolean[departments.size()];
		for(int i = 0;i < names.length; i++)
		{
			names[i] = departments.get(i).getDepartmentName();
			syncs[i] = departments.get(i).isSyncable();
			changes[i] = departments.get(i).isChangable();
		}
		listener.onRecieveDepartments(names,syncs,changes);
	}

	@Override
	public void onRequestDepartmentRoles(int depIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		ArrayList<Role2>roles = dep.getRoles();
		String[] roleNames = new String[roles.size()];
		boolean[] changes = new boolean[roles.size()];
		for(int i = 0;i<roleNames.length; i++)
		{
			roleNames[i] = roles.get(i).getRoleName();
			changes[i] = roles.get(i).isChangable();
		}
		listener.onRecieveRoles(roleNames,changes);
	}

	@Override
	public void onRequestRoleEmployees(int depIndex, int roleIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		ArrayList<Role2>roles = dep.getRoles();
		ArrayList<Employee2> emp = roles.get(roleIndex).getEmployees();
		listener.onRecieveEmployee(emp);
	}

	@Override
	public void onCanChange(boolean change, int depIndex, int roleIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		if(roleIndex == -1)
		{
			//only updates the department
			dep.onCanChange(change);
		}
		else
		{
			Role2 role = dep.getRoles().get(roleIndex);
			role.onCanChange(change);
		}
	}

	@Override
	public void onCanSync(boolean sync, int depIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onCanSync(sync);
	}

	@Override
	public void onRequestCompanyProfit() {
		// TODO Auto-generated method stub
		float companyProfit = 0f;
		for(Department2 dep : departments)
		{
			companyProfit = companyProfit +  dep.getDepartmentsProfit();
		}
		listener.onRecieveProfit("The company profit is: ",companyProfit);
	}
	
	@Override
	public void onRequestDepartmentsProfit(int depIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		float profit = dep.getDepartmentsProfit();
		listener.onRecieveProfit("Department " + dep.getDepartmentName() + " profit is: ",profit);
	}

	@Override
	public void onRequsetRoleProfit(int depIndex, int roleIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		float profit = dep.getRoleProfit(roleIndex);
		listener.onRecieveProfit("Role " + dep.getRoles().get(roleIndex).getRoleName() + " profit is: ",profit);
	}
	
	@Override
	public void onRequestEmployeeProfit(int depIndex, int roleIndex, int empIndex) {
		// TODO Auto-generated method stub
		Employee2 emp = departments.get(depIndex).getRoles().get(roleIndex).getEmployees().get(empIndex);
		int roleTime = departments.get(depIndex).getRoles().get(roleIndex).getRoleTime();
		int h_diff = Math.abs(roleTime - emp.getStartTime());
		int startTime = emp.getStartTime();
		float efficiency = 0f,profit = 0f;
		switch (emp.getPreference(true)) {
		case defaultStart:
			if(startTime == roleTime)
			{
				efficiency = 0f;
				
			}
			break;
		case earlyStart:
			if(startTime < roleTime)
			{
				efficiency = 0.2f;
			}
			else
			{
				efficiency = -0.2f;
			}
			break;
		case homework:
		{
			if(emp.getCanWorkFromHome()) {
				efficiency = 0.1f;
			}
			else
			{
				efficiency = -0.2f;
			}
			h_diff = 8;
			break;
		}
		case lateStart:
		{
			if(startTime <= roleTime)
				efficiency =  -0.2f;
			else if(startTime > roleTime)
				efficiency = 0.2f;
		}
		default:
			break;
		}
	
		profit = efficiency*h_diff;
		listener.onRecieveProfit("employee " + emp.getName() + " profit is: ", profit);
		
	}
	
	private void onChangeRole(int depIndex, int roleIndex, Preference preference, int startTime) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		if(dep.isChangable()) {
			if(dep.isSyncable())
			{
				dep.onSync(startTime);
			}
			Role2 role = dep.getRoles().get(roleIndex);
			role.onChange(preference, startTime);
		}
	}

	
	private void onChangeDepartment(int depIndex, Preference preference, int startTime) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onChange(preference, startTime);	
	}

	@Override
	public void onChange(int depIndex, int roleIndex, Preference preference, int startTime) {
		// TODO Auto-generated method stub
		if(roleIndex == -1)//changing at department level
			onChangeDepartment(depIndex, preference, startTime);
		else//changing at role level
			onChangeRole(depIndex, roleIndex, preference, startTime);
	}
	
	@Override
	public void onChange(int depIndex, int roleIndex, Preference preference) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onChange(roleIndex, preference);
	}


	@Override
	public void onChange(int depIndex, int roleIndex, int startTime) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onChange(roleIndex, startTime);
	}
	
	@Override
	public void onSave(String depName, String[] roleName, String[] employeeName, int[] startTime,String[] saleryType) {
		// TODO Auto-generated method stub
		boolean newDep = true;
		for(int i = 0;i<departments.size();i++)
		{
			Department2 dep = departments.get(i);
			if(dep.getDepartmentName().equals(depName))
			{
				addNewRole(dep, roleName, employeeName, startTime,saleryType);
				newDep = false;
			}
		}
		if(newDep)
		{
			Department2 dep = new Department2(depName);
			dep.setListener(new Feedback() {
				
				@Override
				public void onSuccess(String success) {
					// TODO Auto-generated method stub
					listener.onSuccess(success);
				}
				
				@Override
				public void onError(String error) {
					// TODO Auto-generated method stub
					listener.onError(error);
				}
			});
			addNewRole(dep, roleName, employeeName, startTime,saleryType);
			departments.add(dep);
		}
		listener.onSuccess("Saved!");
	}
	
	private void addNewRole(Department2 dep,String[] roleName, String[] employeeName, int[] startTime,String[] saleryType)
	{
		for(int j = 0;j<employeeName.length;j++)
		{
			dep.addNewRole(roleName[j], employeeName[j], employeeCount, startTime[j],saleryType[j]);
			employeeCount++;	
		}
	}

	@Override
	public void onChangeEmployeePreference(int depIndex, int roleIndex, int empIndex, Preference preference) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onChangeEmployeePreference(roleIndex, empIndex, preference);
	}

	@Override
	public void onChangeEmployeeStartTime(int depIndex, int roleIndex, int empIndex, int startTime) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onChangeEmployeeTime(roleIndex, empIndex, startTime);
	}

	@Override
	public void onSaveFile() {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("company.dat"));
			outFile.writeObject(departments);
			outFile.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onLoadFile() {
		// TODO Auto-generated method stub
			try {
				ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("company.dat"));
				departments = (ArrayList<Department2>)inFile.readObject();
				inFile.close();
				RecreateListener();
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				
			}finally {
				if(departments == null)
					departments = new ArrayList<Department2>();
			}
		
	}

	private void RecreateListener()
	{
		for(Department2 dep : departments)
		{
			dep.setListener(new Feedback() {
				
				@Override
				public void onSuccess(String success) {
					// TODO Auto-generated method stub
					listener.onSuccess(success);
				}
				
				@Override
				public void onError(String error) {
					// TODO Auto-generated method stub
					listener.onError(error);
				}
			});
			dep.RecreateListener();
		}
	}

	@Override
	public void onChangeCanWorkFromHome(int depIndex, int roleIndex, int empIndex, boolean value) {
		// TODO Auto-generated method stub
		Employee2 emp = departments.get(depIndex).getRoles().get(roleIndex).getEmployees().get(empIndex);
		emp.setCanWorkFromHome(value);
	}

	@Override
	public void onSync(int startTime,int depIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onSync(startTime);
	}

	@Override
	public void onSync(Preference preference,int depIndex) {
		// TODO Auto-generated method stub
		Department2 dep = departments.get(depIndex);
		dep.onSync(preference);
	}

	





}
