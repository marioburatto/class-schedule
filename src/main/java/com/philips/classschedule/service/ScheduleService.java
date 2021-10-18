package com.philips.classschedule.service;

import com.philips.classschedule.domain.Schedule;
import com.philips.classschedule.domain.ScheduleEntity;
import com.philips.classschedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Optional<Schedule> findByExample(Schedule example) {
        return scheduleRepository
                .findById(example)
                .map(ScheduleEntity::getSchedule);
    }

    public List<Schedule> listAll() {
        return scheduleRepository
                .findAll()
                .stream()
                .map(ScheduleEntity::getSchedule)
                .collect(Collectors.toList());
    }

    public Schedule create(Schedule schedule) {
        return scheduleRepository
                .save(new ScheduleEntity(schedule))
                .getSchedule();
    }

    public void delete(Schedule schedule) {
        scheduleRepository.deleteById(schedule);
    }
}
