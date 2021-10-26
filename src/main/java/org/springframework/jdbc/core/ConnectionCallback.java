package org.springframework.jdbc.core;

/**
 * Class just to fake LiquibaseAutoConfiguration not needed dependency on spring-jdbc.
 * <p>
 * {@link org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration#LiquibaseConfiguration}
 * <p>
 * See discussion at: https://github.com/spring-projects/spring-boot/issues/20715
 */
public class ConnectionCallback {
    //Just to fake the LiquibaseAutoConfiguration
}
