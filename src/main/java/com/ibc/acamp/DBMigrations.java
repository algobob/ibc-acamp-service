package com.ibc.acamp;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.ibc.acamp.support.PropertiesHelper.getJdbcPassword;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcUrl;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcUsername;

public class DBMigrations {

    public static final String DB_MIGRATION_DIR_RESOURCES = "filesystem:src/main/resources/db/migrations";
    public static final String DB_DEFAULT_SCHEMA = "acamp";
    private static Logger LOGGER = LoggerFactory.getLogger(DBMigrations.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Running database migrations.");
        buildFlyway(DB_DEFAULT_SCHEMA).migrate();
        LOGGER.info("Finished database migrations.");
    }

    public static void initFlywayForTest() {
            buildFlyway("acamp_test").migrate();
    }

    private static Flyway buildFlyway(String schema) {
        Flyway flyway = new Flyway();
        flyway.setLocations(DB_MIGRATION_DIR_RESOURCES);
        flyway.setSchemas(schema);
        flyway.setDataSource(getJdbcUrl(),getJdbcUsername(), getJdbcPassword());

        return flyway;
    }
}
