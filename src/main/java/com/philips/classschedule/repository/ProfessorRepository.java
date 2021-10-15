package com.philips.classschedule.repository;

import com.philips.classschedule.domain.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer> {
    List<Professor> findAll();
}
