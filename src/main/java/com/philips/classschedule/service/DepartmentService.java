package com.philips.classschedule.service;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Mono<Department> findById(Integer id) {
        return departmentRepository.findById(id);
    }

    public Flux<Department> listAll() {
        return departmentRepository.findAll();
    }

    public Mono<Department> create(Department department) {
        return departmentRepository.save(department);
    }

    public Mono<Department> update(Department department) {
        return departmentRepository.save(department);
    }

    public Mono<Void> delete(Department department) {
        return departmentRepository.delete(department);
    }

}
