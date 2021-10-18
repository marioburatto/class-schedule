package com.philips.classschedule.controller.mapper;

import com.philips.classschedule.controller.dto.ProfessorAndCourseDto;
import com.philips.classschedule.domain.projection.ProfessorAndCourseNameProjection;
import org.springframework.stereotype.Service;

@Service
public class ProfessorAndCourseDtoMapper implements
        DtoMapperInterface<ProfessorAndCourseNameProjection, ProfessorAndCourseDto> {

    @Override
    public ProfessorAndCourseDto domainToDto(ProfessorAndCourseNameProjection domain) {
        return ProfessorAndCourseDto.builder()
                .name(domain.getName())
                .courses(domain.getCourses())
                .build();
    }

    @Override
    public ProfessorAndCourseNameProjection dtoToDomain(ProfessorAndCourseDto dto) {
        return ProfessorAndCourseNameProjection.builder()
                .name(dto.getName())
                .courses(dto.getCourses())
                .build();
    }
}
