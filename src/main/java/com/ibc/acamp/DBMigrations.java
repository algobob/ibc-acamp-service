package com.ibc.acamp;

import com.ibc.acamp.support.PropertiesHelper;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DBMigrations {

    public static final String DB_MIGRATION_DIR_RESOURCES = "filesystem:src/main/resources/db/migrations";
    public static final String DB_DEFAULT_SCHEMA = "acamp";
    private static Logger LOGGER = LoggerFactory.getLogger(DBMigrations.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Running database migrations.");
        runFlywayMigrations(DB_MIGRATION_DIR_RESOURCES, DB_DEFAULT_SCHEMA);
        LOGGER.info("Finished database migrations.");
    }

    public static void initForTest() {
        try {
            runFlywayMigrations(DB_MIGRATION_DIR_RESOURCES, "acamp_test");
        } catch( IOException ex) {
            LOGGER.error("Error to run db migrations. ex: {}.", ex.getMessage());
        }
    }

    private static void runFlywayMigrations(String location, String schema) throws IOException {
        Flyway flyway = new Flyway();
        flyway.setLocations(location);
        flyway.setSchemas(schema);

        if (PropertiesHelper.isHerokuEnv()){
            LOGGER.info("Heroku env - Load database properties from system enviroment.");

            setFlywayDataSource(flyway,
                    System.getenv("JDBC_DATABASE_URL"),
                    System.getenv("JDBC_DATABASE_USERNAME"),
                    System.getenv("JDBC_DATABASE_PASSWORD"));
        }else {
            PropertiesHelper.load("local");
            LOGGER.info("Local env - Load database properties from properties file.");

            setFlywayDataSource(flyway,
                    PropertiesHelper.getProps("db.jdbc.url"),
                    PropertiesHelper.getProps("db.jdbc.user"),
                    PropertiesHelper.getProps("db.jdbc.password"));
        }

        flyway.migrate();
    }

    private static void setFlywayDataSource(Flyway flyway, String url, String username, String password) {
        flyway.setDataSource(url,username,password);
    }
}
