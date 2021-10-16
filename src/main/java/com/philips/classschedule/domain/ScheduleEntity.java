package com.philips.classschedule.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @EmbeddedId
    public Schedule schedule;

    public ScheduleEntity() {
    }

    public ScheduleEntity(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}

