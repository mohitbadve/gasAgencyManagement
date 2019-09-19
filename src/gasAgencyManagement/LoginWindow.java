package gasAgencyManagement;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class LoginWindow{
	Stage primaryStage;
	TextField username, captcha;
	PasswordField password;
	Label captchaCode,justAdjust;
	Button login,changeCaptcha;
	static int count=0;
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	
	public void displayLoginWindow(Stage window) throws FileNotFoundException{
		
		primaryStage = window;
		username = new TextField();
		password = new PasswordField();
		captcha = new TextField();
		captchaCode = new Label();
		justAdjust = new Label();
		justAdjust.setPrefHeight(30);
		username.setPromptText("Enter username");
		password.setPromptText("Enter password");
		captcha.setPromptText("Enter captcha");
		
		String s = captchaCodeGenerator();
		captchaCode.setText(s);
		
		login = new Button("Login");
		changeCaptcha = new Button("↻");
		changeCaptcha.setFont(Font.font(20));
	
		setAllStyle();
		
		login.setOnAction(e->{
			if(captcha.getText().equals(captchaCode.getText())) {
				int check = 0;
				try {
					check = doValidate(username.getText(),password.getText());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(check==1) {
					justAdjust.setText("");
					System.out.println("YesL");	
					System.out.println(username.getText());
					OptionWindow optionObj = new OptionWindow(username.getText());
					try {
						optionObj.displayOptionWindow(window);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//Code
				}
				else {
					System.out.println("NoL");
					justAdjust.setText("*Invalid Credentials*");
					justAdjust.setStyle("-fx-text-fill : RED");
					justAdjust.setFont(Font.font(18));
					captchaCode.setText(captchaCodeGenerator());
				}	
			}
			else {
				System.out.println("No");		
				justAdjust.setText("*Invalid Captcha Code*");
				justAdjust.setStyle("-fx-text-fill : RED");
				justAdjust.setFont(Font.font(18));
				captchaCode.setText(captchaCodeGenerator());
			}
		});
		
		changeCaptcha.setOnAction(e->{
			captchaCode.setText(captchaCodeGenerator());
		});
		
		
		HBox layout = setTopMenu(primaryStage);
		VBox loginLayout= new VBox();
		
		loginLayout.setSpacing(20);
		loginLayout.setAlignment(Pos.TOP_CENTER);
		loginLayout.setFillWidth(false);
		loginLayout.getChildren().addAll(justAdjust,username,password,captcha,captchaCode,changeCaptcha,login);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setTop(layout);
		mainLayout.setCenter(loginLayout);
		
		Scene scene = new Scene(mainLayout,1365,800);
		
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public String captchaCodeGenerator() {
		String code = "";
		Random rand = new Random();
		for (int i = 1; i <= 4; i++) {
			code += String.format("%d", rand.nextInt(9));
		}
		return code;

	}
	
	public void setAllStyle() {
		username.setPrefSize(700,100);
		login.setPrefSize(500,80);
		password.setPrefSize(700,100);
		captcha.setPrefSize(700,100);
		changeCaptcha.setStyle("-fx-color : GOLD");
		login.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		captcha.setPrefHeight(100);
		captcha.setFont(Font.font(22));
		captchaCode.setFont(Font.font(20));
		username.setFont(Font.font(22));
		login.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		password.setFont(Font.font(22));
	}
	
	public static HBox setTopMenu(Stage s) throws FileNotFoundException {
		count++;

		Button homeB = new Button("Home");
		Button adminB = new Button("Admin Zone");
		Button customerB = new Button("Customer Zone");
		homeB.setPrefSize(400, 120);
		adminB.setPrefSize(410, 120);
		customerB.setPrefSize(400, 120);
		homeB.setFont(Font.font("Georgia",24));
		homeB.setStyle("-fx-background-color : GOLD");
		//loginB.setFont(Font.font("Georgia",24));
		//loginB.setStyle("-fx-background-color : GOLD");
		adminB.setFont(Font.font("Georgia",24));
		adminB.setStyle("-fx-background-color : GOLD");
		customerB.setFont(Font.font("Georgia",24));
		customerB.setStyle("-fx-background-color : GOLD");
		HBox layout = new HBox();
	
		adminB.setOnAction(e->{
			LoginWindow loginObj = new LoginWindow();
			try {
				loginObj.displayLoginWindow(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		homeB.setOnAction(e->{
			HomeWindow obj = new HomeWindow();
			try {
				obj.start(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		customerB.setOnAction(e->{
			OptionWindowForCustomers optionObj = new OptionWindowForCustomers();
			try {
				optionObj.displayOptionWindowForCustomers(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		Image image = new Image(new FileInputStream("asset/logo.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(120);
		imageView.setFitWidth(160);
		imageView.setPreserveRatio(false);
		layout.getChildren().addAll(imageView,homeB,adminB,customerB);
		return layout;
	} 
	
	public int doValidate(String userName,String passWord) throws ClassNotFoundException, SQLException {
		int isLogged = 0;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		String passQuery = String.format("SELECT * FROM admin_login_data WHERE username='%s' AND password='%s';", userName ,passWord);
		resultSet = statement.executeQuery(passQuery);
		while (resultSet.next()) {
           /* Integer class_id = resultSet.getInt("class_id");
            Integer no_students = resultSet.getInt("no_students");
            Integer id = resultSet.getInt("id");
            System.out.println("class_id: " + class_id);
            System.out.println("no_students: " + no_students);
            System.out.println("id: " + id);
            System.out.println("---------------------------------");*/
            isLogged++;
        }
		resultSet.close();
		statement.close();
		connect.close();
		return isLogged;
	}
}
