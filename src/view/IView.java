package view;

import java.util.ArrayList;


import model.Employee2;


public interface IView {

	//void onReciveDepartments(String[] departmentsNames);
	//void onReciveRoles(String[] rolesNames);
	//void onRecieveEmployee(Employee employee);
	void onError(String error);
	void onSuccess(String success);
	
	void onShowProfit(String prefix,float profit);
	void onShowEmployee(ArrayList<Employee2> employee);
	void onShowDepartments(String[] departmentsNames,boolean[] sync,boolean[] change);
	void onShowRoles(String[] rolesNames,boolean[] change);
}
