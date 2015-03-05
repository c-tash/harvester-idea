package ru.umeta.harvester.services;

import ru.umeta.harvesting.base.model.Query;
import ru.umeta.harvesting.base.wrap.ProtocolMessage;
import ru.umeta.harvesting.base.wrap.QueryMessage;
import ru.umeta.harvesting.base.wrap.ScheduleMessage;
import ru.umeta.harvesting.base.wrap.ServiceMessage;
import ru.umeta.harvesting.base.wrap.StringMessage;

public interface IHarvestingManagementService {

	public QueryMessage getQueryInfo(String login, String pw, int qid);

	public ServiceMessage updateQuery(String login, String pw, Query qr);

	public StringMessage addQuery(String login, String pw, Query qr);

	public QueryMessage getQueriesForUser(String login, String pw);

	public ScheduleMessage getFailedAttemptsForQuery(String login, String pw, int qid);

	public ServiceMessage register(String login, String pw);

	public ServiceMessage deleteQuery(String login, String pw, int qid);

	public ServiceMessage activateQuery(String login, String pw, int qid);

	public ServiceMessage deactivateQuery(String login, String pw, int qid);

	public ProtocolMessage getProtocols(String login, String pw);

	public StringMessage getProtocolNameForId(String login, String pw, int pid);

	public ProtocolMessage getProtocolForId(String login, String pw, int pid);

}