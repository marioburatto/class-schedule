package com.philips.classschedule.service;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.testutil.BaseSpringIntegrationTest;
import com.philips.classschedule.testutil.DepartmentFixture;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentServiceIntegrationTest extends BaseSpringIntegrationTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void basicCrudTest() {
        Department department = departmentService.create(DepartmentFixture.aNewDepartment()).block();
        MatcherAssert.assertThat(department.getId(), IsNull.notNullValue());

        MatcherAssert.assertThat(
                departmentService.findById(department.getId()).block().getName(),
                Is.is(department.getName()));

        department = department.toBuilder()
                .name("SOME NEW NAME")
                .build();
        departmentService.update(department).block();

        MatcherAssert.assertThat(
                departmentService.findById(department.getId()).block().getName(),
                Is.is(department.getName()));

        departmentService.delete(department).block();

        MatcherAssert.assertThat(
                departmentService.findById(department.getId()).hasElement().block(),
                Is.is(false));
    }
}
