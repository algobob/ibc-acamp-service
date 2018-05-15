package com.ibc.acamp;

import com.google.inject.Guice;
import com.ibc.acamp.acampantecrud.AcampanteRouter;
import com.ibc.acamp.acampantecrud.IAcampanteService;
import com.ibc.acamp.support.PropertiesHelper;
import com.ibc.acamp.support.SimpleModule;

import javax.inject.Inject;
import java.io.IOException;

import static spark.Spark.get;

public class Treino {

    @Inject IAcampanteService acampanteService;

    public static void main(String[] args) throws IOException {
        PropertiesHelper.load(getEnv());
        new Treino().init();
    }

    private void init() throws IOException {
        Guice.createInjector(new SimpleModule()).injectMembers(this);
        get("/",(req,res) -> "teste!!!");
        new AcampanteRouter(acampanteService);
    }

    private static String getEnv() {
        return System.getenv("ENV") == null ? "dev" : System.getenv("ENV");
    }
}
