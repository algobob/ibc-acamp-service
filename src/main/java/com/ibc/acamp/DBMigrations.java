package com.ibc.acamp;

import com.ibc.acamp.support.PropertiesHelper;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.ibc.acamp.support.PropertiesHelper.getJdbcPassword;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcSchema;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcUrl;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcUsername;

public class DBMigrations {

    private static final String DB_MIGRATION_DIR_RESOURCES = "filesystem:src/main/resources/db/migrations";
    private static String dbDefaultSchema;
    private static Logger LOGGER = LoggerFactory.getLogger(DBMigrations.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Running database migrations.");
        PropertiesHelper.load("local");
        dbDefaultSchema = getJdbcSchema();
        buildFlyway(dbDefaultSchema).migrate();
        LOGGER.info("Finished database migrations.");
    }

    public static void initFlywayForTest() throws IOException {
        PropertiesHelper.load("local_test");
        buildFlyway(getJdbcSchema()).migrate();
    }

    private static Flyway buildFlyway(String schema) {
        Flyway flyway = new Flyway();
        flyway.setLocations(DB_MIGRATION_DIR_RESOURCES);
        flyway.setSchemas(schema);
        flyway.setDataSource(getJdbcUrl(),getJdbcUsername(), getJdbcPassword());

        return flyway;
    }
}
