package ru.umeta.harvester.db;

import java.sql.*;

public class StoredProceduresExecutor implements IStoredProceduresExecutor {

    private final static String SQL_DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String SQL_DB_CONNECT_STRING = "jdbc:sqlserver://localhost:1433;";
    private final static String SQL_DB_NAME = "databaseName=HarvestingSchedule";
    private final static String SQL_DB_USER = "QueryLogin";
    private final static String SQL_DB_PASS = "QueryLogin";
    private static final String EXEC_ACTIVATE_QUERY = "EXEC ActivateQuery @qid = ?, @uid = ?";
    private static final String EXEC_SELECT_USER = "EXEC SelectUser @lg = ?";
    public static final String EXEC_ADD_USER = "exec AddUser @lg = ?, @pw = ?";

    private Connection getConnection() throws SQLException {
        return DriverManager
            .getConnection(SQL_DB_CONNECT_STRING + SQL_DB_NAME, SQL_DB_USER, SQL_DB_PASS);
    }

    public StoredProceduresExecutor() throws ClassNotFoundException, SQLException {
        Class.forName(SQL_DRIVER_NAME);
    }

    @Override public int activateQuery(int qid, int uid) {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_ACTIVATE_QUERY);
            statement.setInt(1, qid);
            statement.setInt(2, uid);
            int result = statement.executeUpdate();

            return result == 0 ? 0 : 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override public Boolean selectUser(String login) {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_SELECT_USER);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            Boolean userExists = resultSet.next();

            return userExists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override public Boolean addUser(String login, String password)
    {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_ADD_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            return statement.execute();

        } catch (Exception e) {
            System.err.println("DBAddUser. An error has occured");
            e.printStackTrace();

            return false;
        }
    }
}
