package com.philips.classschedule.domain;

import javax.persistence.*;

@Entity
public class Course {

    @Id
    @SequenceGenerator(name="course_id_seq",
            sequenceName="course_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="course_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String name;
    private Integer credits;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
