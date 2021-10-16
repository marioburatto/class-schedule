package com.philips.classschedule.controller;

import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.service.ProfessorService;
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
@RequestMapping("/professor")
public class ProfessorController {
    private ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("")
    List<Professor> listAll() {
        return professorService.listAll();
    }

    @PostMapping("")
    ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) {
        if (professor.getId() != null && professorService.findById(professor.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(professorService.create(professor));
    }

    @GetMapping("/{id}")
    ResponseEntity<Professor> findById(@PathVariable Integer id) {
        return professorService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Professor> updateProfessor(@RequestBody Professor professor, @PathVariable Integer id) {
        if (professor.getId() != null && !professor.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        return professorService.findById(id)
                .map(oldProfessor -> professorService.update(professor))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteProfessor(@PathVariable Integer id) {
        return professorService.findById(id)
                .map(oldProfessor -> {
                    professorService.delete(oldProfessor);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
