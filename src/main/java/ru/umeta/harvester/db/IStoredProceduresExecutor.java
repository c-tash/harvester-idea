package ru.umeta.harvester.db;

public interface IStoredProceduresExecutor {

	/**
	 * Executes the ActivateQuery SQL stored procedure
	 * @param qid Query id
	 * @param uid User id
	 * @return status of the execution
	 */
	public abstract int activateQuery(int qid, int uid);

    Boolean selectUser(String login);

    Boolean addUser(String login, String password);
}
