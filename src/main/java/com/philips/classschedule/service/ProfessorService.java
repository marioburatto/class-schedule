package com.philips.classschedule.service;

import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.domain.projection.ProfessorAndCourseNameProjection;
import com.philips.classschedule.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Mono<Professor> findById(Integer id) {
        return professorRepository.findById(id);
    }

    public Flux<Professor> listAll() {
        return professorRepository.findAll();
    }

    public Flux<ProfessorAndCourseNameProjection> listAllProfessorAndCourseNames() {
        return professorRepository
                .listAllProfessorAndCourseNames();
    }

    public Mono<Professor> create(Professor professor) {
        return professorRepository.save(professor);
    }

    public Mono<Professor> update(Professor professor) {
        return professorRepository.save(professor);
    }

    public Mono<Void> delete(Professor professor) {
        return professorRepository.delete(professor);
    }

}
