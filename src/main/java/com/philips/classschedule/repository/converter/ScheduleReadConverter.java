package com.philips.classschedule.repository.converter;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Professor;
import com.philips.classschedule.domain.Schedule;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Optional;

@ReadingConverter
public class ScheduleReadConverter implements Converter<Row, Schedule> {

    public Schedule convert(Row source) {
        return new Schedule(
            source.get("semester", Integer.class),
            source.get("year", Integer.class),
            Optional.ofNullable(source.get("professor_id", Integer.class))
                .map(it -> Professor.builder().id(it).build())
                .orElse(null),
            Optional.ofNullable(source.get("course_id", Integer.class))
                .map(it -> Course.builder().id(it).build())
                .orElse(null));
    }
}
