package model;

import java.io.Serializable;

public class Employee2 implements Serializable{

	private String name;
	private int id;
	private int startTime;
	private int endTime;
	private Preference employeePreference;//,companyPreference;
	private EmployeeType type;
	private boolean canWorkFromHome;
	
	public Employee2(String name,int id,int startTime,String saleryType)
	{
		this.name = name;
		this.id = id;
		this.startTime = startTime;
		startTime = 8;
		endTime = 17;
		employeePreference = Preference.defaultStart;
		//companyPreference = Preference.defaultStart;
	//	type = EmployeeType.base;
		canWorkFromHome = false;
		
		switch (saleryType) {
		case "base":
			type = EmployeeType.base;
			break;
		case "hourly":
			type = EmployeeType.hourly;
			break;
		case "baseAndPercentage":
			type = EmployeeType.baseAndPercentage;
			break;
		default:
			break;
		}
	}
	
	/*public void UpdateCompanyPreference(Preference preference)
	{
		companyPreference = preference;
	}*/
	public void UpdateEmployeePreference(Preference preference)
	{
		employeePreference = preference;
	}
	
	
	/*public void UpdateStartTime(int time)
	{
		switch (companyPreference) {
		case defaultStart:
			startTime = 8;
			break;
		case earlyStart:
			startTime = time;
			break;
		case homework:
			startTime = -1;
			break;
		case lateStart:
			startTime = time;
			break;
		default:
			break;
		}
		if(startTime!=-1)
			{
				endTime = startTime + 8;
				if(endTime>=24)
					endTime = endTime - 24;
			}
		else
			endTime = -1;
	}*/
	
	public void setType(EmployeeType type)
	{
		this.type = type;
	}
	
	public EmployeeType getType()
	{
		return type;
	}
	
	public Preference getPreference(boolean employee)
	{
		/*if(employee)
			return employeePreference;*/
		return employeePreference;
		//return companyPreference;
	}
	
	public int getStartTime()
	{
		return startTime;
	}
	
	public boolean getCanWorkFromHome()
	{
		return canWorkFromHome;
	}
	
	public void setCanWorkFromHome(boolean can)
	{
		canWorkFromHome = can;
	}
	
	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}
	
	public String getName()
	{
		return name;
	}
}
