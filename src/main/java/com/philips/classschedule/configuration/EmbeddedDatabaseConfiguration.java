package com.philips.classschedule.configuration;

import de.flapdoodle.embed.process.runtime.Network;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Configuration
@Profile({"dev", "test"})
public class EmbeddedDatabaseConfiguration {
    private static final List<String> DEFAULT_ADDITIONAL_INIT_DB_PARAMS = Arrays
            .asList("--nosync", "--locale=en_US.UTF-8");

    @Bean
    @DependsOn("postgresProcess")
    public ConnectionFactory connectionFactory(PostgresConfig config) {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host(config.net().host())
                .port(config.net().port())
                .database(config.storage().dbName())
                .username(config.credentials().username())
                .password(config.credentials().password())
                .build());
    }

    @Bean
    @LiquibaseDataSource
    @DependsOn("postgresProcess")
    public DataSource dataSource(PostgresConfig config) {
        PGSimpleDataSource ds = new PGSimpleDataSource();
//        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(format("jdbc:postgresql://%s:%s/%s", config.net().host(), config.net().port(), config.storage().dbName()));
        ds.setUser(config.credentials().username());
        ds.setPassword(config.credentials().password());
        return ds;
    }

    @Bean
    public PostgresConfig postgresConfig() throws IOException {
        final PostgresConfig postgresConfig = new PostgresConfig(Version.V9_6_11,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("class_schedule"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("app-user", "app-pass")
        );

        postgresConfig.getAdditionalInitDbParams().addAll(DEFAULT_ADDITIONAL_INIT_DB_PARAMS);

        return postgresConfig;
    }

    @Bean(destroyMethod = "stop")
    public PostgresProcess postgresProcess(PostgresConfig config) throws IOException {
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();
        PostgresExecutable exec = runtime.prepare(config);
        PostgresProcess process = exec.start();
        return process;
    }
}