package com.philips.classschedule.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class ProfessorDto {
    private Integer id;
    private String name;
    private Integer departmentId;
}
