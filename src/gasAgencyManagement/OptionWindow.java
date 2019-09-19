package gasAgencyManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TooltipBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class OptionWindow{
	Stage primaryStage;
	Button op1,op2,op3,op4,op5;
	Label justAdjust;
	static String username="Default";
	OptionWindow paymentObj = null;
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public OptionWindow() {
		
	}
	
	public OptionWindow(String userName) {
		username=userName;
		System.out.println(username);
	}
	
	public void displayOptionWindow(Stage window) throws FileNotFoundException{
		
		primaryStage = window;
		
		op1 = new Button("Add/Remove Customer");
		op2 = new Button("Upload Usage Data");
		op3 = new Button("Create/Update Rates");
		op4 = new Button("View Customers' Bills");
		op5 = new Button("Pay Customers' Bills");
		justAdjust = new Label("");
		justAdjust.setPrefHeight(50);
		
		setAllStyle();
		
		op1.setOnAction(e->{
			VBox layoutOp1 = setOp1();
			layoutOp1.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutOp1);
			Scene s = new Scene(l,1365,800);
			primaryStage.setScene(s);
		});
		
		op2.setOnAction(e->{
			VBox layoutUpload = setOp2(primaryStage);
			layoutUpload.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutUpload);
			Scene scene2 = new Scene(l,1365,800);
			primaryStage.setScene(scene2);
		});
		
		op3.setOnAction(e->{
			VBox layoutOp3 = setOp3();
			layoutOp3.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutOp3);
			Scene s = new Scene(l,1365,800);
			primaryStage.setScene(s);
		});
		op4.setOnAction(e->{
			ViewBills v = new ViewBills("Admin");
			VBox layoutOp3 = null;
			try {
				layoutOp3 = v.setTop(primaryStage,"bill");
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			layoutOp3.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutOp3);
			
			Scene s = new Scene(l,1365,800);
			primaryStage.setScene(s);
		});
		op5.setOnAction(e->{
			VBox layoutOp5 = setOp5();
			layoutOp5.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutOp5);
			Scene s = new Scene(l,1365,800);
			primaryStage.setScene(s);
		});
		
		
		VBox layoutOp = new VBox();
		layoutOp.setSpacing(60);
		layoutOp.setAlignment(Pos.TOP_CENTER);
		layoutOp.getChildren().addAll(justAdjust,op1,op2,op3,op4,op5);
		
		
		HBox layout = setTopMenu(primaryStage);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setTop(layout);
		mainLayout.setCenter(layoutOp);
		
		
		Scene scene = new Scene(mainLayout,1365,800);
		
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		primaryStage.show();
	}


	
	public VBox setOp5() {
		OptionWindow payObj  = new OptionWindow();
		String justAdjustText = "";
		VBox layout = new VBox();
		TextField meterNo = new TextField();
		meterNo.setFont(Font.font(22));
		ComboBox<String> month = new ComboBox<>();
		
		Label justAdjust1 = new Label();
		justAdjust1.setPrefHeight(50);
		justAdjust1.setFont(Font.font(18));
		Button pay = new Button("PAY");
		pay.setPrefSize(500,80);
		pay.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		pay.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		meterNo.setPrefSize(700,40);
		
		month.getItems().addAll("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
		month.setTooltip(new Tooltip("Select the bill month to be paid"));
		month.setValue("Jan");
		month.setStyle("-fx-background-color : GOLD;-fx-font-size : 16");
		month.setPrefSize(700, 40);
		
		meterNo.setPromptText("Enter meter no.");
		
		
		pay.setOnAction(e->{
			try {
				justAdjust1.setText(payObj.checkMonthAndPay(month.getValue(),meterNo.getText()));
				if(justAdjust1.getText().equals("Payment Successful")) {
					justAdjust1.setStyle("-fx-text-fill: GREEN");
				}
				else
					justAdjust1.setStyle("-fx-text-fill: RED");
			} catch (Exception e1) {
				justAdjust1.setText("*Exception and Unsuccessful*");
				justAdjust1.setStyle("-fx-text-fill: RED");
				e1.printStackTrace();
			}
		});
		
		layout.getChildren().addAll(justAdjust1,meterNo,month,pay);
		layout.setSpacing(80);
		layout.setFillWidth(false);
		return layout;
	}

	private String checkMonthAndPay(String monthCheck,String meterCheck) throws Exception {
		String text = ""; 
		int check= 0,index = 0;
		String getMonth = "";
		String passQuery = "SELECT date_format(t.due_date,'%b')  date , t.index FROM transaction_data t WHERE t.meter_no = '" + meterCheck + "' AND t.payment_status=0 ORDER BY t.due_date LIMIT 1;";
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(passQuery);
		while(resultSet.next()) {
			getMonth = resultSet.getString("date");
			index = resultSet.getInt("index");
			if(monthCheck.equals(getMonth)) {
				check=1;
			}
		}
		resultSet.close();
		statement.close();
		connect.close();
		paymentObj = new OptionWindow();
		text = paymentObj.insertPaymentTransaction(check,monthCheck,meterCheck,index);
		return text;
		
		
	}

	private String insertPaymentTransaction(int check,String month,String meterNo,int index) throws Exception {
		String string = "*Payment Unsuccessful*";
		if(check==1) {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");

			String q = String.format("UPDATE transaction_data t SET t.payment_status = 1, t.payment_date = now() ,t.paid_amount = t.amount,t.admin_id = '%s' WHERE t.meter_no = '%s' AND t.index = %d;", username,meterNo,index);
			preparedStatement = connect.prepareStatement(q);
			preparedStatement.executeUpdate();
			preparedStatement.close();
	        connect.close();
	        string = "*Payment Successful*";
		}
			
		return string;	
	}

	public VBox setOp1() {
		VBox layout= new VBox();
		TextField meterNo = new TextField();
		TextField name = new TextField();
		TextField areaCode = new TextField();
		Label justAdjust1 = new Label();
		justAdjust1.setPrefHeight(40);
		Button add = new Button("ADD");
		Button remove = new Button("REMOVE");
		add.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		add.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		remove.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		remove.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		add.setPrefSize(500,80);
		remove.setPrefSize(500,80);
		
		justAdjust1.setPrefHeight(30);
		meterNo.setFont(Font.font(22));
		areaCode.setFont(Font.font(22));
		name.setFont(Font.font(22));
		meterNo.setPrefSize(700,100);
		name.setPrefSize(700,100);
		areaCode.setPrefSize(700,100);
		
		meterNo.setPromptText("Enter meter no.");
		name.setPromptText("Enter name");
		areaCode.setPromptText("Enter area code");
		
		add.setOnAction(e->{
			Add addObj = new Add();
			try {
				addObj.addCustomer(meterNo.getText(),name.getText(),areaCode.getText(),username,"active");
				justAdjust1.setText("*Added Successfully*");
				justAdjust1.setStyle("-fx-text-fill : GREEN");
				justAdjust1.setFont(Font.font(18));
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				justAdjust1.setText("*Customer already exists*");
				justAdjust1.setStyle("-fx-text-fill : RED");
				justAdjust1.setFont(Font.font(18));
				e1.printStackTrace();
			}
		});
		
		remove.setOnAction(e->{
			Add addObj = new Add();
			try {
				int checkIt = addObj.removeCustomer(meterNo.getText(),name.getText(),areaCode.getText(),"Inactive");
			
				if(checkIt == 1) {
					justAdjust1.setText("*Removed Successfully*");
					justAdjust1.setStyle("-fx-text-fill : GREEN");
					justAdjust1.setFont(Font.font(18));
				}
				else {
					justAdjust1.setText("*Customer does not exist*");
					justAdjust1.setStyle("-fx-text-fill : RED");
					justAdjust1.setFont(Font.font(18));
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				justAdjust1.setText("*Customer does not exist*");
				justAdjust1.setStyle("-fx-text-fill : RED");
				justAdjust1.setFont(Font.font(18));
				e1.printStackTrace();
			}
		});
		
		layout.setFillWidth(false);
		layout.setSpacing(20);
		layout.getChildren().addAll(justAdjust1,meterNo,name,areaCode,add,remove);
		
		layout.setSpacing(20);
		return layout;
	}
	
	public VBox setOp2(Stage s) {
		VBox layout= new VBox();
		
		ToggleGroup buttons = new ToggleGroup();
	    RadioButton button1 = new RadioButton("Individual Upload");
	    button1.setToggleGroup(buttons);
	    button1.setSelected(true);
	    RadioButton button2 = new RadioButton("Batch Upload");
	    button2.setToggleGroup(buttons);
	    Label justAdjust = new Label();
	    justAdjust.setPrefHeight(80);
	    
	    button1.setFont(Font.font(20));
	    button2.setFont(Font.font(20));
	    button1.setPrefSize(200,100);
	    button2.setPrefSize(200,100);
	    
		
		Button submit = new Button("Submit");
		submit.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		submit.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		
		
		ExcelFileReader objExcel = new ExcelFileReader();
		submit.setOnAction(e->{
				if(button1.isSelected()) {
					ExcelFileReader obj = new ExcelFileReader();
					try {
						obj.individualUpload(s);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(button2.isSelected()) {
					ExcelFileReader obj = new ExcelFileReader();
					try {
						obj.batchUpload(s);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			
		});
		
		layout.setFillWidth(false);
		layout.setSpacing(40);
		layout.getChildren().addAll(justAdjust,button1,button2,submit);
		
		
		return layout;
	}
	
	public VBox setOp3() {
		VBox layout = new VBox();
		
		Label justAdjust2 = new Label();
		justAdjust2.setPrefHeight(70);
		justAdjust2.setStyle("-fx-text-fill : RED;");
		justAdjust2.setFont(Font.font(20));
		
		Button update = new Button("UPDATE");
		update.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		update.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		update.setPrefSize(500,80);
		

		ComboBox<String> rateFields = new ComboBox<>();
		rateFields.getItems().addAll("slab1","slab2","slab3","gst");
		rateFields.setTooltip(new Tooltip("Select the rate field to update"));
		rateFields.setValue("slab1");
		rateFields.setStyle("-fx-background-color : GOLD;-fx-font-size : 16");
		rateFields.setPrefSize(700, 40);
		
		TextField newRate = new TextField();
		newRate.setPromptText("Enter new rate");
		newRate.setFont(Font.font(22));
		newRate.setPrefSize(700,40);
		
		rateFields.setOnAction(e->{
			try {
				setLabelForOldRate(justAdjust2,rateFields.getValue());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		update.setOnAction(e->{
			Calendar now = Calendar.getInstance();
			String yearPass = String.format("%d", now.get(Calendar.YEAR));
			String monthPass = String.format("%d", now.get(Calendar.MONTH) + 1);
			
			
			Update updateObj = new Update(yearPass,monthPass,username,newRate.getText(),rateFields.getValue());
			try {
				updateObj.updateRate();
				justAdjust2.setText(justAdjust2.getText() + "\nNew Rate : " + newRate.getText());
			} catch (ClassNotFoundException | SQLException e1) {
				justAdjust2.setText(justAdjust2.getText() + "\nUnsuccessful Updation");
				e1.printStackTrace();
			}
			
			
		});
		
		layout.setFillWidth(false);
		layout.setSpacing(100);
		layout.getChildren().addAll(justAdjust2,rateFields,newRate,update);
		
		return layout;
	}
	
	



	
public Label setLabelForOldRate(Label label,String value) throws SQLException, ClassNotFoundException {
		String rate = "";
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		String passQuery = String.format("SELECT * FROM rate_data;");
		resultSet = statement.executeQuery(passQuery);
		while (resultSet.next()) {
          rate = String.format("%.2f",resultSet.getDouble(String.format("%s",value)));
        }
		resultSet.close();
		statement.close();
		connect.close();
		label.setText("Current Rate : " + rate);
		return label;
	}



	public void setAllStyle() {
		op1.setPrefSize(500,10);
		op2.setPrefSize(500,10);
		op3.setPrefSize(500,10);
		op4.setPrefSize(500,10);
		op5.setPrefSize(500,10);
		op1.setFont(Font.font(20));
		op2.setFont(Font.font(20));
		op3.setFont(Font.font(20));
		op4.setFont(Font.font(20));
		op5.setFont(Font.font(20));
		op1.setOnMouseEntered(e->{
			op1.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLUE");
			op1.setUnderline(true);
		});
		op1.setOnMouseExited(e->{
			op1.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
			op1.setUnderline(false);
		});
		op2.setOnMouseEntered(e->{
			op2.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLUE");
			op2.setUnderline(true);
		});
		op2.setOnMouseExited(e->{
			op2.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
			op2.setUnderline(false);
		});
		op3.setOnMouseEntered(e->{
			op3.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLUE");
			op3.setUnderline(true);
		});
		op3.setOnMouseExited(e->{
			op3.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
			op3.setUnderline(false);
		});
		op4.setOnMouseEntered(e->{
			op4.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLUE");
			op4.setUnderline(true);
		});
		op4.setOnMouseExited(e->{
			op4.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
			op4.setUnderline(false);
		});
		op5.setOnMouseEntered(e->{
			op5.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLUE");
			op5.setUnderline(true);
		});
		op5.setOnMouseExited(e->{
			op5.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
			op5.setUnderline(false);
		});
		op1.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		op2.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		op3.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		op4.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		op5.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		
		
	}
	
	public static HBox setTopMenu(Stage s) throws FileNotFoundException {
	

		Button homeB = new Button("Home");
		Button addCustomer = new Button("Add Customer");
		Button upload = new Button("Upload Usage Data");
		Button updateRate = new Button("Update Rates");
		Button viewBill = new Button("View Bills");
		Button logout = new Button("Log Out");
		
		Button pay = new Button("Pay");
		
		homeB.setPrefSize(150, 120);
		addCustomer.setPrefSize(200, 120);
		upload.setPrefSize(250, 120);
		updateRate.setPrefSize(200, 120);
		viewBill.setPrefSize(150, 120);
		logout.setPrefSize(150, 120);
		pay.setPrefSize(100, 120);
		
		homeB.setFont(Font.font("Georgia",24));
		homeB.setStyle("-fx-background-color : GOLD");
		addCustomer.setFont(Font.font("Georgia",24));
		addCustomer.setStyle("-fx-background-color : GOLD");
		upload.setFont(Font.font("Georgia",24));
		upload.setStyle("-fx-background-color : GOLD");
		updateRate.setFont(Font.font("Georgia",24));
		updateRate.setStyle("-fx-background-color : GOLD");
		viewBill.setFont(Font.font("Georgia",24));
		viewBill.setStyle("-fx-background-color : GOLD");
		logout.setFont(Font.font("Georgia",24));
		logout.setStyle("-fx-background-color : GOLD");
		pay.setFont(Font.font("Georgia",24));
		pay.setStyle("-fx-background-color : GOLD");
		
		HBox layout = new HBox();
	
		OptionWindow obj1 = new OptionWindow();
		addCustomer.setOnAction(e->{
			VBox layAdd = obj1.setOp1();
			layAdd.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layAdd);
			Scene scene1 = new Scene(l,1365,800);
			s.setScene(scene1);
		});
		
		updateRate.setOnAction(e->{
			VBox layoutUpdate = obj1.setOp3();
			layoutUpdate.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutUpdate);
			Scene scene2 = new Scene(l,1365,800);
			s.setScene(scene2);
		});
		
		upload.setOnAction(e->{
			VBox layoutUpload = obj1.setOp2(s);
			layoutUpload.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutUpload);
			Scene scene2 = new Scene(l,1365,800);
			s.setScene(scene2);
		});
		
		homeB.setOnAction(e->{
			HomeWindow obj = new HomeWindow(1);
			try {
				obj.start(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		logout.setOnAction(e->{
			HomeWindow obj = new HomeWindow();
			try {
				obj.start(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		viewBill.setOnAction(e->{
			ViewBills v = new ViewBills("Admin");
			VBox layoutOp3 = null;
			try {
				layoutOp3 = v.setTop(s,"bill");
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			layoutOp3.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutOp3);
			Scene scene1 = new Scene(l,1365,800);
			s.setScene(scene1);
		});
		
		pay.setOnAction(e->{
			VBox layoutOp5 = obj1.setOp5();
			layoutOp5.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutOp5);
			Scene scene = new Scene(l,1365,800);
			s.setScene(scene);
		});
		
		Image image = new Image(new FileInputStream("asset/logo.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(120);
		imageView.setFitWidth(160);
		imageView.setPreserveRatio(false);
		layout.getChildren().addAll(imageView,homeB,addCustomer,upload,updateRate,viewBill,pay,logout);
		return layout;
	} 
	
	
}