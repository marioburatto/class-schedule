package com.philips.classschedule.controller;

import com.philips.classschedule.testutil.BaseSpringIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentControllerTest extends BaseSpringIntegrationTest {

    @Autowired
    private DepartmentController departmentController;

    @Test
    public void test01(){
        System.out.println("Ok!");
    }
}
