package ru.umeta.harvester.db;

import ru.umeta.harvester.model.HarvesterTask;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;

public interface IStoredProceduresExecutor {

    /**
     * Executes the ActivateQuery SQL stored procedure
     *
     * @param qid Query id
     * @param uid User id
     * @return status of the execution
     */
    int activateQuery(int qid, int uid);

    Boolean selectUser(String login);

    Boolean addUser(String login, String password);

    HarvesterTask checkNextHarvest();

    Query selectQueryForId(int queryId);

    Protocol selectProtocolForId(int protocolId);

    boolean updateScheduleStatus(int scheduleId, int statusId);

    void insertProtocol(Protocol protocol);
}