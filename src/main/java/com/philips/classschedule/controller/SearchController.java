package com.philips.classschedule.controller;

import com.philips.classschedule.controller.dto.ProfessorAndCourseDto;
import com.philips.classschedule.controller.mapper.ProfessorAndCourseDtoMapper;
import com.philips.classschedule.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorAndCourseDtoMapper dtoMapper;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("")
    List<ProfessorAndCourseDto> listAll() {
        return professorService.listAllProfessorAndCourseNames()
                .stream()
                .map(dtoMapper::domainToDto)
                .collect(Collectors.toList());
    }

}
