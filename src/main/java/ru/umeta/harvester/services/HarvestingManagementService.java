package ru.umeta.harvester.services;

import ru.umeta.harvester.db.IStoredProceduresExecutor;
import ru.umeta.harvester.model.User;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;
import ru.umeta.harvesting.base.model.ScheduleElement;

import java.util.List;
//import xml.ErrorMessagesXMLParser;


public class HarvestingManagementService implements IHarvestingManagementService {

    private final IStoredProceduresExecutor storedProceduresExecutor;
    private final IHarvesterTimerService harvesterTimerService;

    public HarvestingManagementService(IStoredProceduresExecutor storedProceduresExecutor, IHarvesterTimerService harvesterTimerService) {
        this.storedProceduresExecutor = storedProceduresExecutor;
        this.harvesterTimerService = harvesterTimerService;
    }

    private boolean validateString(String arg) {
        for (int i = 0; i < arg.length(); ++i) {
            char c = arg.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
                continue;
            else
                return false;
        }
        return true;
    }

    @Override
    public void addProtocol(String name, String className, String path) {
        if (className != null && path != null) {
            final Protocol protocol = new Protocol(-1, name);
            protocol.setClass_(className);
            protocol.setPath(path);
            storedProceduresExecutor.insertProtocol(protocol);
        }
    }

    @Override
    public Query addQuery(Query query, User user) {
        if (!storedProceduresExecutor.checkQueryExistence(query, user)) {
            query = storedProceduresExecutor.addQuery(query, user);
            harvesterTimerService.schedule();
        }
        return null;
    }

    @Override
    public List<Query> getQueriesForUser(User user) {
        return storedProceduresExecutor.getQueriesForUser(user);
    }

    @Override
    public List<Protocol> getProtocols() {
        return storedProceduresExecutor.getProtocols();
    }

    @Override
    public String register(String login, String pw) {
        if (!validateString(login)) {
            return "BAD_INPUT";
        }
        if (!storedProceduresExecutor.selectUser(login)) {
            storedProceduresExecutor.addUser(login, pw);
            return "SUCCESS";
        } else {
            return "ERROR_ADD_USER";
        }
    }

    @Override
    public User login(User userWithoutId) {
        if (!validateString(userWithoutId.getUser())) {
            return null;
        }
        return storedProceduresExecutor.checkPassword(userWithoutId);
    }

    @Override
    public Query getQueryForId(Integer queryId) {
        return storedProceduresExecutor.selectQueryForId(queryId);
    }

    @Override
    public List<ScheduleElement> getFailedAttemptsForQuery(User user, Query query) {
        return storedProceduresExecutor.checkScheduleForQuery(user, query);
    }

    @Override
    public boolean queryChangeActive(Integer queryId, String active, User user) {
        final boolean result;
        if (active.equals("0")) {
            result = storedProceduresExecutor.activateQuery(queryId, user.getId());
            harvesterTimerService.schedule();
        } else {
            result = storedProceduresExecutor.deactivateQuery(queryId, user.getId());
            harvesterTimerService.schedule();
        }
        return result;
    }

    @Override
    public boolean updateQuery(Query query, User user) {
        final boolean result;
        result = storedProceduresExecutor.updateQuery(query, user);
        harvesterTimerService.schedule();
        return result;
    }

    @Override
    public boolean deleteQuery(Integer queryId, User user) {
        final boolean result = storedProceduresExecutor.deleteQuery(queryId, user.getId());
        harvesterTimerService.schedule();
        return result;
    }


}
