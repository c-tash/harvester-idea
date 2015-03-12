package ru.umeta.harvester.services;

import ru.umeta.harvester.db.StoredProceduresExecutor;
import ru.umeta.harvester.timer.HarvesterTimer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by k.kosolapov on 11.03.2015.
 */
public class HarvesterTimerService extends HttpServlet
        implements IHarvesterTimerService {

    private final StoredProceduresExecutor storedProceduresExecutor;

    public HarvesterTimerService(StoredProceduresExecutor storedProceduresExecutor) {
        super();
        this.storedProceduresExecutor = storedProceduresExecutor;
    }

    @Override
    public void schedule() {
        HarvesterTimer.INSTANCE.schedule(storedProceduresExecutor.checkNextHarvest(), this);
    }

    public void init() throws ServletException {
        schedule();
    }
}
