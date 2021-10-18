package com.philips.classschedule.controller;

import com.philips.classschedule.controller.dto.ProfessorDto;
import com.philips.classschedule.controller.mapper.ProfessorDtoMapper;
import com.philips.classschedule.service.ProfessorService;
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
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorDtoMapper dtoMapper;

    @GetMapping("")
    List<ProfessorDto> listAll() {
        return professorService.listAll()
                .stream()
                .map(dtoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto professorDto) {
        if (professorDto.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return Optional.of(professorDto)
                .map(dtoMapper::dtoToDomain)
                .map(professorService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    ResponseEntity<ProfessorDto> findById(@PathVariable Integer id) {
        return professorService.findById(id)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<ProfessorDto> updateProfessor(@RequestBody ProfessorDto professorDto,
                                                 @PathVariable Integer id) {
        if (professorDto.getId() != null && !professorDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        return professorService.findById(id)
                .map(it -> dtoMapper.dtoToDomain(professorDto))
                .map(professorService::update)
                .map(dtoMapper::domainToDto)
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
