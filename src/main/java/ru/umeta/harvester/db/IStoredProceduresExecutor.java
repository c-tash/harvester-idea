package ru.umeta.harvester.db;

import ru.umeta.harvester.model.HarvesterTask;
import ru.umeta.harvester.model.User;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;
import ru.umeta.harvesting.base.model.ScheduleElement;

import java.util.List;

public interface IStoredProceduresExecutor {

    /**
     * Executes the ActivateQuery SQL stored procedure
     *
     * @param qid Query id
     * @param uid User id
     * @return status of the execution
     */
    boolean activateQuery(int qid, int uid);

    boolean deactivateQuery(int qid, int uid);

    Boolean selectUser(String login);

    Boolean addUser(String login, String password);

    HarvesterTask checkNextHarvest();

    Query selectQueryForId(int queryId);

    Protocol selectProtocolForId(int protocolId);

    boolean updateScheduleStatus(int scheduleId, int statusId);

    void insertProtocol(Protocol protocol);

    User checkPassword(User userWithoutId);

    List<Query> getQueriesForUser(User user);

    List<Protocol> getProtocols();

    boolean checkQueryExistence(Query query, User user);

    Query addQuery(Query query, User user);

    List<ScheduleElement> checkScheduleForQuery(User user, Query query);

    boolean updateQuery(Query query, User user);

    boolean deleteQuery(Integer queryId, Integer userId);
}
