package com.philips.classschedule.controller.mapper;

import com.philips.classschedule.controller.dto.ScheduleDto;
import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.domain.Schedule;
import org.springframework.stereotype.Service;

@Service
public class ScheduleDtoMapper implements DtoMapperInterface<Schedule, ScheduleDto> {

    @Override
    public ScheduleDto domainToDto(Schedule domain) {
        return ScheduleDto.builder()
                .professorId(domain.getProfessor().getId())
                .courseId(domain.getCourse().getId())
                .semester(domain.getSemester())
                .year(domain.getYear())
                .build();
    }

    @Override
    public Schedule dtoToDomain(ScheduleDto dto) {
        return Schedule.builder()
                .professor(Professor.builder().id(dto.getProfessorId()).build())
                .course(Course.builder().id(dto.getCourseId()).build())
                .semester(dto.getSemester())
                .year(dto.getYear())
                .build();
    }
}
