package com.philips.classschedule.controller.mapper;

import com.philips.classschedule.controller.dto.ProfessorDto;
import com.philips.classschedule.domain.Department;
import com.philips.classschedule.domain.Professor;
import org.springframework.stereotype.Service;

@Service
public class ProfessorDtoMapper implements DtoMapperInterface<Professor, ProfessorDto> {

    @Override
    public ProfessorDto domainToDto(Professor domain) {
        return ProfessorDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .departmentId(domain.getDepartment().getId())
                .build();
    }

    @Override
    public Professor dtoToDomain(ProfessorDto dto) {
        return Professor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .department(Department.builder()
                        .id(dto.getDepartmentId())
                        .build())
                .build();
    }
}
