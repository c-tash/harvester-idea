package ru.umeta.harvester.timer;

import ru.umeta.harvester.model.HarvesterTask;
import ru.umeta.harvester.services.IHarvesterTimerService;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;

import java.text.MessageFormat;
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

    public synchronized void schedule(HarvesterTask harvesterTask,
                                      IHarvesterTimerService harvesterTimerService) throws NullPointerException {
        try {
            if (harvesterTask == null) {
                System.out.println("There is nothing to harvest");
                return;
            }

            if (harvesterTimerTask != null) {
                if (harvesterTask.getScheduleId() == scheduleId) {
                    System.out.println("The task already scheduled.");
                    return;
                }
            }

            this.scheduleId = harvesterTask.getScheduleId();
            this.queryId = harvesterTask.getQueryId();

            final Date date = harvesterTask.getDate();

            harvesterTimerTask = new HarvesterTimerTask(harvesterTimerService);
            System.out.println("------------------------------------");
            System.out.println("Next harvesting is planned on " + date.toString());
            timer.schedule(harvesterTimerTask, date);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class HarvesterTimerTask extends TimerTask {

        private final IHarvesterTimerService harvesterTimerService;

        public HarvesterTimerTask(IHarvesterTimerService harvesterTimerService) {
            this.harvesterTimerService = harvesterTimerService;
        }

        public void run() {
            System.out
                    .println("The delay is " + (System.currentTimeMillis() - scheduledExecutionTime()));
            System.out.println("It's time for harvesting!");
            //Harvester nextHarv = new Harvester(scheduleId, queryId);
            Thread thread = new Thread() {
                public void run() {
                    System.out.print("scheduleId = ");
                    System.out.println(scheduleId);
                    int statusId = 0;
                    final Query query = harvesterTimerService.selectQueryForId(queryId);
                    final Protocol protocol = harvesterTimerService.selectProtocolForQueryId(queryId);
                    if (protocol != null) {
                        try {
                            statusId = ModuleEngine.executeClassMethod(protocol.getPath(), protocol.getClass_(), query);
                            if (statusId > 5) {
                                statusId = 1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print(MessageFormat
                            .format("trying to update schedule with id = {0}, status_id = {1}", scheduleId,
                                    statusId));
                    System.out.println(scheduleId);
                    System.out.print("status_id = ");
                    System.out.println(statusId);
                    harvesterTimerService.finishHarvesting(scheduleId, statusId);
                }
            };
            thread.start();

            System.out.println("Debug message. Alarm");
            harvesterTimerService.schedule();
        }
    }
}
