package com.ibc.acamp;

import com.ibc.acamp.support.PropertiesHelper;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DBMigrations {

    private static Logger LOGGER = LoggerFactory.getLogger(DBMigrations.class);

    public static void main(String[] args) throws IOException {
        PropertiesHelper.load("local");
        LOGGER.info("Running database migrations.");
        Flyway flyway = new Flyway();
        flyway.setLocations("filesystem:src/main/resources/db/migrations");
        flyway.setSchemas("acamp");

        if (PropertiesHelper.isHerokuEnv()){
            LOGGER.info("Heroku env - Load database properties from system enviroment.");

            setFlywayDataSource(flyway,
                    System.getenv("JDBC_DATABASE_URL"),
                    System.getenv("JDBC_DATABASE_USERNAME"),
                    System.getenv("JDBC_DATABASE_PASSWORD"));
        }else {
            LOGGER.info("Local env - Load database properties from properties file.");

            setFlywayDataSource(flyway,
                    PropertiesHelper.getProps("db.jdbc.url"),
                    PropertiesHelper.getProps("db.jdbc.user"),
                    PropertiesHelper.getProps("db.jdbc.password"));
        }

        flyway.migrate();
        LOGGER.info("Finished database migrations.");
    }

    private static void setFlywayDataSource(Flyway flyway, String url, String username, String password) {
        flyway.setDataSource(url,username,password);
    }
}
