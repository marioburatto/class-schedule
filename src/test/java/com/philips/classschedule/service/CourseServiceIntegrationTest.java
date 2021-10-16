package com.philips.classschedule.service;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Department;
import com.philips.classschedule.testutil.BaseSpringIntegrationTest;
import com.philips.classschedule.testutil.CourseFixture;
import com.philips.classschedule.testutil.DepartmentFixture;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceIntegrationTest extends BaseSpringIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void basicCrudTest() {
        Department department1 = departmentService.create(
                DepartmentFixture.aNewDepartment());
        Department department2 = departmentService.create(
                DepartmentFixture.aNewDepartment("SomeDifferentDepartment"));

        Course course = courseService.create(CourseFixture.aNewCourse(department1));
        MatcherAssert.assertThat(course.getId(), IsNull.notNullValue());
        MatcherAssert.assertThat(course.getCredits(), Is.is(6));
        MatcherAssert.assertThat(course.getDepartment().getId(), Is.is(department1.getId()));

        MatcherAssert.assertThat(
                courseService.findById(course.getId()).get().getName(),
                Is.is(course.getName()));

        course.setName("SOME NEW NAME");
        course.setCredits(4);
        course.setDepartment(department2);
        courseService.update(course);

        MatcherAssert.assertThat(
                courseService.findById(course.getId()).get().getName(),
                Is.is(course.getName()));
        MatcherAssert.assertThat(course.getCredits(), Is.is(4));
        MatcherAssert.assertThat(course.getDepartment().getId(), Is.is(department2.getId()));

        courseService.delete(course);

        MatcherAssert.assertThat(
                courseService.findById(course.getId()).isPresent(),
                Is.is(false));
    }
}
