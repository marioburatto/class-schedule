package com.philips.classschedule.controller;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.service.DepartmentService;
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
@RequestMapping("/department")
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    List<Department> listAll() {
        return departmentService.listAll();
    }

    @PostMapping("")
    ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        if (department.getId() != null && departmentService.findById(department.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(departmentService.create(department));
    }

    @GetMapping("/{id}")
    ResponseEntity<Department> findById(@PathVariable Integer id) {
        return departmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Department> updateDepartment(@RequestBody Department department, @PathVariable Integer id) {
        if (department.getId() != null && !department.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        return departmentService.findById(id)
                .map(oldDepartment -> departmentService.update(department))
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
