package com.philips.classschedule.controller;

import com.philips.classschedule.controller.dto.CourseDto;
import com.philips.classschedule.controller.mapper.CourseDtoMapper;
import com.philips.classschedule.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDtoMapper dtoMapper;

    @GetMapping("")
    List<CourseDto> listAll() {
        return courseService.listAll()
                .stream()
                .map(dtoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        if (courseDto.getId() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return Optional.of(courseDto)
                .map(dtoMapper::dtoToDomain)
                .map(courseService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    ResponseEntity<CourseDto> findById(@PathVariable Integer id) {
        return courseService.findById(id)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto,
                                           @PathVariable Integer id) {
        if (courseDto.getId() != null && !courseDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        return courseService.findById(id)
                .map(it -> dtoMapper.dtoToDomain(courseDto))
                .map(courseService::update)
                .map(dtoMapper::domainToDto)
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
