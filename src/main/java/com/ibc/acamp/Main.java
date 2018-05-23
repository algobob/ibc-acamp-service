package com.ibc.acamp;

import com.google.inject.Guice;
import com.ibc.acamp.acampantecrud.AcampanteRouter;
import com.ibc.acamp.acampantecrud.IAcampanteService;
import com.ibc.acamp.support.PropertiesHelper;
import com.ibc.acamp.support.SimpleModule;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

    private Logger LOGGER = LoggerFactory.getLogger(Main.class);
    @Inject IAcampanteService acampanteService;

    public static void main(String[] args) throws IOException {
        port(getHerokuAssignedPort());
        PropertiesHelper.load(getEnv());
        new Main().init();
    }

    private void init() throws IOException {
        LOGGER.info("Initalizing guice injector.");
        initGuice();
        LOGGER.info("Setting up routes.");
        initRoutes();
        LOGGER.info("Running database migrations.");
        initDbMigrations();
        LOGGER.info("Finished database migrations.");
    }

    private void initDbMigrations() {
        Flyway flyway = new Flyway();
        flyway.setLocations("filesystem:src/main/resources/db/migrations");
        flyway.setSchemas("acamp");

        if (PropertiesHelper.isHerokuEnv()){
            setFlywayDataSource(flyway,
                    System.getenv("JDBC_DATABASE_URL"),
                    System.getenv("JDBC_DATABASE_USERNAME"),
                    System.getenv("JDBC_DATABASE_PASSWORD"));
        }else {
            setFlywayDataSource(flyway,
                    PropertiesHelper.getProps("db.jdbc.url"),
                    PropertiesHelper.getProps("db.jdbc.user"),
                    PropertiesHelper.getProps("db.jdbc.password"));
        }
        flyway.migrate();
    }

    private void setFlywayDataSource(Flyway flyway, String url, String username, String password) {
        flyway.setDataSource(url,username,password);
    }

    private void initRoutes() {
        get("/",(req,res) -> "teste!!!");
        new AcampanteRouter(acampanteService);
    }

    private void initGuice() {
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    private static String getEnv() {
        return System.getenv("ENV") == null ? "local" : System.getenv("ENV");
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
