package com.philips.classschedule.domain;

import javax.persistence.*;

@Entity
public class Department {

    @Id
    @SequenceGenerator(name="department_id_seq",
            sequenceName="department_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="department_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String name;

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
}
