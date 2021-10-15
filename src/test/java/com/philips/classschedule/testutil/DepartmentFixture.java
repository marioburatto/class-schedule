package com.philips.classschedule.testutil;

import com.philips.classschedule.domain.Department;

public class DepartmentFixture {

    public static Department aNewDepartment() {
        Department department = new Department();
        department.setName("aNewDepartmentName");
        return department;
    }
}
