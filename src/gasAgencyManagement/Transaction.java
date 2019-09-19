package gasAgencyManagement;

import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Transaction {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private java.sql.Date sqlDate;
	
	public Transaction(){
		
	}
	public Transaction(Date utildate){
		sqlDate = new java.sql.Date(utildate.getTime());
		System.out.println(sqlDate);
	}

	public int addTransaction(String meterno, String areaCode, String reading, Date utildate) throws Exception {
		String validate = checkValidCustomer(meterno,areaCode);
		System.out.println(meterno + " " + areaCode);
		if(validate.equals("Wrong")) {
			System.out.println("validate");
			return 0;
		}
		BillCalculator obj = new BillCalculator();
		int readingP = Integer.parseInt(reading);
		String prevReading = getPrevReading(meterno,readingP,utildate);
		if(prevReading.equals("Wrong")) {
			System.out.println("PrevR");
			return 0;
		}
		double amt = obj.billCal(prevReading, reading);	
		if(amt==0) {
			System.out.println("amount");
			return 0;
		}
		System.out.println(sqlDate);
		
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		
		
		preparedStatement = connect.prepareStatement("INSERT INTO transaction_data(meter_no,reading,payment_status,due_date,amount)VALUES(?,?,0,?,?);");
		preparedStatement.setString(1, meterno);
		preparedStatement.setInt(2, readingP);
		preparedStatement.setDate(3, sqlDate);
		preparedStatement.setDouble(4, amt);
		preparedStatement.executeUpdate();
		preparedStatement.close();
        connect.close();
        return 1;
		
	}
	private String checkValidCustomer(String meterno, String areaCode) throws Exception {
		String s="Wrong";
		String passQuery = String.format("SELECT * FROM customer_data WHERE meter_no = '%s' AND area_code='%s' AND status='active';", meterno,areaCode);
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(passQuery);
		while(resultSet.next()) {
			s="Right";
		}
		resultSet.close();
		statement.close();
		connect.close();
		return s;
	}
	private String getPrevReading(String meterno,int readingNow,Date utildateNow) throws Exception {
		String pass = "0";
		java.sql.Date dateNow = new java.sql.Date(utildateNow.getTime());
		String passQuery = String.format("SELECT * FROM transaction_data WHERE meter_no = '%s';", meterno);
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(passQuery);
		while(resultSet.next()) {
			java.sql.Date datePrev = resultSet.getDate("due_date");
			Integer reading = resultSet.getInt("reading");
			pass=String.format("%d",reading);
			if(dateNow.before(datePrev))
				return "Wrong";
				
			if(readingNow<reading)
				return "Wrong";
		}
		resultSet.close();
		statement.close();
		connect.close();
		return pass;
	}
	
}
