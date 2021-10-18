package com.philips.classschedule.controller.mapper;

import com.philips.classschedule.controller.dto.CourseDto;
import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Department;
import org.springframework.stereotype.Service;

@Service
public class CourseDtoMapper implements DtoMapperInterface<Course, CourseDto> {

    @Override
    public CourseDto domainToDto(Course domain) {
        return CourseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .credits(domain.getCredits())
                .departmentId(domain.getDepartment().getId())
                .build();
    }

    @Override
    public Course dtoToDomain(CourseDto dto) {
        return Course.builder()
                .id(dto.getId())
                .name(dto.getName())
                .credits(dto.getCredits())
                .department(Department.builder()
                        .id(dto.getDepartmentId())
                        .build())
                .build();
    }
}
