package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import controller.IController2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Employee2;
import model.EmployeeType;
import model.Preference;

public class View2 implements IView{

	private IController2 listener;
	private BorderPane border;
	private final String Company = "Company";
	private final String Departments = "Deparments";
	private final String ProfitProjections = "Profit";
	private final String NEW = "New";
	private final String ROLE_NAME = "role name";
	private final String EMPLOYEE_NAME = "employee name";
	private int selectedDepartment,selectedRole;
	private final String GREEN = "-fx-background-color: PALEGREEN;";
	private final String RED = "-fx-background-color: mistyrose;";
	
	public View2(Stage stage)
	{
		border = new BorderPane();
		border.setStyle("-fx-background-color: CORNFLOWERBLUE;");
		AddTop();
		AddLeft();
		SetCenter();
		Scene scene = new Scene(border);
		EventHandler<WindowEvent> windowEvent = new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				listener.onSaveFile();//saves the file on X button press
			}
		};
		stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST,windowEvent );
		stage.setScene(scene);
        stage.setTitle("Emulator");
        stage.show();
        
        ShowFileDialog();
	}
	
	private void ShowFileDialog()
	{
		Stage fileStage = new Stage();
        fileStage.setTitle("Load file");
        BorderPane fileBorder = new BorderPane();
        Scene fileScene = new Scene(fileBorder);
        HBox hbox = new HBox();
    	hbox.setSpacing(10);
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setStyle("-fx-background-color: #336699;");
		Button yesBtn = new Button("Load");
		yesBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				listener.onLoadFile();
				fileStage.close();
			 }
			 
		});
		Button noBtn = new Button("Don't load");
		noBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				fileStage.close();
			 }
			 
		});
		Label title = new Label("Load saved file?");
		hbox.getChildren().addAll(yesBtn,noBtn);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(title,hbox);
		fileBorder.setTop(vbox);
		fileStage.setScene(fileScene);
		fileStage.show();
		fileStage.setResizable(false);
		fileBorder.setPrefSize(250, 40);
	}
	
	private void AddTop()
	{
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setStyle("-fx-background-color: #336699;");
		Label label = new Label(Company);
		Button companyProfitBtn = new Button(ProfitProjections);
		companyProfitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				listener.onRequestCompanyProfit();
			}
		});
		hbox.getChildren().addAll(label,companyProfitBtn);
		border.setTop(hbox);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		border.setPrefSize(size.getWidth(), size.getHeight()-100);
	}
	
	private void AddLeft()
	{
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setStyle("-fx-background-color: CORNFLOWERBLUE; -fx-border-color: black ");
		Label departments = new Label(Departments);
		
		Button showDepartments = new Button(Departments);
		showDepartments.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				SetCenter();
				border.setRight(null);
				listener.onRequestDepartments();
			}
		});
		
		Button addNewBtn = new Button(NEW);
		addNewBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				border.setCenter(null);
				border.setRight(null);
				LoadNewForm();
			}
		});
		vbox.getChildren().addAll(departments,addNewBtn,showDepartments);
		border.setLeft(vbox);
	}
	
	private void SetCenter()
	{
		FlowPane flowPane = new FlowPane();
		VBox departments = new VBox();
		departments.setPadding(new Insets(10));
		departments.setSpacing(8);
		VBox roles = new VBox();
		roles.setPadding(new Insets(10));
		roles.setSpacing(8);
		VBox employees = new VBox();
		employees.setPadding(new Insets(10));
		employees.setSpacing(8);
		flowPane.setHgap(25); 
		FlowPane.setMargin(departments, new Insets(0, 0, 0, 0));
		flowPane.getChildren().addAll(departments,roles,employees);
		border.setCenter(flowPane);
	}


	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		DisplayMessage(error, false);
	}

	@Override
	public void onSuccess(String success) {
		// TODO Auto-generated method stub
		DisplayMessage(success, true);
	}

	private void DisplayMessage(String message,boolean success)
	{
		Label label = new Label(message);
		label.setWrapText(true);
		Font font = new Font(30);
		label.setFont(font);
		StackPane layout = new StackPane();
		if(success)
			layout.setStyle(GREEN + " -fx-padding: 10;");//greenish background color to indicate success
		else
			layout.setStyle(RED + " -fx-padding: 10;");//reddish background color to indicate error
	    layout.getChildren().setAll(label);
		border.setRight(layout);
	}
	
	@Override
	public void onShowProfit(String prefix,float profit) {
		// TODO Auto-generated method stub
		border.setBottom(null);
		Label profitLabel = new Label(prefix + "" + profit);
		profitLabel.setWrapText(true);
		Font font = new Font(30);
		profitLabel.setFont(font);
		StackPane layout = new StackPane();
		if(profit>=0)
		{
			layout.setStyle("-fx-background-color: PALEGREEN; -fx-padding: 10;");//greenish background color to indicate success
		}
		else
		{
			layout.setStyle("-fx-background-color: mistyrose; -fx-padding: 10;");//reddish background color to indicate error
		}
		layout.getChildren().add(profitLabel);
		border.setBottom(layout);
	}

	@Override
	public void onShowEmployee(ArrayList<Employee2> employees) {
		// TODO Auto-generated method stub
		FlowPane flow = (FlowPane) border.getCenter();
		VBox roles = (VBox) flow.getChildren().get(2);
		roles.getChildren().clear();
		Text depName = new Text("All the employees (" + employees.size() + ")");
		roles.getChildren().add(depName);
		GridHelper grid = new GridHelper();
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setPadding(new Insets(10,10,10,10));
		
		for(int i = 0; i < employees.size(); i++)
		{
			Employee2 emp = employees.get(i);
			Label empName = new Label("name: " + emp.getName());
			Label empPre = new Label("preference");
			ChoiceBox<String>workPreference = new ChoiceBox<String>();
			workPreference.setValue(emp.getPreference(true).name());
			workPreference.setId("empWork_" + i);
			for(int j = 0;j<Preference.values().length;j++)
				workPreference.getItems().add(Preference.values()[j].name());
			workPreference.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					String[] split = workPreference.getId().split("_");
					Preference value = Preference.values()[workPreference.getItems().indexOf(workPreference.getValue())];
					listener.onChangeEmployeePreference(selectedDepartment, selectedRole, Integer.parseInt(split[1]),value);
				}
			});
			ChoiceBox<String>startHour = new ChoiceBox<String>();
			for(int j = 0;j<24;j++)
				startHour.getItems().add(j + "");
			startHour.setId("empHour_" + i);
			startHour.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					String[] split = startHour.getId().split("_");
					String value = startHour.getValue();
					listener.onChangeEmployeeStartTime(selectedDepartment, selectedRole, Integer.parseInt(split[1]), Integer.parseInt(value));
				}
			});
			startHour.setValue(emp.getStartTime() + "");
			Button employeeProfitBtn = new Button(ProfitProjections);
			employeeProfitBtn.setId("empProfit_"+ i);
			employeeProfitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					String split[] = employeeProfitBtn.getId().split("_");
					listener.onRequestEmployeeProfit(selectedDepartment, selectedRole, Integer.parseInt(split[1]));
				}
			});
			CheckBox canWorkFromHome = new CheckBox("can work from home?");
			canWorkFromHome.setSelected(false);
			canWorkFromHome.setId("home_" + i);
			canWorkFromHome.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					String[] split = canWorkFromHome.getId().split("_");
					listener.onChangeCanWorkFromHome(selectedDepartment, selectedRole, Integer.parseInt(split[1]), newValue);
				}
			});
			canWorkFromHome.setSelected(emp.getCanWorkFromHome());
			grid.addRow(i, empName,empPre,workPreference,startHour,employeeProfitBtn,canWorkFromHome);
		}
		roles.getChildren().add(grid);
	}

	public void setListener(IController2 listener)
	{
		this.listener = listener;
	}
	
	private void LoadNewForm()
	{
		BorderPane innerPane = new BorderPane();
		innerPane.setPadding(new Insets(30,10,10,25));
		Text title = new Text("Update Departments");
		title.setFont(new Font(20));
		GridHelper grid = new GridHelper();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		Text departmentName = new Text("Department Name: ");
		TextField departmentNameField = new TextField();
		
		Label roleName = new Label(ROLE_NAME);
		TextField roleField = new TextField();
		roleField.setId("role");
		Label employeeName = new Label(EMPLOYEE_NAME);
		TextField employeeField = new TextField();
		employeeField.setId("employee");
		Button moreRolesBtn = new Button("Another role");
		moreRolesBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				CreateNewRole(grid);
			}
		});
		ChoiceBox<String>saleryType = new ChoiceBox<>();
		for(int i = 0;i<EmployeeType.values().length;i++)
		{
			saleryType.getItems().add(EmployeeType.values()[i].name());
		}
		saleryType.setValue(EmployeeType.base.name());
		Label s_type = new Label("salery type");
		grid.addRow(0, title);
		grid.addRow(1, departmentName,departmentNameField);
		grid.addRow(2, roleName,roleField,employeeName,employeeField,s_type,saleryType);
		grid.addRow(3, moreRolesBtn);
		Button saveBtn = new Button("Save");
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color: DODGERBLUE; -fx-border-color: black");
		sp.setContent(grid);
		innerPane.setCenter(sp);
		innerPane.setBottom(saveBtn);
		border.setCenter(innerPane);
		
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				List<Node> children = grid.getChildren();
				ArrayList<String>roles = new ArrayList<String>();
				ArrayList<String>names = new ArrayList<String>();
				ArrayList<String>salery = new ArrayList<String>();
				for(int i = 3;i<children.size()-1;i++)
				{
					if(children.get(i) instanceof TextField)
					{
						TextField field = (TextField)children.get(i);
						if(field.getId().equals("role"))
							roles.add(field.getText());
						else if(field.getId().equals("employee"))
							names.add(field.getText());
					}
					else if(children.get(i) instanceof ChoiceBox)
					{
						ChoiceBox<String>box = (ChoiceBox<String>)children.get(i);
						salery.add(box.getValue());
					}
				}
				String depName = departmentNameField.getText();
				if(depName.matches("^$"))//regular expression for empty string
					DisplayMessage("department name can't be an empty field", false);
				else
				{
					String[] rol = roles.toArray(new String[0]);
					String[] nam = (String[]) names.toArray(new String[0]);
					String[] sal = salery.toArray(new String[0]);
					boolean match = false;
					for(int i = 0; i <rol.length;i++)
					{
						if(nam[i].matches("^$|[0-9]+"))//regular expression for empty string or just numbers repeating at least 1 time
						{
							DisplayMessage("employee name can't be empty\nor just numbers", false);
							match = true;
						}
						if(rol[i].matches("^$"))//regular expression for empty string
							{
								DisplayMessage("role name cannot be empty", false);
								match = true;
							}
						
					}
					if(!match)
					{
						listener.onSaveNew(departmentNameField.getText(), rol, nam,sal);
						LoadNewForm();	//resets the form
					}
				}
			}
		});
	}
	
	private void CreateNewRole(GridHelper grid)
	{
		int rowsCount = grid.getRowsNumber();
		int numberOfChildren = grid.getChildren().size();
		Button moreBtn = (Button) grid.getChildren().get(numberOfChildren-1);
		Label roleName = new Label(ROLE_NAME);
		TextField roleField = new TextField();
		roleField.setId("role");
		Label employeeName = new Label(EMPLOYEE_NAME);
		TextField employeeField = new TextField();
		employeeField.setId("employee");
		ChoiceBox<String>saleryType = new ChoiceBox<>();
		for(int i = 0;i<EmployeeType.values().length;i++)
		{
			saleryType.getItems().add(EmployeeType.values()[i].name());
		}
		saleryType.setValue(EmployeeType.base.name());
		Label s_type = new Label("salery type");
		grid.getChildren().remove(numberOfChildren-1);
		grid.addRow(rowsCount-1, roleName,roleField,employeeName,employeeField,s_type,saleryType);
		grid.addRow(rowsCount, moreBtn);
	}

	
	@Override
	public void onShowDepartments(String[] departmentsNames, boolean[] sync, boolean[] change) {
		// TODO Auto-generated method stub
		FlowPane flow = (FlowPane) border.getCenter();
		VBox departments = (VBox) flow.getChildren().get(0);
		departments.getChildren().clear();
		Text depName = new Text("All the departments (" + departmentsNames.length + ")");
		departments.getChildren().add(depName);
		GridHelper grid = new GridHelper();
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setPadding(new Insets(10,10,10,10));
		
		for(int i = 0; i < departmentsNames.length; i++)
		{
			ChoiceBox<String>workPreference = new ChoiceBox<String>();
			//workPreference.setVisible(false);
			ChoiceBox<String>hours = new ChoiceBox<>();
			//hours.setVisible(false);
			workPreference.setDisable(false);
			hours.setDisable(false);
			ButtonHelper syncBtn = new ButtonHelper("Sync Status");
			Button dep = new Button(departmentsNames[i]);
			dep.setId("department_" + i);
			Tooltip depTooltip = new Tooltip("Departments name");
			dep.setTooltip(depTooltip);
			dep.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					String[] split = dep.getId().split("_");
					selectedDepartment = Integer.parseInt(split[1]);
					listener.onRequestDepartmentRole(selectedDepartment);
				}
			});
			Button profitBtn = new Button(ProfitProjections);
			Tooltip profitTip = new Tooltip("Show the profit for this department");
			profitBtn.setTooltip(profitTip);
			profitBtn.setId("departmentProfit_" + i);
			profitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					String[] split = profitBtn.getId().split("_");
					listener.onRequestDepartmentProfit(Integer.parseInt(split[1]));
				}
			});
			ButtonHelper changeBtn = new ButtonHelper("Change Status");
			changeBtn.setButtonClicks(0);
			changeBtn.setId("change_" + i);
			changeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					String[] split = changeBtn.getId().split("_");
					if(changeBtn.getButtonClickCount()%2 == 1)
						{
							listener.onCanChange(false,Integer.parseInt(split[1]),-1);
							workPreference.setDisable(true);
							hours.setDisable(true);
							syncBtn.setDisable(true);
						}
					else
						{
							listener.onCanChange(true,Integer.parseInt(split[1]),-1);
							workPreference.setDisable(false);
							hours.setDisable(false);
							syncBtn.setDisable(false);
							syncBtn.setButtonClicks(1);
						}
				}
			});
			if(change[i])
				changeBtn.setButtonClicks(0);
			else
				changeBtn.setButtonClicks(1);
			
			Tooltip changeTooltip = new Tooltip("allows the deparment to change hours");
			changeBtn.setTooltip(changeTooltip);
			
			syncBtn.setId("sync_" + i);
	//		syncBtn.setButtonClicks(1);
			syncBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					if(syncBtn.getButtonClickCount()%2 == 1)
					{
						workPreference.setDisable(true);
						hours.setDisable(true);
					}
					else
					{
						workPreference.setDisable(false);
						hours.setDisable(false);
					}
					String[] split = syncBtn.getId().split("_");
					int depIndex = Integer.parseInt(split[1]);
					listener.onCanSync(syncBtn.getButtonClickCount()%2 == 0, depIndex);
				}
			});
			Tooltip syncTool = new Tooltip("set the department as a syncronzied department and syncs to the first user of the first role time");
			syncBtn.setTooltip(syncTool);
			if(sync[i])
				syncBtn.setButtonClicks(0);
			else
				syncBtn.setButtonClicks(1);
			
			workPreference.setId("departmentPreference_" + i);
			for(int j = 0;j<Preference.values().length;j++)
				workPreference.getItems().add(Preference.values()[j].name());
			workPreference.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Preference value = Preference.values()[workPreference.getItems().indexOf(workPreference.getValue())];
					String[] split = workPreference.getId().split("_");
					listener.onSync(value,Integer.parseInt(split[1]));
					//listener.onChange(Integer.parseInt(split[1]), -1, value);
				}
			});
			for(int j = 0;j<24;j++)
			{
				hours.getItems().add(j + "");
			}
			Tooltip workTip = new Tooltip("Change all emplpyess at this department work preference");
			workPreference.setTooltip(workTip);
			Tooltip depHours = new Tooltip("Change all employess at this department start time");
			hours.setTooltip(depHours);
			hours.setId("hours_" + i);
			hours.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					String[] split = hours.getId().split("_");
					int time =Integer.parseInt(hours.getValue());
					listener.onSync(time, Integer.parseInt(split[1]));
					//listener.onChange(time,Integer.parseInt(split[1]),-1);
				}
			});
			grid.addRow(i, dep,profitBtn,changeBtn,syncBtn,workPreference,hours);
		}
		departments.getChildren().add(grid);
	}

	@Override
	public void onShowRoles(String[] rolesNames, boolean[] change) {
		// TODO Auto-generated method stub
		FlowPane flow = (FlowPane) border.getCenter();
		VBox roles = (VBox) flow.getChildren().get(1);
		roles.getChildren().clear();
		Text depName = new Text("All the roles (" + rolesNames.length + ")");
		roles.getChildren().add(depName);
		GridHelper grid = new GridHelper();
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setPadding(new Insets(10,10,10,10));
		
		for(int i = 0; i < rolesNames.length; i++)
		{
			Button rol = new Button(rolesNames[i]);
			rol.setId("role_" + i);
			Tooltip depTooltip = new Tooltip("roles name");
			rol.setTooltip(depTooltip);
			rol.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					String[] split = rol.getId().split("_");
					selectedRole = Integer.parseInt(split[1]);
					listener.onRequestRoleEmployees(selectedDepartment, Integer.parseInt(split[1]));
				}
			});
			Button profitBtn = new Button(ProfitProjections);
			Tooltip profitTip = new Tooltip("Show the profit for this role");
			profitBtn.setTooltip(profitTip);
			profitBtn.setId("rolesProfit_" + i);
			profitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					String[] split = profitBtn.getId().split("_");
					listener.onRequestRoleProfit(selectedDepartment, Integer.parseInt(split[1]));
				}
			});
			ButtonHelper changeBtn = new ButtonHelper("Change Status");
			changeBtn.setButtonClicks(0);
			changeBtn.setId("change_" + i);
			changeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					String[] split = changeBtn.getId().split("_");
					if(changeBtn.getButtonClickCount()%2 == 1)
						{
							listener.onCanChange(false,selectedDepartment,Integer.parseInt(split[1]));
						}
					else
						{
							listener.onCanChange(true,selectedDepartment,Integer.parseInt(split[1]));
						}
				}
			});
			if(change[i])
				changeBtn.setButtonClicks(0);
			else 
				changeBtn.setButtonClicks(1);	
			Tooltip changeTooltip = new Tooltip("allows the role to change hours");
			changeBtn.setTooltip(changeTooltip);
			ChoiceBox<String>workPreference = new ChoiceBox<String>();
			
			workPreference.setId("departmentPreference_" + i);
			workPreference.getItems().add(Preference.values()[2].name());
			/*for(int j = 0;j<Preference.values().length;j++)
				workPreference.getItems().add(Preference.values()[j].name());*/
			workPreference.setValue(Preference.values()[2].name());
			workPreference.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Preference value = Preference.values()[workPreference.getItems().indexOf(workPreference.getValue())];
					String[] split = workPreference.getId().split("_");
					listener.onChange(selectedDepartment, Integer.parseInt(split[1]), value);
				}
			});
			
			Tooltip workTip = new Tooltip("Default role preference");
			workPreference.setTooltip(workTip);
			ChoiceBox<String>hours = new ChoiceBox<>();
			for(int j = 0;j<24;j++)
			{
				hours.getItems().add(j + "");
			}
			hours.setId("roleHours_" + i);
			hours.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					String[] split = hours.getId().split("_");
					int time =Integer.parseInt(hours.getValue());
					listener.onChange(time,selectedDepartment,Integer.parseInt(split[1]));
				}
			});
			Tooltip depHours = new Tooltip("the time the role starts working");
			hours.setTooltip(depHours);
			grid.addRow(i, rol,profitBtn,changeBtn,workPreference,hours);
		}
		roles.getChildren().add(grid);
	}
}
