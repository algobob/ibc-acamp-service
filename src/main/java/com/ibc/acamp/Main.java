package com.ibc.acamp;

import com.google.inject.Guice;
import com.ibc.acamp.acampantecrud.AcampanteRouter;
import com.ibc.acamp.acampantecrud.AcampanteService;
import com.ibc.acamp.support.PropertiesHelper;
import com.ibc.acamp.support.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

import static com.ibc.acamp.support.PropertiesHelper.isHerokuEnv;
import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

    private Logger LOGGER = LoggerFactory.getLogger(Main.class);
    @Inject AcampanteService acampanteService;

    public static void main(String[] args) throws IOException {
        PropertiesHelper.load(getEnv());
        port(getHerokuAssignedPort());
        new Main().init();
    }

    private void init() throws IOException {
        LOGGER.info("Initalizing guice injector.");
        initGuice();
        LOGGER.info("Setting up routes.");
        initRoutes();
    }

    private void initRoutes() {
        get("/",(req,res) -> "teste!!!");
        new AcampanteRouter(acampanteService);
    }

    private void initGuice() {
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    private static String getEnv() {
        return isHerokuEnv() ?  "local" : "local_test";
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
