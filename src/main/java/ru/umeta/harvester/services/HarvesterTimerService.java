package ru.umeta.harvester.services;

import ru.umeta.harvester.db.IStoredProceduresExecutor;
import ru.umeta.harvester.db.StoredProceduresExecutor;
import ru.umeta.harvester.timer.HarvesterTimer;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by k.kosolapov on 11.03.2015.
 */
public class HarvesterTimerService extends HttpServlet implements IHarvesterTimerService {

    private static final long serialVersionUID = -3603543750927440988L;
    private final IStoredProceduresExecutor storedProceduresExecutor;

    public HarvesterTimerService(IStoredProceduresExecutor storedProceduresExecutor) {
        super();
        this.storedProceduresExecutor = storedProceduresExecutor;
    }

    @Override
    public void schedule() {
        HarvesterTimer.INSTANCE.schedule(storedProceduresExecutor.checkNextHarvest(), this);
    }

    @Override
    public Query selectQueryForId(int queryId) {
        return storedProceduresExecutor.selectQueryForId(queryId);
    }

    @Override
    public Protocol selectProtocolForQueryId(int queryId) {
        final Query query = storedProceduresExecutor.selectQueryForId(queryId);
        if (query != null && query.getActive().equals("1")) {
            return storedProceduresExecutor.selectProtocolForId(Integer.parseInt(query.getProtocol_id()));
        }
        return null;
    }

    @Override
    public void finishHarvesting(int scheduleId, int statusId) {
        storedProceduresExecutor.updateScheduleStatus(scheduleId, statusId);
        schedule();
    }

    public void init() throws ServletException {
        schedule();
    }

}
