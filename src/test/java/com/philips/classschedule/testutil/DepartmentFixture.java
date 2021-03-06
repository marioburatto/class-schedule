package com.philips.classschedule.testutil;

import com.philips.classschedule.domain.Department;

public class DepartmentFixture {

    public static Department aNewDepartment() {
        return aNewDepartment("aNewDepartmentName");
    }

    public static Department aNewDepartment(String name) {
        return Department.builder()
                .name(name)
                .build();
    }
}
