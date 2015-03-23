package ru.umeta.harvester.db;

import ru.umeta.harvester.model.HarvesterTask;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;

import java.sql.*;

public class StoredProceduresExecutor implements IStoredProceduresExecutor {

    private final static String SQL_DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String SQL_DB_CONNECT_STRING = "jdbc:sqlserver://localhost:1433;";
    private final static String SQL_DB_NAME = "databaseName=HarvestingSchedule";
    private final static String SQL_DB_USER = "QueryLogin";
    private final static String SQL_DB_PASS = "QueryLogin";
    private static final String EXEC_ACTIVATE_QUERY = "EXEC ActivateQuery @qid = ?, @uid = ?";
    private static final String EXEC_SELECT_USER = "EXEC SelectUser @lg = ?";
    private static final String EXEC_ADD_USER = "EXEC AddUser @lg = ?, @pw = ?";
    private static final String EXEC_CHECK_NEXT_SCHEDULE = "EXEC CheckNextSchedule";
    private static final String EXEC_SELECT_QUERY_FOR_ID = "EXEC SelectQueryForId @qid = ?";
    private static final String EXEC_SELECT_PROTOCOL_FOR_ID = "EXEC SelectProtocolForId @pid = ?";

    public StoredProceduresExecutor() throws ClassNotFoundException, SQLException {
        Class.forName(SQL_DRIVER_NAME);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager
            .getConnection(SQL_DB_CONNECT_STRING + SQL_DB_NAME, SQL_DB_USER, SQL_DB_PASS);
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

            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override public Boolean addUser(String login, String password) {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_ADD_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            return statement.execute();

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override public HarvesterTask checkNextHarvest() {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_CHECK_NEXT_SCHEDULE);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            int queryId = resultSet.getInt("query_id");
            int scheduleId = resultSet.getInt("id");
            Date date = resultSet.getDate("datetime");

            if (date == null)
                throw new NullPointerException(
                    "\"" + EXEC_CHECK_NEXT_SCHEDULE + "\"" + " returned null date.");

            return new HarvesterTask(date, scheduleId, queryId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override public Query selectQueryForId(int queryId) {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_SELECT_QUERY_FOR_ID);
            statement.setInt(1, queryId);
            ResultSet resultSet = statement.executeQuery();
            Query query;

            if (resultSet.next()) {
                query = new Query(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),
                    resultSet.getString(9), resultSet.getString(10), resultSet.getString(11));
            } else {
                return null;
            }

            return query;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override public Protocol selectProtocolForId(int protocolId) {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_SELECT_PROTOCOL_FOR_ID);
            statement.setInt(1, protocolId);
            ResultSet resultSet = statement.executeQuery();
            Protocol protocol;

            if (resultSet.next()) {
                protocol = new Protocol(protocolId, resultSet.getString(2));
                protocol.setClass_(resultSet.getString(3));
                protocol.setPath(resultSet.getString(4));
                protocol.setXml(resultSet.getString(5));
            } else {
                return null;
            }
            return protocol;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override public boolean updateScheduleStatus(int scheduleId, int statusId) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement =
                conn.prepareStatement("EXEC UpdateStatusForSchedule @sid = ?, @status = ?");
            statement.setInt(1, scheduleId);
            statement.setInt(2, statusId);
            int result = statement.executeUpdate();
            if (result <= 0) {
                throw new Exception("The status update has not updated anything.");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override public void insertProtocol(Protocol protocol) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement =
                conn.prepareStatement("INSERT INTO Protocol(name, class, path, xml) VALUES(?,?,?,?)");
            statement.setString(1, protocol.getName());
            statement.setString(2, protocol.getClass_());
            statement.setString(3, protocol.getPath());
            statement.setString(4, protocol.getXml());
            int result = statement.executeUpdate();
            if (result <= 0) {
                throw new Exception("The status update has not updated anything.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
