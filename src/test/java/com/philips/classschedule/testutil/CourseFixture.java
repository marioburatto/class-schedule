package com.philips.classschedule.testutil;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Department;

public class CourseFixture {

    public static Course aNewCourse(Department department) {
        Course course = new Course();
        course.setName("aNewCourseName");
        course.setCredits(6);
        course.setDepartment(department);
        return course;
    }
}
