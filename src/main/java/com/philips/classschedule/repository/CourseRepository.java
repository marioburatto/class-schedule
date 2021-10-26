package com.philips.classschedule.repository;

import com.philips.classschedule.domain.Course;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends ReactiveCrudRepository<Course, Integer> {
}
