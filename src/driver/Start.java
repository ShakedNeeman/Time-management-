package driver;


import controller.Controller2;

import controller.IController2;
import javafx.application.Application;
import javafx.stage.Stage;

import model.Company2;
import model.IModel2;
import view.IView;

import view.View2;

public class Start extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		IModel2 model = new Company2();
		IView view = new View2(stage);
		IController2 controller = new Controller2(model,view);
		View2 view1 = (View2)view;
		view1.setListener(controller);
		Company2 company = (Company2)model;
		company.setListener(controller);
	}

}
