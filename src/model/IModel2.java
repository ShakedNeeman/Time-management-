package model;

public interface IModel2 {

	void onRequestDepartments();
	void onRequestDepartmentRoles(int depIndex);
	void onRequestRoleEmployees(int depIndex,int roleIndex);
	
	void onCanChange(boolean change,int depIndex,int roleIndex);
	void onCanSync(boolean sync,int depIndex);
	
	void onRequestCompanyProfit();
	void onRequestDepartmentsProfit(int depIndex);
	void onRequsetRoleProfit(int depIndex,int roleIndex);
	void onRequestEmployeeProfit(int depIndex,int roleIndex,int empIndex);
	
	void onChange(int depIndex,int roleIndex,Preference preference,int startTime);

	void onChange(int depIndex,int roleIndex,Preference preference);
	void onChange(int depIndex,int roleIndex,int startTime);
	
	void onSave(String depName,String[] roleName,String[] employeeName,int[] startTime,String[] saleryType);
	
	void onChangeEmployeePreference(int depIndex,int roleIndex,int empIndex,Preference preference);
	void onChangeEmployeeStartTime(int depIndex,int roleIndex,int empIndex,int startTime);
	void onChangeCanWorkFromHome(int depIndex,int roleIndex,int empIndex,boolean value);
	void onSaveFile();
	void onLoadFile();
	
	void onSync(int startTime,int depIndex);
	void onSync(Preference preference,int depIndex);
}
