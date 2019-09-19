package gasAgencyManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Add {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public void addCustomer(String meterNo, String name, String areaCode, String username,String status) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");

		String q = String.format("INSERT INTO customer_data(meter_no,name,area_code,admin_id,status)VALUES('%s','%s','%s','%s','%s');", meterNo,name,areaCode,username,status);
		preparedStatement = connect.prepareStatement(q);
		preparedStatement.executeUpdate();
		preparedStatement.close();
        connect.close();
       
	}
	public int removeCustomer(String meterNo, String name, String areaCode,String status) throws ClassNotFoundException, SQLException {
		int checkExist = 0;
		String passQuery = String.format("SELECT * FROM customer_data WHERE meter_no = '%s' AND name = '%s' AND area_code = '%s' AND status = 'active';", meterNo,name,areaCode);
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(passQuery);
		while(resultSet.next()) {
			checkExist++;
		}
		resultSet.close();
		statement.close();
		connect.close();
		if(checkExist == 0)
			return 0;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");

		String q = String.format("UPDATE customer_data SET status = 'inactivated' WHERE meter_no = '%s' AND name = '%s' AND area_code = '%s';", meterNo,name,areaCode);
		preparedStatement = connect.prepareStatement(q);
		preparedStatement.executeUpdate();
		preparedStatement.close();
        connect.close();
        return 1;
	}
	public int unregisterSelf(String meterNo,String status) throws ClassNotFoundException, SQLException {
		int checkExist = 0;
		String passQuery = String.format("SELECT * FROM customer_data WHERE meter_no = '%s' AND status = 'active';", meterNo);
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(passQuery);
		while(resultSet.next()) {
			checkExist++;
		}
		resultSet.close();
		statement.close();
		connect.close();
		if(checkExist == 0)
			return 0;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");

		String q = String.format("UPDATE customer_data SET status = 'unregistered' WHERE meter_no = '%s';", meterNo);
		preparedStatement = connect.prepareStatement(q);
		preparedStatement.executeUpdate();
		preparedStatement.close();
        connect.close();
        return 1;
	}
}
