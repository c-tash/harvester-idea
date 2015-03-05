package ru.umeta.harvester.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class StoredProceduresExecutor implements IStoredProceduresExecutor {
	
	private final static String SQL_DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final static String SQL_DB_CONNECT_STRING = "jdbc:sqlserver://localhost:1433;";
	private final static String SQL_DB_NAME = "databaseName=HarvestingSchedule";
	private final static String SQL_DB_USER = "QueryLogin";
	private final static String SQL_DB_PASS = "fvpQas26yPAkggCF";

    @Autowired
	public StoredProceduresExecutor() throws ClassNotFoundException {
		Class.forName(SQL_DRIVER_NAME);
	}
	
	/* (non-Javadoc)
	 * @see ru.umeta.harvesterspring.db.IStoredProceduresExecutor#activateQuery(int, int)
	 */
	@Override
	public int activateQuery(int qid, int uid) 
	{
		try {
	        
	        Connection conn = DriverManager.getConnection(SQL_DB_CONNECT_STRING + SQL_DB_NAME, SQL_DB_USER, SQL_DB_PASS);
	       
	        Statement statement = conn.createStatement();
	        
		   	String que1 = "exec ActivateQuery " + qid + ", " + uid + ";";
		   	int i = statement.executeUpdate(que1);
		   	if (i == 0) {
		   		conn.close();
			   	return 0;
		   	} else {
			   	conn.close();
			   	return 1;
		   	}
	    } 
		catch (Exception e) {
	    	e.printStackTrace();
	    	return -1;
		}
	}
}
