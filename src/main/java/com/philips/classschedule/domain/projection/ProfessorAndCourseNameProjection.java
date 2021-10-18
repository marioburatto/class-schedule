package com.philips.classschedule.domain.projection;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProfessorAndCourseNameProjection {
    private String name;
    private List<String> courses;
}
