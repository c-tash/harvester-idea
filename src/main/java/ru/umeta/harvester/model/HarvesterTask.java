package ru.umeta.harvester.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ctash on 08.03.2015.
 */
public class HarvesterTask implements Serializable {

    private static final long serialVersionUID = -7086045561313377427L;

    private Date date;
    private int scheduleId;
    private int queryId;

    public HarvesterTask(Date date, int scheduleId, int queryId) {
        this.date = date;
        this.scheduleId = scheduleId;
        this.queryId = queryId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }
}
