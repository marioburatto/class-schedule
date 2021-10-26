package com.philips.classschedule.repository;

import com.philips.classschedule.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ScheduleRepository {

    @Autowired
    private R2dbcEntityTemplate template;

    public Mono<Schedule> findOne(Schedule example) {
        return template
                .select(Schedule.class)
                .matching(entityQuery(example))
                .first();
    }

    public Flux<Schedule> findAll() {
        return template
                .select(Schedule.class)
                .all();
    }

    public Mono<Long> count() {
        return template
                .count(Query.empty(), Schedule.class);
    }

    public Mono<Schedule> save(Schedule entity) {
        return template
                .insert(entity);
    }

    public Mono<Integer> delete(Schedule example) {
        return template
                .delete(
                        entityQuery(example),
                        Schedule.class);
    }

    public Mono<Integer> deleteAll() {
        return template
                .delete(Schedule.class)
                .all();
    }

    private Query entityQuery(Schedule schedule) {
        return Query.query(
                Criteria.where("semester").is(schedule.getSemester())
                        .and(Criteria.where("year").is(schedule.getYear()))
                        .and(Criteria.where("professor_id").is(schedule.getProfessor().getId()))
                        .and(Criteria.where("course_id").is(schedule.getCourse().getId()))
        );
    }
}
