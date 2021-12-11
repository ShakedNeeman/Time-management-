package model;

public interface Feedback {

	void onSuccess(String success);
	void onError(String error);
}
