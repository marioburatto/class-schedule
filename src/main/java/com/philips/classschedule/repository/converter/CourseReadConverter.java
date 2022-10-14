package com.philips.classschedule.repository.converter;

import com.philips.classschedule.domain.Course;
import com.philips.classschedule.domain.Department;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Optional;

@ReadingConverter
public class CourseReadConverter implements Converter<Row, Course> {

    public Course convert(Row source) {
        return new Course(
            source.get("id", Integer.class),
            source.get("name", String.class),
            source.get("credits", Integer.class),
            Optional.ofNullable(source.get("department_id", Integer.class))
                .map(it -> Department.builder().id(it).build())
                .orElse(null));
    }
}
