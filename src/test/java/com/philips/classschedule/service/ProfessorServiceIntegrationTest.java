package com.philips.classschedule.service;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.testutil.BaseSpringIntegrationTest;
import com.philips.classschedule.testutil.DepartmentFixture;
import com.philips.classschedule.testutil.ProfessorFixture;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfessorServiceIntegrationTest extends BaseSpringIntegrationTest {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void basicCrudTest() {
        Department department1 = departmentService.create(
                DepartmentFixture.aNewDepartment());
        Department department2 = departmentService.create(
                DepartmentFixture.aNewDepartment("SomeDifferentDepartment"));

        Professor professor = professorService.create(ProfessorFixture.aNewProfessor(department1));
        MatcherAssert.assertThat(professor.getId(), IsNull.notNullValue());
        MatcherAssert.assertThat(professor.getDepartment().getId(), Is.is(department1.getId()));

        MatcherAssert.assertThat(
                professorService.findById(professor.getId()).get().getName(),
                Is.is(professor.getName()));

        professor.setName("SOME NEW NAME");
        professor.setDepartment(department2);
        professorService.update(professor);

        MatcherAssert.assertThat(
                professorService.findById(professor.getId()).get().getName(),
                Is.is(professor.getName()));
        MatcherAssert.assertThat(professor.getDepartment().getId(), Is.is(department2.getId()));

        professorService.delete(professor);

        MatcherAssert.assertThat(
                professorService.findById(professor.getId()).isPresent(),
                Is.is(false));
    }
}
