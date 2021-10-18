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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentDtoMapper dtoMapper;

    @GetMapping("")
    List<DepartmentDto> listAll() {
        return departmentService.listAll()
                .stream()
                .map(dtoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        if (departmentDto.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return Optional.of(departmentDto)
                .map(dtoMapper::dtoToDomain)
                .map(departmentService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    ResponseEntity<DepartmentDto> findById(@PathVariable Integer id) {
        return departmentService.findById(id)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto,
                                                   @PathVariable Integer id) {
        if (departmentDto.getId() != null && !departmentDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        return departmentService.findById(id)
                .map(it -> dtoMapper.dtoToDomain(departmentDto))
                .map(departmentService::update)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteDepartment(@PathVariable Integer id) {
        return departmentService.findById(id)
                .map(oldDepartment -> {
                    departmentService.delete(oldDepartment);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
