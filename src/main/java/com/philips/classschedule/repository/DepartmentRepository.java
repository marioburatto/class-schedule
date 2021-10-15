package com.philips.classschedule.repository;

import com.philips.classschedule.domain.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    List<Department> findAll();
}
