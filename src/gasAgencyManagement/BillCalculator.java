package gasAgencyManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillCalculator {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private static float slab1,slab2,slab3,gst;
	
	public void setRates() throws SQLException, Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gas_agency?user=mohit&password=Mohit@2K");
		statement = connect.createStatement();
		resultSet = statement.executeQuery("SELECT * FROM rate_data;");
		while(resultSet.next()) {
			slab1 = resultSet.getFloat("slab1");
			slab2 = resultSet.getFloat("slab2");
			slab3 = resultSet.getFloat("slab3");
			gst = resultSet.getFloat("gst");
			System.out.println(slab1 +" "+ slab2 +" "+ slab3 +" "+  gst );
			
		}
		resultSet.close();
		statement.close();
		connect.close();
	}
	public float billCal(String readingStr1,String readingStr2) throws SQLException, Exception {
		float totalAmt = 0;
		float floatReading1 = Float.parseFloat(readingStr1);
		float floatReading2 = Float.parseFloat(readingStr2);
		if(floatReading2<=floatReading1) {
			return 0;
		}
		float floatReading = floatReading2-floatReading1;
		float dailyConsumption = floatReading/61;
		BillCalculator obj = new BillCalculator();
		obj.setRates();
		System.out.println(slab1 +" "+ slab2 +" "+ slab3 +" "+  gst );
		if(dailyConsumption<=0.6) {
			totalAmt = (float) (dailyConsumption*61*slab1);
		}
		else if(dailyConsumption>=0.61 && dailyConsumption<=1.5) {
			totalAmt = (float) (0.6*61*slab1);
			totalAmt += (1.5-dailyConsumption)*61*slab2;
		}
		else if(dailyConsumption>=1.51) {
			totalAmt = (float) (0.6*61*slab1);
			totalAmt += 0.9*61*slab2;
			totalAmt += (float)(dailyConsumption-1.5)*61*slab3;
		}
		totalAmt = totalAmt + totalAmt*gst/100;
		System.out.println("Bill Amount : " + totalAmt);
		return totalAmt;
	}
}
