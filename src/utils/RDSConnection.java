package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class RDSConnection {
	private static final String jdbcUrl = "jdbc:mysql://cs218.cs5ptoqx9ouz.us-west-2.rds.amazonaws.com:3306/cs218?user=root&password=12345678";
    private static Connection conn = null;
    
    public static Connection CreateConnection(){
        try {
            // Create connection to RDS instance
        	Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl);
            
          } catch (Exception ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
          } 
        return conn;
    }
//	public static void main(String arg[]){
//		CreateConnection();
//	}
}