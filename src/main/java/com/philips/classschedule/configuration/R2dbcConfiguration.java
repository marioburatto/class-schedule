package com.philips.classschedule.configuration;

import com.philips.classschedule.repository.converter.CourseReadConverter;
import com.philips.classschedule.repository.converter.CourseWriteConverter;
import com.philips.classschedule.repository.converter.FixedIssueR2dbcEntityTemplate;
import com.philips.classschedule.repository.converter.ProfessorReadConverter;
import com.philips.classschedule.repository.converter.ProfessorWriteConverter;
import com.philips.classschedule.repository.converter.ScheduleReadConverter;
import com.philips.classschedule.repository.converter.ScheduleWriteConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Autowired
    private ConnectionFactory connectionFactory;

    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Override
    public R2dbcEntityTemplate r2dbcEntityTemplate(DatabaseClient databaseClient, ReactiveDataAccessStrategy dataAccessStrategy) {
        Assert.notNull(databaseClient, "DatabaseClient must not be null!");
        Assert.notNull(dataAccessStrategy, "ReactiveDataAccessStrategy must not be null!");

        return new FixedIssueR2dbcEntityTemplate(databaseClient, dataAccessStrategy);
    }

    @Override
    protected List<Object> getCustomConverters() {

        List<Object> converterList = new ArrayList<>();
        converterList.add(new ProfessorReadConverter());
        converterList.add(new ProfessorWriteConverter());
        converterList.add(new CourseReadConverter());
        converterList.add(new CourseWriteConverter());
        converterList.add(new ScheduleReadConverter());
        converterList.add(new ScheduleWriteConverter());

        return converterList;
    }
}