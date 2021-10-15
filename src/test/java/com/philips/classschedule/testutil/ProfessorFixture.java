package com.philips.classschedule.testutil;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.domain.Professor;

public class ProfessorFixture {

    public static Professor aNewProfessor(Department department) {
        Professor professor = new Professor();
        professor.setName("aNewProfessorName");
        professor.setDepartment(department);
        return professor;
    }
}
