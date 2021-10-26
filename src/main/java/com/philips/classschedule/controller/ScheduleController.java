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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleDtoMapper dtoMapper;

    @GetMapping("")
    Flux<ScheduleDto> listAll() {
        return scheduleService.listAll()
                .map(dtoMapper::domainToDto);
    }

    @PostMapping("")
    Mono<ResponseEntity<ScheduleDto>> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return Mono.just(scheduleDto)
                .map(dtoMapper::dtoToDomain)
                .flatMap(scheduleService::create)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/exists")
    Mono<ResponseEntity<ScheduleDto>> scheduleExists(@RequestBody ScheduleDto scheduleDto) {

        return Mono.just(scheduleDto)
                .map(dtoMapper::dtoToDomain)
                .flatMap(scheduleService::findByExample)
                .map(dtoMapper::domainToDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }

    @DeleteMapping("")
    Mono<ResponseEntity<Object>> deleteSchedule(@RequestBody Schedule schedule) {
        return scheduleService.findByExample(schedule)
                .flatMap(scheduleService::delete)
                .map(result -> ResponseEntity.noContent().build())
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())));
    }
}
