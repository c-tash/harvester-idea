package ru.umeta.harvester.services;

import ru.umeta.harvesting.base.model.Protocol;

/**
 * Created by ctash on 11.03.2015.
 */
public interface IHarvesterTimerService {
    void schedule();

    Protocol selectProtocolForQueryId(int queryId);

    void finishHarvesting(int scheduleId, int statusId);
}
