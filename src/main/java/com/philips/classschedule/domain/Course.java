package com.philips.classschedule.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Course {

    @Id
    @SequenceGenerator(name = "course_id_seq",
            sequenceName = "course_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "course_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String name;
    private Integer credits;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
