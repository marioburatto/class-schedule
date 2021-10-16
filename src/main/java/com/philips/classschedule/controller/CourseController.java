package com.philips.classschedule.controller;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    List<Course> listAll() {
        return courseService.listAll();
    }

    @PostMapping("")
    ResponseEntity<Course> createCourse(@RequestBody Course course) {
        if (course.getId() != null && courseService.findById(course.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(courseService.create(course));
    }

    @GetMapping("/{id}")
    ResponseEntity<Course> findById(@PathVariable Integer id) {
        return courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable Integer id) {
        if (course.getId() != null && !course.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        return courseService.findById(id)
                .map(oldCourse -> courseService.update(course))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteCourse(@PathVariable Integer id) {
        return courseService.findById(id)
                .map(oldCourse -> {
                    courseService.delete(oldCourse);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
