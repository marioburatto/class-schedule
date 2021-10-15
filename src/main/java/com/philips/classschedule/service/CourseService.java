package com.philips.classschedule.service;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Optional<Course> findById(Integer id){
        return courseRepository.findById(id);
    }

    public List<Course> listAll(){
        return courseRepository.findAll();
    }

    public Course create(Course course){
        return courseRepository.save(course);
    }

    public Course update(Course course){
        return courseRepository.save(course);
    }

    public void delete(Course course){
        courseRepository.delete(course);
    }

}
