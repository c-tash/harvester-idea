package ru.umeta.harvester.services;

import ru.umeta.harvester.model.User;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;
import ru.umeta.harvesting.base.model.ScheduleElement;

import java.util.List;

public interface IHarvestingManagementService {

    void addProtocol(String name, String className, String path);

    Query addQuery(Query query, User user);

    List<Query> getQueriesForUser(User user);

    List<Protocol> getProtocols();

    public String register(String login, String pw);

    User login(User userWithoutId);

    Query getQueryForId(Integer queryId);

    List<ScheduleElement> getFailedAttemptsForQuery(User user, Query query);

    boolean queryChangeActive(Integer queryId, String active, User user);

    boolean updateQuery(Query query, User user);

    boolean deleteQuery(Integer queryId, User user);
}
