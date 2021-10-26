package com.philips.classschedule.repository.converter;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.function.BiFunction;

/**
 * Temporary workaround for spring-data-r2dbc Issue
 * Spring-Data-R2dbc issue: https://github.com/spring-projects/spring-data-r2dbc/issues/672
 */
public class FixedIssueMappingR2dbcConverter extends MappingR2dbcConverter {
    public FixedIssueMappingR2dbcConverter(MappingContext<? extends RelationalPersistentEntity<?>, ? extends RelationalPersistentProperty> context, CustomConversions conversions) {
        super(context, conversions);
    }

    @Override
    public <T> BiFunction<Row, RowMetadata, T> populateIdIfNecessary(T object) {


        Assert.notNull(object, "Entity object must not be null!");

        Class<?> userClass = ClassUtils.getUserClass(object);
        RelationalPersistentEntity<?> entity = getMappingContext().getRequiredPersistentEntity(userClass);

        RelationalPersistentProperty idProperty = entity.getIdProperty();
        if (idProperty == null) {
            return (row, metadata) -> object;
        }

        return super.populateIdIfNecessary(object);
    }
}
