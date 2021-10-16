package com.philips.classschedule.service;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Department;
import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.domain.Schedule;
import com.philips.classschedule.repository.ScheduleRepository;
import com.philips.classschedule.testutil.BaseSpringIntegrationTest;
import com.philips.classschedule.testutil.CourseFixture;
import com.philips.classschedule.testutil.DepartmentFixture;
import com.philips.classschedule.testutil.ProfessorFixture;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ScheduleServiceIntegrationTest extends BaseSpringIntegrationTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setup(){
        scheduleRepository.deleteAll();
    }

    @Test
    public void basicCrudTest() {
        Department department = departmentService.create(
                DepartmentFixture.aNewDepartment());
        Professor professor = professorService.create(
                ProfessorFixture.aNewProfessor(department));
        Course course = courseService.create(CourseFixture.aNewCourse(department));

        Schedule baseSchedule = new Schedule(1, 2021, professor, course);

        MatcherAssert.assertThat(
                scheduleService.findByExample(baseSchedule).isPresent(),
                Is.is(false));

        MatcherAssert.assertThat(scheduleService.create(baseSchedule), IsNull.notNullValue());
        MatcherAssert.assertThat(scheduleService.create(baseSchedule), IsNull.notNullValue());

        MatcherAssert.assertThat(
                scheduleService.findByExample(baseSchedule).isPresent(),
                Is.is(true));

        scheduleService.delete(baseSchedule);

        MatcherAssert.assertThat(
                scheduleService.findByExample(baseSchedule).isPresent(),
                Is.is(false));
    }
}
