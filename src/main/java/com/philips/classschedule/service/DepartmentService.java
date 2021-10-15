package com.philips.classschedule.service;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Optional<Department> findById(Integer id){
        return departmentRepository.findById(id);
    }

    public List<Department> listAll(){
        return departmentRepository.findAll();
    }

    public Department create(Department department){
        return departmentRepository.save(department);
    }

    public Department update(Department department){
        return departmentRepository.save(department);
    }

    public void delete(Department department){
        departmentRepository.delete(department);
    }

}
