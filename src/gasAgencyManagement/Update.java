package gasAgencyManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	String year,month,userName,newRate,rateName;
	
	public Update(String yearPass, String monthPass, String username, String changedRate,String type) {
		year = yearPass;
		month = monthPass;
		userName = username;
		newRate = changedRate;
		rateName = type;
	}
	public void updateRate() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");

		String q = String.format("UPDATE rate_data SET  %s = %.2f, admin_id = '%s', month = %d, year = %d;",rateName,Float.parseFloat(newRate),userName,Integer.parseInt(month),Integer.parseInt(year));
		preparedStatement = connect.prepareStatement(q);
		preparedStatement.executeUpdate();
		preparedStatement.close();
        connect.close();
	}

}
