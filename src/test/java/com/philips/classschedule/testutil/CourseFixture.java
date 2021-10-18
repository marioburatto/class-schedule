package com.philips.classschedule.testutil;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Department;

public class CourseFixture {

    public static Course aNewCourse(Department department) {
        return Course.builder()
                .name("aNewCourseName")
                .credits(6)
                .department(department)
                .build();
    }
}
