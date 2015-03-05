package ru.umeta.harvester.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import timePackage.AlarmStarter;

public class DBAddQuery implements DBProcedure{
	final static String db_connect_string = "jdbc:jtds:sqlserver://localhost:1433";
	final static String dbName = "/HarvestingSchedule";
	final static String dbUser = "QueryLogin";
	final static String dbPssw = "fvpQas26yPAkggCF";
	public static int dbConnect(
			String name,
			String eURL,
			String sURL,
		    String pid,
		    String time,
		    String reg,
		    String uid,
		    String sloc) 
	{
		Connection conn = null;
		ResultSet generatedKeys = null;
		CallableStatement statementInsert = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        int query_id;
	        
	        String que1 = "exec AddQuery ?,?,?,?,?,?,?,?,?;";
		   	statementInsert = conn.prepareCall(que1);
		    statementInsert.setString(1,name);
		    statementInsert.setString(2,eURL);
		    statementInsert.setString(3,sURL);
		    statementInsert.setString(4,pid);
		    statementInsert.setString(5,time);
		    statementInsert.setString(6,reg);
		    statementInsert.setString(7,uid);
		    statementInsert.setString(8,sloc);
		    statementInsert.registerOutParameter(9,java.sql.Types.INTEGER);
	        statementInsert.executeUpdate();
	        query_id = statementInsert.getInt(9);
	        AlarmStarter.run();
	        return query_id;
		} catch (Exception e) {
	    	System.err.println("DBAddQuery. An error has occured");
	    	e.printStackTrace();
	    	return -1;
		}
		finally {
		    if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
		    if (statementInsert != null) try { statementInsert.close(); } catch (SQLException logOrIgnore) {}
		    if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
		}
	}
}
