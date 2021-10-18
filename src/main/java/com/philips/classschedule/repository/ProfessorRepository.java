package com.philips.classschedule.repository;

import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.domain.projection.ProfessorAndCourseNameProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer> {
    List<Professor> findAll();

    @Query("SELECT s.schedule.professor.name, s.schedule.course.name " +
            "from ScheduleEntity s " +
            "group by 1, 2 " +
            "order by 1, 2;")
    List<Object[]> listAllProfessorAndCourseNamesTuples();

    default List<ProfessorAndCourseNameProjection> listAllProfessorAndCourseNames() {
        return listAllProfessorAndCourseNamesTuples()
                .stream()
                .collect(Collectors.groupingBy(it -> it[0]))
                .entrySet().stream()
                .map(mapEntry -> ProfessorAndCourseNameProjection.builder()
                        .name(mapEntry.getKey().toString())
                        .courses(
                                mapEntry.getValue().stream()
                                        .map(groupList -> groupList[1].toString())
                                        .sorted()
                                        .collect(Collectors.toList()))
                        .build())
                .sorted(Comparator.comparing(ProfessorAndCourseNameProjection::getName))
                .collect(Collectors.toList());
    }
}
