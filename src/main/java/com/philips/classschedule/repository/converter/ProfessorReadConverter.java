package com.philips.classschedule.repository.converter;

import com.philips.classschedule.domain.Department;
import com.philips.classschedule.domain.Professor;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Optional;

@ReadingConverter
public class ProfessorReadConverter implements Converter<Row, Professor> {

    public Professor convert(Row source) {
        return new Professor(
            source.get("id", Integer.class),
            source.get("name", String.class),
            Optional.ofNullable(source.get("department_id", Integer.class))
                .map(it -> Department.builder().id(it).build())
                .orElse(null));
    }
}
