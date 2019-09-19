package gasAgencyManagement;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void individualUpload(Stage s) throws FileNotFoundException {

		VBox layout = new VBox();

		Label justAdjust = new Label();
		justAdjust.setPrefHeight(50);
		TextField meterNo = new TextField();
		meterNo.setPromptText("Enter meter no");
		TextField areaCode = new TextField();
		areaCode.setPromptText("Enter area code");
		TextField reading = new TextField();
		DatePicker getDate = new DatePicker();
		getDate.setPromptText("Enter due date for the payment");
		getDate.setStyle("-fx-font-size : 20");
		getDate.setPrefSize(700, 100);
		getDate.autosize();
		reading.setPromptText("Enter reading");
		meterNo.setPrefSize(700, 100);
		meterNo.setFont(Font.font(20));
		reading.setPrefSize(700, 100);
		reading.setFont(Font.font(20));
		areaCode.setPrefSize(700, 100);
		areaCode.setFont(Font.font(20));
		Button submit = new Button("Submit");
		submit.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
		submit.setStyle("-fx-background-color : GOLD;-fx-text-fill : #278420");

		submit.setOnAction(e -> {
			justAdjust.setText("" + getDate.getValue());
			Instant instant = Instant.from(getDate.getValue().atStartOfDay(ZoneId.of("GMT")));
			Date utildate = Date.from(instant);
			java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
			// justAdjust.setText(""+sqlDate);
			Transaction fill = new Transaction(utildate);
			try {
				int check = fill.addTransaction(meterNo.getText(), areaCode.getText(), reading.getText(), utildate);
				if (check == 0) {
					justAdjust.setText("*Incorrect Data Entry*");
					justAdjust.setStyle("-fx-text-fill : RED");
					justAdjust.setFont(Font.font(18));
				} else {
					justAdjust.setText("*Upload Successful*");
					justAdjust.setStyle("-fx-text-fill : GREEN");
					justAdjust.setFont(Font.font(18));
				}
			} catch (Exception e1) {
				justAdjust.setText("Wrong");
				e1.printStackTrace();
			}
		});

		layout.setFillWidth(false);
		layout.setSpacing(20);
		layout.getChildren().addAll(justAdjust, meterNo, areaCode, getDate, reading, submit);
		layout.setAlignment(Pos.TOP_CENTER);
		HBox setTopLayout = setTopMenu(s);
		BorderPane l = new BorderPane();
		l.setTop(setTopLayout);
		l.setCenter(layout);
		Scene scene = new Scene(l, 1365, 800);

		s.setScene(scene);

	}

	public static HBox setTopMenu(Stage s) throws FileNotFoundException {

		Button homeB = new Button("Home");
		Button addCustomer = new Button("Add Customer");
		Button upload = new Button("Upload Usage Data");
		Button updateRate = new Button("Update Rates");
		Button viewBill = new Button("View Bills");
		Button logout = new Button("Log Out");

		homeB.setPrefSize(200, 120);
		addCustomer.setPrefSize(200, 120);
		upload.setPrefSize(250, 120);
		updateRate.setPrefSize(200, 120);
		viewBill.setPrefSize(200, 120);
		logout.setPrefSize(150, 120);

		homeB.setFont(Font.font("Georgia", 24));
		homeB.setStyle("-fx-background-color : GOLD");
		addCustomer.setFont(Font.font("Georgia", 24));
		addCustomer.setStyle("-fx-background-color : GOLD");
		upload.setFont(Font.font("Georgia", 24));
		upload.setStyle("-fx-background-color : GOLD");
		updateRate.setFont(Font.font("Georgia", 24));
		updateRate.setStyle("-fx-background-color : GOLD");
		viewBill.setFont(Font.font("Georgia", 24));
		viewBill.setStyle("-fx-background-color : GOLD");
		logout.setFont(Font.font("Georgia", 24));
		logout.setStyle("-fx-background-color : GOLD");

		HBox layout = new HBox();

		OptionWindow obj1 = new OptionWindow();
		addCustomer.setOnAction(e -> {
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
			Scene scene1 = new Scene(l, 1365, 800);
			s.setScene(scene1);
		});

		updateRate.setOnAction(e -> {
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
			Scene scene2 = new Scene(l, 1365, 800);
			s.setScene(scene2);
		});

		upload.setOnAction(e -> {
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
			Scene scene2 = new Scene(l, 1365, 800);
			s.setScene(scene2);
		});

		homeB.setOnAction(e -> {
			HomeWindow obj = new HomeWindow(1);
			try {
				obj.start(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		logout.setOnAction(e -> {
			HomeWindow obj = new HomeWindow();
			try {
				obj.start(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		viewBill.setOnAction(e -> {
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
			Scene scene1 = new Scene(l, 1365, 800);
			s.setScene(scene1);
		});
		Image image = new Image(new FileInputStream("asset/logo.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(120);
		imageView.setFitWidth(160);
		imageView.setPreserveRatio(false);
		layout.getChildren().addAll(imageView, homeB, addCustomer, upload, updateRate, viewBill, logout);
		return layout;
	}

	public void batchUpload(Stage s) throws FileNotFoundException {

		VBox layout = new VBox();
		Label justAdjust = new Label();
		justAdjust.setPrefHeight(50);
		Label label = new Label("Upload Usage Data Excel(.xlsx) File Here");
		label.setStyle("-fx-text-fill: GREEN");
		label.setFont(Font.font(24));
		Label label1 = new Label("Please follow the same format for Excel File");
		label1.setStyle("-fx-text-fill: GREEN");
		label1.setFont(Font.font(24));
		Button browse = new Button("Browse");
		browse.setFont(Font.font(30));
		browse.setStyle("-fx-background-color: GOLD;");
		browse.setPrefSize(200, 40);

		Button uploadIt = new Button("Upload");

		uploadIt.setFont(Font.font(30));
		uploadIt.setStyle("-fx-background-color: GOLD;");
		uploadIt.setPrefSize(200, 40);

		layout.setSpacing(40);
		layout.setFillWidth(false);
		layout.setAlignment(Pos.TOP_CENTER);
		layout.getChildren().addAll(justAdjust, label, browse, label1, uploadIt);

		browse.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showOpenDialog(s);
			System.out.println(file);
			try {
				readExcel(file, uploadIt, label1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		HBox setTopLayout = setTopMenu(s);
		BorderPane l = new BorderPane();
		l.setTop(setTopLayout);
		l.setCenter(layout);
		Scene scene = new Scene(l, 1365, 800);

		s.setScene(scene);

	}

	public void readExcel(File file, Button uploadIt, Label justAdjust) throws Exception {
		uploadIt.setOnAction(e -> {
			InputStream ExcelFileToRead = null;
			try {
				ExcelFileToRead = new FileInputStream(file);
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			XSSFWorkbook wb = null;
			try {
				wb = new XSSFWorkbook(ExcelFileToRead);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Error");
				//e1.printStackTrace();
			}

			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			

			System.out.println("NoROWS : " + sheet.getLastRowNum());

			for (int i = 1; i < sheet.getLastRowNum(); i++) {

				row = sheet.getRow(i);
				String meterno = row.getCell(0).getStringCellValue();
				if(meterno.equals(""))
					break;
				//System.out.println(meterno);
				String reading = row.getCell(1).getStringCellValue();
				///System.out.println(reading);
				String utildate = row.getCell(2).getStringCellValue();
				//System.out.println(utildate);
				String areacode = row.getCell(3).getStringCellValue();
				//System.out.println(areacode);
				System.out.println("Meter : " + meterno + "Reading : " + reading + "DATE : " + utildate + "ARea : " + areacode);
				Date date1 = null;
				try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(utildate);
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}  
				  //System.out.println(sDate1+"\t"+date1);  
				Transaction fill = new Transaction(date1);
				int check;
				try {
					check = fill.addTransaction(meterno, areacode,reading,date1);
					System.out.println(check);
					if (check == 0) {
						justAdjust.setText("*Incorrect Data Entry*");
						justAdjust.setStyle("-fx-text-fill : RED");
						justAdjust.setFont(Font.font(18));
					} else {
						justAdjust.setText("*Upload Successful*");
						justAdjust.setStyle("-fx-text-fill : GREEN");
						justAdjust.setFont(Font.font(18));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

	}

}
