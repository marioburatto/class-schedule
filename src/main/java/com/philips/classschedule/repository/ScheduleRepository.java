package com.philips.classschedule.repository;

import com.philips.classschedule.domain.Schedule;
import com.philips.classschedule.domain.ScheduleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Schedule> {
    List<ScheduleEntity> findAll();
}
