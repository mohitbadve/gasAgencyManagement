package gasAgencyManagement;

public class DateConvertor {
 
    public static void main(String args[]) {
 
        // contains both date and time information
        java.util.Date utilDate = new java.util.Date();
        System.out.println("Util date in Java : " + utilDate);
 
        // contains only date information without time
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("SQL date in Java : " + sqlDate);
        
     
 
    }
 
}


