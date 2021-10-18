package com.philips.classschedule.controller;

import com.philips.classschedule.controller.dto.ScheduleDto;
import com.philips.classschedule.controller.mapper.ScheduleDtoMapper;
import com.philips.classschedule.domain.Schedule;
import com.philips.classschedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleDtoMapper dtoMapper;

    @GetMapping("")
    List<ScheduleDto> listAll() {
        return scheduleService.listAll()
                .stream()
                .map(dtoMapper::domainToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return Optional.of(scheduleDto)
                .map(dtoMapper::dtoToDomain)
                .map(scheduleService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/exists")
    ResponseEntity<ScheduleDto> scheduleExists(@RequestBody ScheduleDto scheduleDto) {

        return Optional.of(scheduleDto)
                .map(dtoMapper::dtoToDomain)
                .map(scheduleService::findByExample)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("")
    ResponseEntity<Object> deleteSchedule(@RequestBody Schedule schedule) {
        return scheduleService.findByExample(schedule)
                .map(oldSchedule -> {
                    scheduleService.delete(oldSchedule);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
