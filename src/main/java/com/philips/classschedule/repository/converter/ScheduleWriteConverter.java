package com.philips.classschedule.repository.converter;

import com.philips.classschedule.domain.Schedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class ScheduleWriteConverter implements Converter<Schedule, OutboundRow> {

    public OutboundRow convert(Schedule source) {
        OutboundRow row = new OutboundRow();
        row.put("semester", Parameter.from(source.getSemester()));
        row.put("year", Parameter.from(source.getYear()));
        row.put("professor_id", Parameter.from(source.getProfessor().getId()));
        row.put("course_id", Parameter.from(source.getCourse().getId()));
        return row;
    }
}
