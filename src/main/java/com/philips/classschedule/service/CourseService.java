package com.philips.classschedule.service;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Mono<Course> findById(Integer id) {
        return courseRepository.findById(id);
    }

    public Flux<Course> listAll() {
        return courseRepository.findAll();
    }

    public Mono<Course> create(Course course) {
        return courseRepository.save(course);
    }

    public Mono<Course> update(Course course) {
        return courseRepository.save(course);
    }

    public Mono<Void> delete(Course course) {
        return courseRepository.delete(course);
    }

}
