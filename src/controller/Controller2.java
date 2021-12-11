package controller;

import java.util.ArrayList;

import model.Employee2;
import model.IModel2;
import model.Preference;
import view.IView;

public class Controller2 implements IController2{

	private IModel2 model;
	private IView view;
	
	public Controller2(IModel2 model,IView view)
	{
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void onRequestDepartments() {
		// TODO Auto-generated method stub
		model.onRequestDepartments();
	}

	@Override
	public void onRequestDepartmentRole(int departmentIndex) {
		// TODO Auto-generated method stub
		model.onRequestDepartmentRoles(departmentIndex);
	}

	@Override
	public void onRequestRoleEmployees(int depIndex,int roleIndex) {
		// TODO Auto-generated method stub
		model.onRequestRoleEmployees(depIndex, roleIndex);
	}
	
	@Override
	public void onRequestRoleProfit(int depIndex, int roleIndex) {
		// TODO Auto-generated method stub
		model.onRequsetRoleProfit(depIndex, roleIndex);
	}

	@Override
	public void onRequestDepartmentProfit(int depIndex) {
		// TODO Auto-generated method stub
		model.onRequestDepartmentsProfit(depIndex);
	}
	
	@Override
	public void onRecieveDepartments(String[] departments,boolean[] sync,boolean[] change) {
		// TODO Auto-generated method stub
		//view.onReciveDepartments(departments);
		view.onShowDepartments(departments, sync, change);
	}

	@Override
	public void onRecieveRoles(String[] roles,boolean[] change) {
		// TODO Auto-generated method stub
		view.onShowRoles(roles, change);
	}

	@Override
	public void onRecieveEmployee(ArrayList<Employee2> employees) {
		// TODO Auto-generated method stub
		view.onShowEmployee(employees);
	}

	@Override
	public void onRecieveProfit(String prefix,float profit) {
		// TODO Auto-generated method stub
		view.onShowProfit(prefix,profit);
	}

	

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		view.onError(error);
	}

	@Override
	public void onSuccess(String success) {
		// TODO Auto-generated method stub
		view.onSuccess(success);
	}

	@Override
	public void onSaveNew(String depName, String[] rolesNames, String[] employees,String[] saleryType) {
		// TODO Auto-generated method stub
		int[] startTimes = new int[employees.length];
		for(int i = 0;i<startTimes.length;i++)
			startTimes[i] = 8;
		model.onSave(depName, rolesNames, employees,startTimes,saleryType);
	}

	@Override
	public void onSaveEdit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSync() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCanSync(boolean sync,int depIndex) {
		// TODO Auto-generated method stub
		model.onCanSync(sync, depIndex);
	}

	@Override
	public void onCanChange(boolean change,int depIndex,int roleIndex) {
		// TODO Auto-generated method stub
		model.onCanChange(change, depIndex, roleIndex);
	}

	@Override
	public void onChange(int time,int depIndex,int roleIndex) {
		// TODO Auto-generated method stub
		model.onChange(depIndex, roleIndex, time);
		//model.onChange(depIndex, roleIndex, Preference.defaultStart, time);
	}

	@Override
	public void onChange(Preference preference, int depIndex, int roleIndex,int time) {
		// TODO Auto-generated method stub
		model.onChange(depIndex, roleIndex, preference, time);
	}

	@Override
	public void onChangeEmployeePreference(int depIndex, int roleIndex, int empIndex, Preference preference) {
		// TODO Auto-generated method stub
		model.onChangeEmployeePreference(depIndex, roleIndex, empIndex, preference);
	}

	@Override
	public void onChangeEmployeeStartTime(int depIndex, int roleIndex, int empIndex, int startTime) {
		// TODO Auto-generated method stub
		model.onChangeEmployeeStartTime(depIndex, roleIndex, empIndex, startTime);
	}

	@Override
	public void onSaveFile() {
		// TODO Auto-generated method stub
		model.onSaveFile();
	}

	@Override
	public void onLoadFile() {
		// TODO Auto-generated method stub
		model.onLoadFile();
	}

	@Override
	public void onChange(int depIndex, int roleIndex, Preference preference) {
		// TODO Auto-generated method stub
		model.onChange(depIndex, roleIndex, preference);
	}

	@Override
	public void onRequestCompanyProfit() {
		// TODO Auto-generated method stub
		model.onRequestCompanyProfit();
	}

	@Override
	public void onRequestEmployeeProfit(int depIndex, int roleIndex, int empIndex) {
		// TODO Auto-generated method stub
		model.onRequestEmployeeProfit(depIndex, roleIndex, empIndex);
	}

	@Override
	public void onChangeCanWorkFromHome(int depIndex, int roleIndex, int empIndex, boolean value) {
		// TODO Auto-generated method stub
		model.onChangeCanWorkFromHome(depIndex, roleIndex, empIndex, value);
	}

	@Override
	public void onSync(int startTime,int depIndex) {
		// TODO Auto-generated method stub
		model.onSync(startTime, depIndex);
	}

	@Override
	public void onSync(Preference preference,int depIndex) {
		// TODO Auto-generated method stub
		model.onSync(preference, depIndex);
	}

}
