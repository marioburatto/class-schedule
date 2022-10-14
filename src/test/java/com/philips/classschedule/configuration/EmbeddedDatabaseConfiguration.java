package com.philips.classschedule.configuration;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile({"dev", "test"})
public class EmbeddedDatabaseConfiguration {
    private static final List<String> DEFAULT_ADDITIONAL_INIT_DB_PARAMS = Arrays
            .asList("--nosync", "--locale=en_US.UTF-8");

//    SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

    @Bean
    public ConnectionFactory connectionFactory(DataSource dataSource) {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(9876)
                .database("postgres")
                .username("postgres")
                .password("postgres")
                .build());
    }

    @Bean
    @Primary
    @LiquibaseDataSource
    public DataSource dataSource() throws Exception  {
        return EmbeddedPostgres.builder()
            .setPort(9876)
            .start()
            .getPostgresDatabase();
//        PGSimpleDataSource ds = new PGSimpleDataSource();
////        ds.setDriverClassName("org.postgresql.Driver");
//        ds.setUrl(format("jdbc:postgresql://%s:%s/%s", config.net().host(), config.net().port(), config.storage().dbName()));
//        ds.setUser(config.credentials().username());
//        ds.setPassword(config.credentials().password());
//        return ds;
    }

//    @Bean
//    public PostgresConfig postgresConfig() throws IOException {
//        SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();
//        pg.getEmbeddedPostgres().
//        pg.getEmbeddedPostgres().
//        final PostgresConfig postgresConfig = new PostgresConfig(Version.V9_6_11,
//                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
//                new AbstractPostgresConfig.Storage("class_schedule"),
//                new AbstractPostgresConfig.Timeout(),
//                new AbstractPostgresConfig.Credentials("app-user", "app-pass")
//        );
//
//        postgresConfig.getAdditionalInitDbParams().addAll(DEFAULT_ADDITIONAL_INIT_DB_PARAMS);
//
//        return postgresConfig;
//    }

//    @Bean(destroyMethod = "stop")
//    public PostgresProcess postgresProcess(PostgresConfig config) throws IOException {
//        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();
//        PostgresExecutable exec = runtime.prepare(config);
//        PostgresProcess process = exec.start();
//        return process;
//    }
}