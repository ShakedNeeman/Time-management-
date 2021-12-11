package model;

public interface Changable {

	void onCanChange(boolean change);
	void onChange(Preference preference,int startTime);
	void onChange(int roleIndex,Preference preference);
	void onChange(int roleIndex,int startTime);
	void onChangeEmployeePreference(int roleIndex,int empIndex,Preference preference);
	void onChangeEmployeeTime(int roleIndex,int empIndex,int time);
	boolean isChangable();
}
