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
import reactor.test.StepVerifier;

public class ProfessorServiceIntegrationTest extends BaseSpringIntegrationTest {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void basicCrudTest() {
        Department department1 = departmentService.create(
                DepartmentFixture.aNewDepartment()).block();
        Department department2 = departmentService.create(
                DepartmentFixture.aNewDepartment("SomeDifferentDepartment")).block();

        Professor professor = professorService.create(ProfessorFixture.aNewProfessor(department1)).block();
        MatcherAssert.assertThat(professor.getId(), IsNull.notNullValue());
        MatcherAssert.assertThat(professor.getDepartment().getId(), Is.is(department1.getId()));

        MatcherAssert.assertThat(
                professorService.findById(professor.getId()).block().getName(),
                Is.is(professor.getName()));

        professor = professor.toBuilder()
                .name("SOME NEW NAME")
                .department(department2)
                .build();
        professor = professorService.update(professor).block();

        Professor finalProfessor = professor;
        professorService.findById(professor.getId())
                .as(StepVerifier::create)
                .expectNextMatches(foundEntity -> foundEntity.getId() != null &&
                        foundEntity.getName().equals(finalProfessor.getName()) &&
                        foundEntity.getDepartment().getId()
                                .equals(finalProfessor.getDepartment().getId()))
                .verifyComplete();

        MatcherAssert.assertThat(
                professorService.findById(professor.getId()).block().getName(),
                Is.is(professor.getName()));
        MatcherAssert.assertThat(professor.getDepartment().getId(), Is.is(department2.getId()));

        professorService.delete(professor).block();

        MatcherAssert.assertThat(
                professorService.findById(professor.getId()).hasElement().block(),
                Is.is(false));
    }
}
