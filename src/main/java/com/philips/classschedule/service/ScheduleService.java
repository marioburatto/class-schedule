package com.philips.classschedule.service;

import com.philips.classschedule.domain.Schedule;
import com.philips.classschedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Mono<Schedule> findByExample(Schedule example) {
        return scheduleRepository
                .findOne(example);
    }

    public Flux<Schedule> listAll() {
        return scheduleRepository
                .findAll();
    }

    public Mono<Schedule> create(Schedule schedule) {
        return scheduleRepository
                .save(schedule);
    }

    public Mono<Void> delete(Schedule schedule) {
        return scheduleRepository
                .delete(schedule)
                .flatMap(result -> Mono.empty().then());
    }
}
