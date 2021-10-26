package com.philips.classschedule.repository;

import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.domain.projection.ProfessorAndCourseNameProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProfessorRepository extends ReactiveCrudRepository<Professor, Integer> {

    @Query("SELECT p.name as name, array_agg(c.name order by c.name) as courses " +
            "from schedule s " +
            "   join professor p " +
            "       on p.id = s.professor_id " +
            "   join course c " +
            "       on c.id = s.course_id " +
            "group by 1 " +
            "order by 1 ")
    Flux<ProfessorAndCourseNameProjection> listAllProfessorAndCourseNames();
}

