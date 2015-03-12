package ru.umeta.harvester.db;

import ru.umeta.harvester.model.HarvesterTask;

public interface IStoredProceduresExecutor {

	/**
	 * Executes the ActivateQuery SQL stored procedure
	 * @param qid Query id
	 * @param uid User id
	 * @return status of the execution
	 */
	int activateQuery(int qid, int uid);

    Boolean selectUser(String login);

    Boolean addUser(String login, String password);

    HarvesterTask checkNextHarvest();
}
