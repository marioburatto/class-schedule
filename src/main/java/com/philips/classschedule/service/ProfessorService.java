package com.philips.classschedule.service;

import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Optional<Professor> findById(Integer id){
        return professorRepository.findById(id);
    }

    public List<Professor> listAll(){
        return professorRepository.findAll();
    }

    public Professor create(Professor professor){
        return professorRepository.save(professor);
    }

    public Professor update(Professor professor){
        return professorRepository.save(professor);
    }

    public void delete(Professor professor){
        professorRepository.delete(professor);
    }

}
