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
                DepartmentFixture.aNewDepartment()).block();
        Department department2 = departmentService.create(
                DepartmentFixture.aNewDepartment("SomeDifferentDepartment")).block();

        Course course = courseService.create(CourseFixture.aNewCourse(department1)).block();
        MatcherAssert.assertThat(course.getId(), IsNull.notNullValue());
        MatcherAssert.assertThat(course.getCredits(), Is.is(6));
        MatcherAssert.assertThat(course.getDepartment().getId(), Is.is(department1.getId()));

        MatcherAssert.assertThat(
                courseService.findById(course.getId()).block().getName(),
                Is.is(course.getName()));

        course = course.toBuilder()
                .name("SOME NEW NAME")
                .credits(4)
                .department(department2)
                .build();
        courseService.update(course).block();

        MatcherAssert.assertThat(
                courseService.findById(course.getId()).block().getName(),
                Is.is(course.getName()));
        MatcherAssert.assertThat(course.getCredits(), Is.is(4));
        MatcherAssert.assertThat(course.getDepartment().getId(), Is.is(department2.getId()));

        courseService.delete(course).block();

        MatcherAssert.assertThat(
                courseService.findById(course.getId()).hasElement().block(),
                Is.is(false));
    }
}
