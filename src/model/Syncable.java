package model;

public interface Syncable {

	void onCanSync(boolean sync);
	void onSync(int startTime);
	void onSync(Preference preference);
	boolean isSyncable();
}
