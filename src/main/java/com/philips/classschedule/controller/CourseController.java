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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDtoMapper dtoMapper;

    @GetMapping("")
    Flux<CourseDto> listAll() {
        return courseService.listAll()
                .map(dtoMapper::domainToDto);
    }

    @PostMapping("")
    Mono<ResponseEntity<CourseDto>> createCourse(@RequestBody CourseDto courseDto) {
        if (courseDto.getId() != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
        return Mono.just(courseDto)
                .map(dtoMapper::dtoToDomain)
                .flatMap(courseService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    Mono<ResponseEntity<CourseDto>> findById(@PathVariable Integer id) {
        return courseService.findById(id)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<CourseDto>> updateCourse(@RequestBody CourseDto courseDto,
                                                 @PathVariable Integer id) {
        if (courseDto.getId() != null && !courseDto.getId().equals(id)) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return courseService.findById(id)
                .map(it -> dtoMapper.dtoToDomain(courseDto))
                .flatMap(courseService::update)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Object>> deleteCourse(@PathVariable Integer id) {
        return courseService.findById(id)
                .flatMap(courseService::delete)
                .map(result -> ResponseEntity.noContent().build())
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }
}
