package ru.umeta.harvester.timer;

import ru.umeta.harvester.model.HarvesterTask;
import ru.umeta.harvester.services.IHarvesterTimerService;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Alarm singleton class. INSTANCE is the only object of this class that can be referenced.
 */
public enum HarvesterTimer {
    INSTANCE;

    private final Timer timer = new Timer();

    private int scheduleId = -1;
    private int queryId = -1;
    private HarvesterTimerTask harvesterTimerTask;

    public synchronized void schedule(HarvesterTask harvesterTask, IHarvesterTimerService harvesterTimerService) throws NullPointerException {

        if (harvesterTask == null) {
            System.out.println("There is nothing to harvest");
            return;
        }

        if (harvesterTimerTask != null) {
            harvesterTimerTask.cancel();
            timer.purge();
        }

        this.scheduleId = harvesterTask.getScheduleId();
        this.queryId = harvesterTask.getQueryId();

        final Date date = harvesterTask.getDate();

        harvesterTimerTask = new HarvesterTimerTask(harvesterTimerService);
        System.out.println("------------------------------------");
        System.out.println("Next harvesting is planned on " + date.toString());
        timer.schedule(harvesterTimerTask, date);
    }

    private class HarvesterTimerTask extends TimerTask {

        IHarvesterTimerService harvesterTimerService;

        public HarvesterTimerTask(IHarvesterTimerService harvesterTimerService) {
            this.harvesterTimerService = harvesterTimerService;
        }

        public void run() {
            System.out.println("The delay is " + (System.currentTimeMillis() - scheduledExecutionTime()));
            System.out.println("It's time for harvesting!");
            Harvester nextHarv = new Harvester(scheduleId, queryId);
            new Thread(nextHarv).start();
            System.out.println("Debug message. Alarm");
            harvesterTimerService.schedule();
        }
    }
}
