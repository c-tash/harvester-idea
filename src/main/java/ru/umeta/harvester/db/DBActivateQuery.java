package ru.umeta.harvester.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


//Deleting query with specific query_ida

public class DBActivateQuery implements DBProcedure {
	
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "QueryLogin";
	final static String dbPssw = "fvpQas26yPAkggCF";
	public static int dbConnect(int qid, int uid) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBActivateQuery connected");// TODO Убрать в финальной версии, либо переместить вывод в лог
	       
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
	    	System.err.println("DBActivateQuery. An error has occured");// TODO Убрать в финальной версии, либо переместить вывод в лог
	    	e.printStackTrace();
	    	return -1;
		}
	}
}