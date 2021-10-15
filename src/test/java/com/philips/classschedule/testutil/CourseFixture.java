package com.philips.classschedule.testutil;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.domain.Course;

public class CourseFixture {

    public static Course aNewCourse(Department department) {
        Course course = new Course();
        course.setName("aNewCourseName");
        course.setCredits(6);
        course.setDepartment(department);
        return course;
    }
}
