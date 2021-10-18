package com.philips.classschedule.testutil;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.domain.Professor;

public class ProfessorFixture {

    public static Professor aNewProfessor(Department department) {
        return Professor.builder()
                .name("aNewProfessorName")
                .department(department)
                .build();
    }
}
