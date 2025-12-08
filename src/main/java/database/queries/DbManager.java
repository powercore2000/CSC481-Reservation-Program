package database.queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.javalite.activejdbc.Base;

public class DbManager {
	
    public static final String JDBC_URL = "jdbc:sqlite:./database/reservation_database.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(JDBC_URL);

        // Important: enable foreign keys in SQLite
        try (Statement st = conn.createStatement()) {
            
    		st.execute("PRAGMA foreign_keys = ON");
        }

        return conn;
    }
    
    public static void openDatabase() {
    	
    	if(Base.hasConnection())
    		return;
    	
		Base.open("org.sqlite.JDBC",DbManager.JDBC_URL, "", "");

		Base.exec("PRAGMA foreign_keys = ON;");
		
		/*System.out.println("====Opened Database====/n");
		
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : stack) {
		    System.out.println(element);
		}
    	*/
    }
    
    public static void closeDatabase() {
    	
        if (Base.hasConnection()) {
            Base.close();
            
            /*System.out.println("====Closed Database====/n");
            
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
    		for (StackTraceElement element : stack) {
    		    System.out.println(element);
    		}
        	*/
        }
       
    }
    
    
}
