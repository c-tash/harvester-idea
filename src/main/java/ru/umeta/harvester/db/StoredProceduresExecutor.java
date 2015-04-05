package ru.umeta.harvester.db;

import ru.umeta.harvester.model.HarvesterTask;
import ru.umeta.harvester.model.User;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;
import ru.umeta.harvesting.base.model.ScheduleElement;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StoredProceduresExecutor implements IStoredProceduresExecutor {

    private static final String EXEC_SELECT_PROTOCOLS = "EXEC dbo.SelectProtocols";
    private static final String EXEC_SELECT_QUERY_FOR_USER = "EXEC dbo.SelectQueryForUser @uid = ?";
    private static final String EXEC_CHECK_PASS = "EXEC dbo.CheckPass @lg = ?, @pw = ?";
    private static final String INSERT_INTO_PROTOCOL = "INSERT INTO dbo.Protocol(name, class, path, xml) VALUES(?,?,?,?)";
    private static final String EXEC_UPDATE_STATUS_FOR_SCHEDULE = "UPDATE dbo.Schedule SET status_id = ? WHERE id = ?";
    private static final String EXEC_CHECK_QUERY_EXISTENCE =
            "EXEC dbo.CheckQueryExistance @eURL = ?, @sURL = ?, @pid = ?, @time = ?, @reg = ?, @uid = ?, @sloc = ?";
    private final static String SQL_DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String SQL_DB_CONNECT_STRING = "jdbc:sqlserver://localhost:1433;";
    private final static String SQL_DB_NAME = "databaseName=HarvestingSchedule";
    private final static String SQL_DB_USER = "sa";
    private final static String SQL_DB_PASS = "1472";
    private static final String EXEC_ACTIVATE_QUERY = "EXEC dbo.ActivateQuery @qid = ?, @uid = ?";
    private static final String EXEC_DEACTIVATE_QUERY = "EXEC dbo.DeactivateQuery @qid = ?, @uid = ?";
    private static final String EXEC_SELECT_USER = "EXEC dbo.SelectUser @lg = ?";
    private static final String EXEC_ADD_USER = "EXEC dbo.AddUser @lg = ?, @pw = ?";
    private static final String EXEC_CHECK_NEXT_SCHEDULE = "EXEC dbo.CheckNextSchedule";
    private static final String EXEC_SELECT_QUERY_FOR_ID = "EXEC dbo.SelectQueryForId @qid = ?";
    private static final String EXEC_SELECT_PROTOCOL_FOR_ID = "EXEC dbo.SelectProtocolForId @pid = ?";
    public static final String EXEC_ADD_QUERY = "EXEC dbo.AddQuery @eURL = ?, @sURL = ?, @pid = ?, @time = ?, @reg = ?, @uid = ?, @sloc = ?, @name = ? ";

    public StoredProceduresExecutor() throws ClassNotFoundException, SQLException {
        Class.forName(SQL_DRIVER_NAME);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(SQL_DB_CONNECT_STRING + SQL_DB_NAME, SQL_DB_USER, SQL_DB_PASS);
    }

    @Override
    public boolean activateQuery(int qid, int uid) {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_ACTIVATE_QUERY);
            statement.setInt(1, qid);
            statement.setInt(2, uid);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deactivateQuery(int qid, int uid) {
        try (Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement(EXEC_DEACTIVATE_QUERY);
            statement.setInt(1, qid);
            statement.setInt(2, uid);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean selectUser(String login) {
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

    @Override
    public Boolean addUser(String login, String password) {
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

    @Override
    public HarvesterTask checkNextHarvest() {
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

    @Override
    public Query selectQueryForId(int queryId) {
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

    @Override
    public Protocol selectProtocolForId(int protocolId) {
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

    @Override
    public boolean updateScheduleStatus(int scheduleId, int statusId) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(EXEC_UPDATE_STATUS_FOR_SCHEDULE);
            statement.setInt(1, statusId);
            statement.setInt(2, scheduleId);
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

    @Override
    public void insertProtocol(Protocol protocol) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(INSERT_INTO_PROTOCOL);
            statement.setString(1, protocol.getName());
            statement.setString(2, protocol.getClass_());
            statement.setString(3, protocol.getPath());
            statement.setString(4, protocol.getXml());
            int result = statement.executeUpdate();
            if (result <= 0) {
                throw new Exception("The protocol insert has not inserted anything.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User checkPassword(User userWithoutId) {
        try (Connection conn = getConnection()) {
            String login = userWithoutId.getUser();
            String password = userWithoutId.getPassword();

            PreparedStatement statement = conn.prepareStatement(EXEC_CHECK_PASS);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            Boolean userExists = resultSet.next();
            User user = null;
            if (userExists)
                user = new User(login, password, resultSet.getInt("id"));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Query> getQueriesForUser(User user) {
        List<Query> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(EXEC_SELECT_QUERY_FOR_USER);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Query q = new Query(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11));
                list.add(q);
            } else {
                return list;
            }
            while (resultSet.next()) {
                Query q = new Query(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11));
                list.add(q);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    @Override
    public List<Protocol> getProtocols() {
        List<Protocol> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(EXEC_SELECT_PROTOCOLS);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Protocol protocol = new Protocol(resultSet.getInt(1), resultSet.getString(2));
                list.add(protocol);
            } else {
                return null;
            }
            while (resultSet.next()) {
                Protocol protocol = new Protocol(resultSet.getInt(1), resultSet.getString(2));
                list.add(protocol);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    @Override
    public boolean checkQueryExistence(Query query, User user) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(EXEC_CHECK_QUERY_EXISTENCE);
            statement.setString(1, query.getEndURL());
            statement.setString(2, query.getStartURL());
            statement.setInt(3, Integer.parseInt(query.getProtocol_id()));
            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            final String[] ts = query.getTime().split("T");
            String queryTime = ts[0] + " " + ts[1];
            statement.setTimestamp(4, new Timestamp(dateFormat.parse(queryTime).getTime()));
            statement.setInt(5, Integer.parseInt(query.getReg()));
            statement.setInt(6, user.getId());
            statement.setString(7, query.getStruct_loc());
            return statement.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Query addQuery(Query query, User user) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(EXEC_ADD_QUERY);
            final String endURL = query.getEndURL();
            final String startURL = query.getStartURL();
            final int protocolId = Integer.parseInt(query.getProtocol_id());
            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            final String[] ts = query.getTime().split("T");
            String queryTime = ts[0] + " " + ts[1];
            final Timestamp timestamp = new Timestamp(dateFormat.parse(queryTime).getTime());
            final int reg = Integer.parseInt(query.getReg());
            final Integer userId = user.getId();
            final String structLoc = query.getStruct_loc();

            statement.setString(1, endURL);
            statement.setString(2, startURL);
            statement.setInt(3, protocolId);
            statement.setTimestamp(4, timestamp);
            statement.setInt(5, reg);
            statement.setInt(6, userId);
            statement.setString(7, structLoc);
            statement.setString(8, query.getName());
            statement.executeUpdate();

            statement = conn.prepareStatement(EXEC_CHECK_QUERY_EXISTENCE);
            statement.setString(1, endURL);
            statement.setString(2, startURL);
            statement.setInt(3, protocolId);
            statement.setTimestamp(4, timestamp);
            statement.setInt(5, reg);
            statement.setInt(6, userId);
            statement.setString(7, structLoc);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                query.setId(resultSet.getString(1));
                return query;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ScheduleElement> checkScheduleForQuery(User user, Query query) {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("EXEC dbo.CheckScheduleForQuery @qid = ?, @uid = ?");
            statement.setInt(1, Integer.parseInt(query.getId()));
            statement.setInt(2, user.getId());
            ResultSet resultSet = statement.executeQuery();
            List<ScheduleElement> scheduleList = new ArrayList<>();

            if (resultSet.next()) {
                ScheduleElement element = new ScheduleElement(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
                scheduleList.add(element);
            } else {
                conn.close();
                return null;
            }
            while (resultSet.next()) {
                ScheduleElement element = new ScheduleElement(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
                scheduleList.add(element);
            }

            conn.close();
            return scheduleList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
