package model;

import java.io.Serializable;
import java.util.ArrayList;


public class Role2 implements Changable,Serializable{

	private String roleName;
	private boolean changable;
	private final int d_startTime = 8;
	private final float p_efficiency = 0.2f;
	private final float n_efficiency = -0.2f;
	private float efficiency;
	private int hoursDiff;
	private ArrayList<Employee2>employess;
	private ArrayList<Float>efficiencies;
	private ArrayList<Integer>hoursDifferences;
	private transient Feedback listener;
	private int roleTime;
	
	public Role2(String name)
	{
		changable = true;
		roleName = name;
		efficiency = 0;
		hoursDiff = 0;
		employess = new ArrayList<>();
		efficiencies = new ArrayList<>();
		hoursDifferences = new ArrayList<>();
		roleTime = 8;
	}

	public void setListener(Feedback listener)
	{
		this.listener = listener;
	}
	
	public int getRoleTime()
	{
		return roleTime;
	}
	
	@Override
	public void onCanChange(boolean change) {
		// TODO Auto-generated method stub
		this.changable = change;
	}

	//changes all the employees to the given preference and start time
	@Override
	public void onChange(Preference preference, int startTime) {
		if(changable)
			for(Employee2 emp : employess)
			{
				emp.setStartTime(startTime);
				emp.UpdateEmployeePreference(preference);
			}
	}

	@Override
	public boolean isChangable() {
		// TODO Auto-generated method stub
		return changable;
	}
	
	
	
	public void CreateEmployee(String empName,int id,int startTime,String saleryType)
	{
		employess.add(new Employee2(empName,id,startTime,saleryType));
	}
	
	
	
	
	public String getRoleName()
	{
		return roleName;
	}
	
	private void MultipleProfitCalculator()
	{
		hoursDifferences.clear();
		efficiencies.clear();
		for(Employee2 emp : employess)
		{
			ProfitCalculator(emp);
		}
	}
	
	private void ProfitCalculator(Employee2 employee)
	{
		
		int startTime = employee.getStartTime();
		Preference empPreference = employee.getPreference(true);
		//hoursDiff = Math.abs(d_startTime - startTime);
		//hoursDifferences.add(Math.abs(d_startTime - startTime));
		hoursDifferences.add(Math.abs(roleTime - startTime));
		switch(empPreference)
		{
			case earlyStart:
			{
				if(startTime < roleTime)
				{
					//positive efficiency
					efficiency = p_efficiency;
					efficiencies.add(p_efficiency);
				}
				else
				{
					efficiency = n_efficiency;
					efficiencies.add(n_efficiency);
				}
				break;
			}
			case lateStart:
			{
				if(startTime > roleTime)
				{
					efficiency = p_efficiency;
					efficiencies.add(p_efficiency);
				}
				else
				{
					efficiency = n_efficiency;
					efficiencies.add(n_efficiency);
				}
				break;
			}
			case homework:
			{
				hoursDifferences.set(hoursDifferences.size()-1, 8);
				if(employee.getCanWorkFromHome())
				{
					efficiency = 0.1f;
					efficiencies.add(0.1f);
				}
				else
				{
					efficiency = n_efficiency;
					efficiencies.add(n_efficiency);
				}
				break;
			}
			case defaultStart:
			{
				if(startTime == roleTime)
					{
						efficiency = 0;
						efficiencies.add(0f);
					}
				else
					{
						efficiency = -0.2f;
						efficiencies.add(n_efficiency);
					}
				break;
			}
			default:
				break;
		}
	}
	
	public float getEfficiency()
	{
		MultipleProfitCalculator();
		return efficiency;
	}
	
	
	public int getHourDiff()
	{
		return hoursDiff;
	}
	
	public ArrayList<Float>getEfficiencies()
	{
		MultipleProfitCalculator();
		return efficiencies;
	}
	
	public ArrayList<Integer>getHoursDifference()
	{
		return hoursDifferences;
	}
	
	public ArrayList<Employee2>getEmployees()
	{
		return employess;
	}
	
	
	
	@Override
	public void onChangeEmployeePreference(int roleIndex,int empIndex,Preference pre)
	{
		Employee2 emp = employess.get(empIndex);
		emp.UpdateEmployeePreference(pre);	
	}
	

	@Override
	public void onChangeEmployeeTime(int roleIndex,int empIndex,int time)
	{
		if(changable) {
			Employee2 emp = employess.get(empIndex);
			emp.setStartTime(time);
		}
		else
			listener.onError("Can't change employee\ntime, role " + roleName + "\nisn't changable");
	}

	//changes all the employees preferences at this role
	@Override
	public void onChange(int roleIndex, Preference preference) {
		// TODO Auto-generated method stub
		if(changable) {
			for(Employee2 emp : employess)
			{
				emp.UpdateEmployeePreference(preference);
			}
			listener.onSuccess("Employees preference\nat role " + roleName + "\nwas updated successfully");
		}
		else listener.onError("Can't change employee\npreference, role " + roleName + "\nisn't changable");
	}

	@Override
	public void onChange(int roleIndex, int startTime) {
		// TODO Auto-generated method stub
		if(changable) {
			if(roleIndex == -1)
			{
				for(Employee2 emp : employess)
				{
					emp.setStartTime(startTime);
				}
			}
			else
				roleTime = startTime;
		}
		else
			listener.onError("Can't change employee\n time, role " + roleName + "\nisn't changable");
	}
}
