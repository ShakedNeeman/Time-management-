package view;

import javafx.scene.control.Button;

public class ButtonHelper extends Button{

	private int buttonClicks = 0;
	private final String GREEN = "-fx-background-color: PALEGREEN;";
	private final String RED = "-fx-background-color: mistyrose;";
	
	public ButtonHelper(String name)
	{
		super(name);
	}
	
	@Override
	public void fire()
	{
		buttonClicks++;
		setStyleOnClick();
		super.fire();
	}
	
	public int getButtonClickCount()
	{
		return buttonClicks;
	}
	
	public void setButtonClicks(int btnClicks)
	{
		buttonClicks = btnClicks;
		setStyleOnClick();
	}
	
	
	private void setStyleOnClick()
	{
		if(buttonClicks%2 == 0)
			setStyle(GREEN);
		else
			setStyle(RED);
	}
}
