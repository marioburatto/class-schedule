package com.philips.classschedule.repository.converter;

import org.springframework.dao.DataAccessException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.StatementMapper;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.sql.Functions;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.PreparedOperation;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Temporary workaround for spring-data-r2dbc Issue
 * Spring-Data-R2dbc issue: https://github.com/spring-projects/spring-data-r2dbc/issues/672
 */
public class FixedIssueR2dbcEntityTemplate extends R2dbcEntityTemplate {

    public FixedIssueR2dbcEntityTemplate(DatabaseClient databaseClient, ReactiveDataAccessStrategy strategy) {
        super(databaseClient, strategy);
    }

    @Override
    public Mono<Long> count(Query query, Class<?> entityClass) throws DataAccessException {

        Assert.notNull(query, "Query must not be null");
        Assert.notNull(entityClass, "entity class must not be null");

        return doCount(query, entityClass, getRequiredEntity(entityClass).getTableName());
    }

    Mono<Long> doCount(Query query, Class<?> entityClass, SqlIdentifier tableName) {

        RelationalPersistentEntity<?> entity = getRequiredEntity(entityClass);
        StatementMapper statementMapper = getDataAccessStrategy().getStatementMapper().forType(entityClass);

        SqlIdentifier columnName = entity.hasIdProperty() ? entity.getRequiredIdProperty().getColumnName()
                : SqlIdentifier.unquoted("*");

        StatementMapper.SelectSpec selectSpec = statementMapper //
                .createSelect(tableName) //
                .doWithTable((table, spec) -> {
                    return spec.withProjection(Functions.count(table.column(columnName)));
                });

        Optional<CriteriaDefinition> criteria = query.getCriteria();
        if (criteria.isPresent()) {
            selectSpec = criteria.map(selectSpec::withCriteria).orElse(selectSpec);
        }

        PreparedOperation<?> operation = statementMapper.getMappedObject(selectSpec);

        return getDatabaseClient().sql(operation) //
                .map((r, md) -> r.get(0, Long.class)) //
                .first() //
                .defaultIfEmpty(0L);
    }

    private RelationalPersistentEntity<?> getRequiredEntity(Class<?> entityClass) {
        return getDataAccessStrategy()
                .getConverter()
                .getMappingContext()
                .getRequiredPersistentEntity(entityClass);
    }
}
