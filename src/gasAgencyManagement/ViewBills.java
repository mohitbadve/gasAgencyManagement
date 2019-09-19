package gasAgencyManagement;

import java.awt.GridBagConstraints;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.javafx.font.FontFactory;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.font.FontFamily;

public class ViewBills {
	String type;
	HBox topLayout;
	static Label val21,val22;
	static String meter,area;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public ViewBills() {
		
	}
	public ViewBills(String type) {
		this.type = type;
	}
	public ViewBills(String meter,String area) {
		this.meter = meter;
		this.area = area;
	}
	public VBox setTop(Stage s, String string) throws FileNotFoundException {
		
		VBox centerLayout = new VBox();
		Label justAdjust = new Label();
		justAdjust.setPrefHeight(90);
		justAdjust.setStyle("-fx-text-fill: RED;");
		justAdjust.setFont(Font.font(20));
		TextField areaCode = new TextField();
		areaCode.setPromptText("Enter area code");
		TextField meterNo = new TextField();
		meterNo.setPromptText("Enter meter no");
		Button submit = new Button("Submit");
		Button submitHistory = new Button("Submit");
		areaCode.setPrefSize(700, 100);
		areaCode.setFont(Font.font(20));
		meterNo.setPrefSize(700, 100);
		meterNo.setFont(Font.font(20));
		submit.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		submit.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		submitHistory.setFont(Font.font("Georgia",FontWeight.BOLD, 22));
		submitHistory.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");
		
		submit.setOnAction(e->{
			
			ViewBills obj = new ViewBills(meterNo.getText(),areaCode.getText());
			int check = 0;
			try {
				check = obj.authenticate();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(check==1) {
				
				Stage bill = null;
				try {
					justAdjust.setText("");
					//code
					
					bill = showCurrentBill(meterNo.getText(),areaCode.getText());
				} catch (Exception e1) {
					
					justAdjust.setText("*No Records Found*");
					e1.printStackTrace();
				}
				bill.showAndWait();
			}
			else
				justAdjust.setText("*No Records Found*");
		});
		submitHistory.setOnAction(e->{
			
			ViewBills obj = new ViewBills(meterNo.getText(),areaCode.getText());
			int check = 0;
			try {
				check = obj.authenticate();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(check==1) {
				Stage history = null;
				try {
					justAdjust.setText("");
					//code
					
					history = showHistory(meterNo.getText(),areaCode.getText());
				} catch (Exception e1) {
					
					justAdjust.setText("*No Records Found*");
					e1.printStackTrace();
				}
				history.setTitle("Billing History");
				history.showAndWait();
			}
			else
				justAdjust.setText("*No Records Found*");
		});
		centerLayout.setFillWidth(false);
		centerLayout.setSpacing(30);
		if(string.equals("bill"))
			centerLayout.getChildren().addAll(justAdjust,meterNo,areaCode,submit);
		else
			centerLayout.getChildren().addAll(justAdjust,meterNo,areaCode,submitHistory);
		centerLayout.setAlignment(Pos.TOP_CENTER);
		
		return centerLayout;
			
	}
	private Stage showHistory(String meterno, String areacode) throws Exception {
		Stage history = new Stage();
		int counter = 1;
		GridPane layout = new GridPane();
		Label month = new Label("Bill Date");
		Label amount = new Label("Amount");
		Label lastD = new Label("Payment Info");
		month.setFont(Font.font(18));
		amount.setFont(Font.font(18));
		lastD.setFont(Font.font(18));
		month.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;-fx-background-color: GOLD");
		amount.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;-fx-background-color: GOLD");
		lastD.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;-fx-background-color: GOLD");
		month.setPrefWidth(170);
		amount.setPrefWidth(170);
		lastD.setPrefWidth(170);
		month.setFont(Font.font("Georgia", FontWeight.BOLD,18));
		lastD.setFont(Font.font("Georgia", FontWeight.BOLD,18));
		amount.setFont(Font.font("Georgia", FontWeight.BOLD,18));
		String passQuery = 
				"SELECT date_format(due_date,'%d-%b-%Y') duedate,round(amount,2) amount,ifnull(date_format(payment_date,'%d-%b-%Y'),'Not Paid') payment_date FROM transaction_data  WHERE meter_no =" + Integer.parseInt(meterno)+" ORDER BY due_date DESC";
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(passQuery);
		
		while(resultSet.next()) {
			Label label0 = new Label();
			Label label1 = new Label();
			Label label2 = new Label();
			label0.setFont(Font.font(18));
			label1.setFont(Font.font(18));
			label2.setFont(Font.font(18));
			label0.setPrefWidth(170);
			label1.setPrefWidth(170);
			label2.setPrefWidth(170);
			label0.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
			label1.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
			label2.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
			label0.setText(resultSet.getString("duedate"));
			label1.setText(String.valueOf(resultSet.getDouble("amount")));
			label2.setText(resultSet.getString("payment_date"));
			layout.setConstraints(label0,0,counter);
			layout.setConstraints(label1,1,counter);
			layout.setConstraints(label2,2,counter);
			counter++;
			layout.getChildren().addAll(label0,label1,label2);
		}
		
		
		
		layout.setConstraints(month, 0, 0);
		layout.setConstraints(amount, 1, 0);
		layout.setConstraints(lastD, 2, 0);
		layout.setAlignment(Pos.CENTER);
		
		layout.getChildren().addAll(month,amount,lastD);
		history.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(layout,600,600);
		history.setScene(scene);
		return history;
	}
	private int authenticate() throws SQLException, Exception {
		int check = 0;

		String passQuery = String.format(
				"SELECT * FROM customer_data WHERE meter_no = %d AND area_code = '%s' AND status = 'active';",Integer.parseInt(meter),area);
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(passQuery);
		while (resultSet.next()) {
			check++;
		}
		resultSet.close();
		statement.close();
		connect.close();
		
		return check;
		
	}
	public Stage showCurrentBill(String metern,String area) throws Exception {
		Stage bill = new Stage();
		bill.initModality(Modality.APPLICATION_MODAL);
		
		GridPane layout1 = new GridPane();
		Label meterNo = new Label("Meter No ");
		Label areaCode = new Label("Area Code ");
		Label cur = new Label("Current Reading ");
		Label prev = new Label("Previous Reading ");
		layout1.setConstraints(meterNo, 0, 1);
		layout1.setConstraints(areaCode, 0, 2);
		layout1.setConstraints(cur, 0, 3);
		layout1.setConstraints(prev, 0, 4);
		Label val1 = new Label(metern);
		Label val2 = new Label(area);
		Label val3 = new Label(getReadings("Prev"));
		Label val4 = new Label(getReadings("Cur"));
		layout1.setConstraints(val1, 1, 1);
		layout1.setConstraints(val2, 1, 2);
		layout1.setConstraints(val4, 1, 3);
		layout1.setConstraints(val3, 1, 4);
		layout1.getChildren().addAll(meterNo,areaCode,cur,prev,val1,val2,val3,val4);
		
		GridPane layout2 = new GridPane();
		Label energy = new Label("Energy Charges ");
		Label GST = new Label("GST ");
		Label total = new Label("Total Amount ");
		Label round = new Label("Rounded Amount ");
		Label pay = new Label("Pay Before ");
		Label last = new Label("Last Receipt Date ");
		layout2.setConstraints(energy, 0, 1);
		layout2.setConstraints(GST, 0, 2);
		layout2.setConstraints(total, 0, 3);
		layout2.setConstraints(round, 0, 4);
		layout2.setConstraints(pay, 0, 5);
		layout2.setConstraints(last, 0, 6);
		val21 = new Label(getReadings(""));
		val22 = new Label(getReadings("TotalAmt"));
		Label val11 = new Label(getReadings("Amount"));
		
		
		Label val23 = new Label(String.valueOf(Math.round(Double.parseDouble(val22.getText()))));
		Label val31 = new Label(getReadings("PrevD"));
		Label val41 = new Label(getReadings("CurD"));
		layout2.setConstraints(val11, 1, 1);
		layout2.setConstraints(val21, 1, 2);
		layout2.setConstraints(val22, 1, 3);
		layout2.setConstraints(val23, 1, 4);
		layout2.setConstraints(val41, 1, 5);
		layout2.setConstraints(val31, 1, 6);
		
		meterNo.setFont(Font.font(18));
		areaCode.setFont(Font.font(18));
		cur.setFont(Font.font(18));
		prev.setFont(Font.font(18));
		energy.setFont(Font.font(18));
		GST.setFont(Font.font(18));
		total.setFont(Font.font(18));
		round.setFont(Font.font(18));
		pay.setFont(Font.font(18));
		last.setFont(Font.font(18));
		val1.setFont(Font.font(18));
		val2.setFont(Font.font(18));
		val3.setFont(Font.font(18));
		val4.setFont(Font.font(18));
		val11.setFont(Font.font(18));
		val21.setFont(Font.font(18));
		val22.setFont(Font.font(18));
		val23.setFont(Font.font(18));
		val31.setFont(Font.font(18));
		val41.setFont(Font.font(18));
		
		
		meterNo.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		areaCode.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		cur.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		prev.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		energy.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		GST.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		total.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		round.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		pay.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;-fx-text-fill: RED");
		last.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;-fx-text-fill: GREEN");
		val1.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		val2.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		val3.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		val4.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		val11.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		val21.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		val31.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;-fx-text-fill: GREEN");
		val41.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;-fx-text-fill: RED");
		val22.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		val23.setStyle("-fx-border-width : 5px;-fx-border-color: GOLD;");
		
		meterNo.setPrefWidth(170);
		areaCode.setPrefWidth(170);
		cur.setPrefWidth(170);
		prev.setPrefWidth(170);
		energy.setPrefWidth(170);
		GST.setPrefWidth(170);
		total.setPrefWidth(170);
		round.setPrefWidth(170);
		pay.setPrefWidth(170);
		last.setPrefWidth(170);
		val11.setPrefWidth(135);
		val21.setPrefWidth(135);
		val22.setPrefWidth(135);
		val23.setPrefWidth(135);
		val31.setPrefWidth(135);
		val41.setPrefWidth(135);
		val1.setPrefWidth(70);
		val2.setPrefWidth(70);
		val3.setPrefWidth(70);
		val4.setPrefWidth(70);
		meterNo.setAlignment(Pos.CENTER);
		areaCode.setAlignment(Pos.CENTER);
		cur.setAlignment(Pos.CENTER);
		prev.setAlignment(Pos.CENTER);
		energy.setAlignment(Pos.CENTER);
		GST.setAlignment(Pos.CENTER);
		total.setAlignment(Pos.CENTER);
		round.setAlignment(Pos.CENTER);
		pay.setAlignment(Pos.CENTER);
		last.setAlignment(Pos.CENTER);
		val11.setAlignment(Pos.CENTER);
		val21.setAlignment(Pos.CENTER);
		val22.setAlignment(Pos.CENTER);
		val23.setAlignment(Pos.CENTER);
		val31.setAlignment(Pos.CENTER);
		val41.setAlignment(Pos.CENTER);
		val1.setAlignment(Pos.CENTER);
		val2.setAlignment(Pos.CENTER);
		val3.setAlignment(Pos.CENTER);
		val4.setAlignment(Pos.CENTER);
		
		
		
		layout2.getChildren().addAll(energy,GST,total,round,val11,val22,val23,val21);
		
		GridPane layoutBottom = new GridPane();
		layoutBottom.setConstraints(val31, 1, 2);
		layoutBottom.setConstraints(val41, 1, 1);
		layoutBottom.setConstraints(pay, 0, 1);
		layoutBottom.setConstraints(last, 0, 2);
		
		layoutBottom.getChildren().addAll(pay,last,val31,val41);
		
		Image image = new Image(new FileInputStream("asset/e-bill-logo.jpg"));
		
		ImageView imageView = new ImageView(image);
		
		imageView.setFitHeight(200);
		imageView.setFitWidth(250);
		
		imageView.setPreserveRatio(false);
		StackPane layoutTop = new StackPane();
		layoutTop.getChildren().addAll(imageView);
		
		BorderPane layout = new BorderPane();
		layout1.setAlignment(Pos.CENTER_LEFT);
		layout2.setAlignment(Pos.CENTER_RIGHT);
		layoutBottom.setAlignment(Pos.BOTTOM_CENTER);
		layoutTop.setAlignment(Pos.TOP_CENTER);
		layout.setLeft(layout1);
		layout.setRight(layout2);
		layout.setTop(layoutTop);
		layout.setBottom(layoutBottom);
		Scene scene = new Scene(layout,600,600);
		bill.setScene(scene);
		bill.setTitle("Current Bill");
		bill.setResizable(false);
		bill.centerOnScreen();
		return bill;
		
	}
	private String getReadings(String check) throws Exception {
		if(check.equals("")) {
			String rate = "";
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
			statement = connect.createStatement();
			String passQuery1 = String.format("SELECT * FROM rate_data;");
			resultSet = statement.executeQuery(passQuery1);
			while (resultSet.next()) {
	          rate = String.format("%.2f",resultSet.getDouble("gst"));
	        }
			resultSet.close();
			statement.close();
			connect.close();
			//val21.setText(rate);
			return rate;
			//System.out.println(val21.getText());
		}
		String returnVal="";
		if(check.equals("Prev")||check.equals("Cur")) {
			String passQuery = String.format("SELECT * FROM customer_data INNER JOIN transaction_data ON  customer_data.meter_no = transaction_data.meter_no WHERE customer_data.meter_no = %s AND area_code ='%s' ORDER BY due_date DESC LIMIT 0,2;",meter,area);
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
			statement = connect.createStatement();
			resultSet = statement.executeQuery(passQuery);
			if(check.equals("Prev")) {
				while(resultSet.next()) {
					returnVal = String.valueOf(resultSet.getInt("reading"));
				}
			}
			else if(check.equals("Cur")) {
				while(resultSet.next()) {
					return String.valueOf(resultSet.getInt("reading"));
					
				}
			}
			resultSet.close();
			statement.close();
			connect.close();
		}
		if(check.equals("PrevD")||check.equals("CurD")) {
			String passQuery = String.format("SELECT * FROM customer_data INNER JOIN transaction_data ON  customer_data.meter_no = transaction_data.meter_no WHERE customer_data.meter_no = %d AND area_code ='%s' ORDER BY due_date DESC LIMIT 0,2;",Integer.parseInt(meter),area);
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
			statement = connect.createStatement();
			resultSet = statement.executeQuery(passQuery);
			if(check.equals("PrevD")) {
				while(resultSet.next()) {
					
					returnVal = String.valueOf(resultSet.getDate("payment_date"));
				}
			}
			else if(check.equals("CurD")) {
				while(resultSet.next()) {
					
					return String.valueOf(resultSet.getDate("due_date"));
				}
			}
			resultSet.close();
			statement.close();
			connect.close();
		}
		if(check.equals("TotalAmt")) {
			
			
			String passQuery = String.format("SELECT * FROM customer_data INNER JOIN transaction_data ON  customer_data.meter_no = transaction_data.meter_no WHERE customer_data.meter_no = %d AND area_code ='%s';",Integer.parseInt(meter),area);
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
			statement = connect.createStatement();
			resultSet = statement.executeQuery(passQuery);
			while(resultSet.next()) {
				returnVal = String.format("%.2f",resultSet.getDouble("amount"));
			}
			resultSet.close();
			statement.close();
			connect.close();
		}
		if(check.equals("Amount")) {
			
			
			returnVal = String.format("%.2f",(Double.parseDouble(val22.getText())*100/(100+Double.parseDouble(val21.getText()))));		
			
		}
		
		return returnVal;
	}
	
}
	