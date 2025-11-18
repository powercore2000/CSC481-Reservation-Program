package main.java.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import main.java.database.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ReservationServer {

	public static void main(String[] args) {
		
		 File dbFile = new File("database/reservation_database.db");
	        boolean freshDb = !dbFile.exists();

	        try (Connection conn = DbManager.getConnection()) {
	        	System.out.println("Handling db");
	            if (freshDb) {
	            	DbInitializer.runSqlResource(conn, "schema.sql");
	            	DbInitializer.runSqlResource(conn, "data.sql");
	            }

	            // now run app logic...
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		SpringApplication.run(main.java.backend.ReservationServer.class, args);
	}

}
