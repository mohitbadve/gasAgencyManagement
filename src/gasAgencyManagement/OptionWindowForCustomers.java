package gasAgencyManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OptionWindowForCustomers {
	Stage primaryStage;
	Button op1,op2,op3,op4;
	Label justAdjust;
	static int checkIfAdminLoggedIn=0;
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public OptionWindowForCustomers() {
		
	}
	
	public OptionWindowForCustomers(int x) {
		checkIfAdminLoggedIn = x;
	}
	
	public void displayOptionWindowForCustomers(Stage window) throws FileNotFoundException{
		
		primaryStage = window;
		
		OptionWindowForCustomers obj = new OptionWindowForCustomers();
		
		op1 = new Button("View Current Bill");
		op2 = new Button("View Billing History");
		op3 = new Button("Energy Bill Calculator");
		op4 = new Button("Unregister Self");
		justAdjust = new Label("");
		justAdjust.setPrefHeight(50);
		
		setAllStyle();
		
		op3.setOnAction(e->{
			VBox layoutbill = obj.setbillCalc();
			layoutbill.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutbill);
			Scene scene2 = new Scene(l,1365,800);
			primaryStage.setScene(scene2);
		});
		op1.setOnAction(e->{
			ViewBills v = new ViewBills("Customer");
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
		op4.setOnAction(e->{
			VBox layoutUnregister = setUnregister();
			layoutUnregister.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutUnregister);
			Scene scene2 = new Scene(l,1365,800);
			primaryStage.setScene(scene2);
		});
		op2.setOnAction(e->{
			ViewBills v = new ViewBills("Customer");
			VBox layoutOp3 = null;
			try {
				layoutOp3 = v.setTop(primaryStage,"history");
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
		VBox layoutOp = new VBox();
		layoutOp.setSpacing(80);
		layoutOp.setAlignment(Pos.TOP_CENTER);
		layoutOp.getChildren().addAll(justAdjust,op1,op2,op3,op4);
		
		
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
	
	private VBox setUnregister() {
		VBox centerLayout = new VBox();
		Label justAdjust = new Label();
		justAdjust.setPrefHeight(90);
		justAdjust.setStyle("-fx-text-fill: RED;");
		justAdjust.setFont(Font.font(20));
		TextField comments = new TextField();
		comments.setPromptText("Enter comments (why you want to unregister)");
		TextField meterNo = new TextField();
		meterNo.setPromptText("Enter meter no");
		Button submit = new Button("Submit");
		comments.setPrefSize(700, 100);
		comments.setFont(Font.font(20));
		meterNo.setPrefSize(700, 100);
		meterNo.setFont(Font.font(20));
		submit.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		submit.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		
		submit.setOnAction(e->{
			if(!comments.getText().equals("") && !meterNo.getText().equals("")) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String q = String.format("INSERT INTO unregister_request (comments,meter_no)VALUES('%s','%s');", comments.getText(),meterNo.getText());
				try {
					preparedStatement = connect.prepareStatement(q);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					preparedStatement.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					preparedStatement.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        try {
					connect.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				justAdjust.setText("*Request Has Been Sent To The Company*");
				justAdjust.setStyle("-fx-text-fill: GREEN");
			}
			else {
				justAdjust.setText("*All fields are mandatory*");
				justAdjust.setStyle("-fx-text-fill: RED");
			}
		});
		
		centerLayout.setFillWidth(false);
		centerLayout.setSpacing(30);
		centerLayout.getChildren().addAll(justAdjust,meterNo,comments,submit);
		centerLayout.setAlignment(Pos.TOP_CENTER);
		
		return centerLayout;
			
	}

	public void setAllStyle() {
		op1.setPrefSize(500,50);
		op2.setPrefSize(500,50);
		op3.setPrefSize(500,50);
		op4.setPrefSize(500,50);
		op1.setFont(Font.font(20));
		op2.setFont(Font.font(20));
		op3.setFont(Font.font(20));
		op4.setFont(Font.font(20));
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
		op1.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		op2.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		op3.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
		op4.setStyle("-fx-background-color : GOLD;-fx-text-fill : BLACK");
	
		
		
	}
	
	public static HBox setTopMenu(Stage s) throws FileNotFoundException {
		

		Button homeB = new Button("Home");
		Button viewBill = new Button("View Bill");
		Button history = new Button("Billing History");
		Button billCalc = new Button("Bill Calculator");
		Button unregister = new Button("Unregister Self");
		
		
		homeB.setPrefSize(242, 120);
		viewBill.setPrefSize(242, 120);
		history.setPrefSize(242, 120);
		billCalc.setPrefSize(242, 120);
		unregister.setPrefSize(242, 120);
		
		homeB.setFont(Font.font("Georgia",24));
		homeB.setStyle("-fx-background-color : GOLD");
		viewBill.setFont(Font.font("Georgia",24));
		viewBill.setStyle("-fx-background-color : GOLD");
		history.setFont(Font.font("Georgia",24));
		history.setStyle("-fx-background-color : GOLD");
		billCalc.setFont(Font.font("Georgia",24));
		billCalc.setStyle("-fx-background-color : GOLD");
		unregister.setFont(Font.font("Georgia",24));
		unregister.setStyle("-fx-background-color : GOLD");
		
		HBox layout = new HBox();
	
		OptionWindowForCustomers obj1 = new OptionWindowForCustomers();
		
		billCalc.setOnAction(e->{
			VBox layoutbill = obj1.setbillCalc();
			layoutbill.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutbill);
			Scene scene2 = new Scene(l,1365,800);
			s.setScene(scene2);
		});
		
		viewBill.setOnAction(e->{
			ViewBills v = new ViewBills("Customer");
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
		
		unregister.setOnAction(e->{
			VBox layoutUnregister = obj1.setUnregister();
			layoutUnregister.setAlignment(Pos.TOP_CENTER);
			HBox layoutT = null;
			try {
				layoutT = setTopMenu(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BorderPane l = new BorderPane(); 
			l.setTop(layoutT);
			l.setCenter(layoutUnregister);
			Scene scene2 = new Scene(l,1365,800);
			s.setScene(scene2);
		});
		
		
		
		homeB.setOnAction(e->{
			HomeWindow obj = new HomeWindow(checkIfAdminLoggedIn);
			try {
				obj.start(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		history.setOnAction(e->{
			ViewBills v = new ViewBills("Customer");
			VBox layoutOp3 = null;
			try {
				layoutOp3 = v.setTop(s,"history");
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
			Scene scene = new Scene(l,1365,800);
			s.setScene(scene);
		});
		
		
		Image image = new Image(new FileInputStream("asset/logo.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(120);
		imageView.setFitWidth(160);
		imageView.setPreserveRatio(false);
		layout.getChildren().addAll(imageView,homeB,viewBill,history,billCalc,unregister);
		return layout;
	}

	public VBox setbillCalc() {
		VBox layout = new VBox();
		
		Label justAdjust = new Label();
		justAdjust.setPrefHeight(50);
		justAdjust.setFont(Font.font(20));
		justAdjust.setStyle("-fx-text-fill : RED;");
		TextField meterReading1 = new TextField();
		meterReading1.setPromptText("Enter meter reading of previous month");
		meterReading1.setFont(Font.font(22));
		meterReading1.setPrefSize(700,40);
		
		TextField meterReading2 = new TextField();
		meterReading2.setPromptText("Enter meter reading of current month");
		meterReading2.setFont(Font.font(22));
		meterReading2.setPrefSize(700,40);
		
		Button calc = new Button("Calculate Bill");
		calc.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		calc.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		calc.setPrefSize(500,80);
		calc.setOnAction(e->{
			BillCalculator obj = new BillCalculator();
			float totalAmt = 0;
			try {
				totalAmt = obj.billCal(meterReading1.getText(),meterReading2.getText());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			justAdjust.setText(String.format("%f",totalAmt));
		});
		
		layout.setSpacing(100);
		layout.setFillWidth(false);
		layout.getChildren().addAll(justAdjust,meterReading1,meterReading2,calc);
		
		return layout;
	} 
}
