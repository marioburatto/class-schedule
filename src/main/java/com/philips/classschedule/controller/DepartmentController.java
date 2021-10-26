package com.philips.classschedule.controller;

import com.philips.classschedule.controller.dto.DepartmentDto;
import com.philips.classschedule.controller.mapper.DepartmentDtoMapper;
import com.philips.classschedule.service.DepartmentService;
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
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentDtoMapper dtoMapper;

    @GetMapping("")
    Flux<DepartmentDto> listAll() {
        return departmentService.listAll()
                .map(dtoMapper::domainToDto);
    }

    @PostMapping("")
    Mono<ResponseEntity<DepartmentDto>> createDepartment(@RequestBody DepartmentDto departmentDto) {
        if (departmentDto.getId() != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
        return Mono.just(departmentDto)
                .map(dtoMapper::dtoToDomain)
                .flatMap(departmentService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    Mono<ResponseEntity<DepartmentDto>> findById(@PathVariable Integer id) {
        return departmentService.findById(id)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<DepartmentDto>> updateDepartment(@RequestBody DepartmentDto departmentDto,
                                                         @PathVariable Integer id) {
        if (departmentDto.getId() != null && !departmentDto.getId().equals(id)) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return departmentService.findById(id)
                .map(it -> dtoMapper.dtoToDomain(departmentDto))
                .flatMap(departmentService::update)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Object>> deleteDepartment(@PathVariable Integer id) {
        return departmentService.findById(id)
                .flatMap(departmentService::delete)
                .map(result -> ResponseEntity.noContent().build())
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }
}
