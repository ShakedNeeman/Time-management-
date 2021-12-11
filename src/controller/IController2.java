package controller;

import java.util.ArrayList;

import model.Employee2;
import model.Preference;

public interface IController2 {
	
	void onRequestDepartments();
	void onRequestDepartmentRole(int departmentIndex);
	void onRequestRoleEmployees(int depIndex,int roleIndex);
	void onRequestRoleProfit(int depIndex,int roleIndex);
	void onRequestDepartmentProfit(int depIndex);
	void onRequestCompanyProfit();
	void onRequestEmployeeProfit(int depIndex,int roleIndex,int empIndex);
	
	void onRecieveDepartments(String[] departments,boolean[] sync,boolean[] change);
	void onRecieveRoles(String[] roles,boolean[] change);
	void onRecieveEmployee(ArrayList<Employee2> employee);
	void onRecieveProfit(String string,float profit);
	
	void onError(String error);
	void onSuccess(String success);
	
	void onSaveNew(String depName,String[]rolesNames,String[] employees,String[] saleryType);
	void onSaveEdit();
	
	void onSync();
	void onCanSync(boolean sync,int depIndex);
	void onCanChange(boolean change,int depIndex,int roleIndex);
	void onChange(int time,int depIndex,int roleIndex);
	void onChange(Preference preference,int depIndex,int roleIndex,int time);
	void onChange(int depIndex,int roleIndex,Preference preference);
	void onChangeCanWorkFromHome(int depIndex,int roleIndex,int empIndex,boolean value);
	void onChangeEmployeePreference(int depIndex,int roleIndex,int empIndex,Preference preference);
	void onChangeEmployeeStartTime(int depIndex,int roleIndex,int empIndex,int startTime);
	
	
	void onSaveFile();
	void onLoadFile();
	
	void onSync(int startTime,int depIndex);
	void onSync(Preference preference,int depIndex);
}
