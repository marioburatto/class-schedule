package com.philips.classschedule.repository.converter;

import com.philips.classschedule.domain.Professor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class ProfessorWriteConverter implements Converter<Professor, OutboundRow> {

    public OutboundRow convert(Professor source) {
        OutboundRow row = new OutboundRow();
        if (source.getId() != null) {
            row.put("id", Parameter.from(source.getId()));
        }
        row.put("name", Parameter.from(source.getName()));
        row.put("department_id", Parameter.from(source.getDepartment().getId()));
        return row;
    }
}
