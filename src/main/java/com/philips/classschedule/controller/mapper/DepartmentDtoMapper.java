package com.philips.classschedule.controller.mapper;

import com.philips.classschedule.controller.dto.DepartmentDto;
import com.philips.classschedule.domain.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentDtoMapper implements DtoMapperInterface<Department, DepartmentDto> {

    @Override
    public DepartmentDto domainToDto(Department domain) {
        return DepartmentDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    @Override
    public Department dtoToDomain(DepartmentDto dto) {
        return Department.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
