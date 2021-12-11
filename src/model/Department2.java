package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Department2 implements Changable,Syncable,Serializable{

	private ArrayList<Role2>roles;
	private boolean changable;
	private boolean syncable;
	private String departmentName;
	private transient Feedback listener;
	
	public Department2(String name)
	{
		roles = new ArrayList<>();
		changable = true;
		departmentName = name;
		
	}

	public void setListener(Feedback listener)
	{
		this.listener = listener;
	}
	
	@Override
	public void onCanChange(boolean change) {
		// TODO Auto-generated method stub
		this.changable = change;
	}

	
	@Override
	public void onChange(Preference preference, int startTime) {
		if(changable) {
			for(Role2 role : roles)
			{
				role.onChange(preference, startTime);
			}
		}
	}

	@Override
	public void onCanSync(boolean sync) {
		// TODO Auto-generated method stub
		this.syncable = sync;
	}

	//Sync all roles to the given start time
	@Override
	public void onSync(int startTime) {
		// TODO Auto-generated method stub
		if(changable)
		{
			if(syncable)
				for(Role2 role : roles)
				{
					role.onChange(-1, startTime);
				}
			else 
				listener.onError("Can't sync,\ndepartment " + departmentName + "\nisn't syncable");
		}
		else
			listener.onError("Can't sync,\ndepartment " + departmentName + "\nisn't changable");
	}
	
	@Override
	public void onSync(Preference preference) {
		// TODO Auto-generated method stub
		if(changable)
		{
			if(syncable)
				for(Role2 role : roles)
				{
					role.onChange(-1, preference);
				}
			else 
				listener.onError("Can't sync,\ndepartment " + departmentName + "\nisn't syncable");
		}
		else
			listener.onError("Can't sync,\ndepartment " + departmentName + "\nisn't changable");
	}
	public void addNewRole(String roleName,String employeeName,int employeeID,int startTime,String saleryType)
	{
		boolean newRole = true;
		for(Role2 role: roles)
		{
			if(role.getRoleName().equals(roleName))
			{
				role.CreateEmployee(employeeName, employeeID, startTime,saleryType);
				newRole = false;
				break;
			}
		}
		if(newRole) {
		Role2 role = new Role2(roleName);
		role.setListener(new Feedback() {
			
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
		role.CreateEmployee(employeeName, employeeID, startTime,saleryType);
		roles.add(role);
		}
		listener.onSuccess("Role " + roleName + "\nwas added!");
	}
	
	
	public void setDepartmentName(String name)
	{
		this.departmentName = name;
	}
	
	public String getDepartmentName()
	{
		return departmentName;
	}

	@Override
	public boolean isSyncable() {
		// TODO Auto-generated method stub
		return syncable;
	}

	@Override
	public boolean isChangable() {
		// TODO Auto-generated method stub
		return changable;
	}
	
	public float getRoleProfit(int roleIndex)
	{
		Role2 role = roles.get(roleIndex);
		ArrayList<Float>efficiencies = role.getEfficiencies();
		ArrayList<Integer> hours = role.getHoursDifference();
		float profit = 0;
		for(int i = 0;i<hours.size();i++)
		{
			profit = profit + hours.get(i)*efficiencies.get(i);
		}
		return profit;
	}
	
	public float getDepartmentsProfit()
	{
		float depProfit = 0;
		for(Role2 role : roles)
		{
			ArrayList<Float>efficiencies = role.getEfficiencies();
			ArrayList<Integer> hours = role.getHoursDifference();
			float roleProfit = 0;
			for(int i = 0;i<hours.size();i++)
			{
				roleProfit = roleProfit + hours.get(i)*efficiencies.get(i);
			}
			depProfit = depProfit + roleProfit;
		}
		return depProfit;
	}
	
	public ArrayList<Role2> getRoles()
	{
		return roles;
	}
	
	@Override
	public void onChangeEmployeePreference(int roleIndex,int empIndex,Preference preference)
	{
		Role2 role = roles.get(roleIndex);
		role.onChangeEmployeePreference(-1,empIndex, preference);
	}
	
	@Override
	public void onChangeEmployeeTime(int roleIndex,int empIndex,int time)
	{
		if(syncable)
		{
			onSync(time);
		}
		else if(changable) {
		Role2 role = roles.get(roleIndex);
		role.onChangeEmployeeTime(-1,empIndex, time);
		}
		else
			listener.onError("Can't change employee\ntime, department " + departmentName + "\nisn't changable");
	}
///////////////////////////////////////////// changing the employees data at a level of department or role ///////////////////////////////////////////////
	@Override
	public void onChange(int roleIndex, Preference preference) {
		// TODO Auto-generated method stub
		if(roleIndex == -1)//we update all the employees at all the roles at this department
		{
			for(Role2 role : roles)
			{
				role.onChange(-1, preference);
			}
		}
		else
		{
			Role2 role = roles.get(roleIndex);
			role.onChange(-1, preference);//role index doesn't matter here, we ignore it at the role object
		}
		listener.onSuccess("Change to preferences\nwas implemented successfully");
	}

	@Override
	public void onChange(int roleIndex, int startTime) {
		// TODO Auto-generated method stub
		if(changable) {
			if(roleIndex == -1)//we update all the employees at all the roles at this department
			{
				for(Role2 role : roles)
				{
					role.onChange(-1, startTime);
				}
			}
			else
			{
				Role2 role = roles.get(roleIndex);
				role.onChange(-1,startTime);//role index doesn't matter here, we ignore it at the role object
				onSync(startTime);
			}
			listener.onSuccess("Change on start time\nwas implemented successfully");
		}
		else
			listener.onError("Can't change start time\n, department " + departmentName + "\nisn't changable");
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public void RecreateListener()
	{
		for(Role2 role : roles)
		{
			role.setListener(new Feedback() {
				
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
		}
	}

	


}
