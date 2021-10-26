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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorDtoMapper dtoMapper;

    @GetMapping("")
    Flux<ProfessorDto> listAll() {
        return professorService.listAll()
                .map(dtoMapper::domainToDto);
    }

    @PostMapping("")
    Mono<ResponseEntity<ProfessorDto>> createProfessor(@RequestBody ProfessorDto professorDto) {
        if (professorDto.getId() != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
        return Mono.just(professorDto)
                .map(dtoMapper::dtoToDomain)
                .flatMap(professorService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    Mono<ResponseEntity<ProfessorDto>> findById(@PathVariable Integer id) {
        return professorService.findById(id)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<ProfessorDto>> updateProfessor(@RequestBody ProfessorDto professorDto,
                                                       @PathVariable Integer id) {
        if (professorDto.getId() != null && !professorDto.getId().equals(id)) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return professorService.findById(id)
                .map(it -> dtoMapper.dtoToDomain(professorDto))
                .flatMap(professorService::update)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Object>> deleteProfessor(@PathVariable Integer id) {
        return professorService.findById(id)
                .flatMap(professorService::delete)
                .map(result -> ResponseEntity.noContent().build())
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }
}
