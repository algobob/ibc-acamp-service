package com.ibc.acamp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ibc.acamp.acampantecrud.AcampanteController;
import com.ibc.acamp.acampantecrud.AcampanteService;
import com.ibc.acamp.suport.PropertiesHelper;
import com.ibc.acamp.suport.SimpleModule;

import java.io.IOException;

import static spark.Spark.get;

public class Treino {
    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new SimpleModule());
        AcampanteService acampanteService = injector.getInstance(AcampanteService.class);
        PropertiesHelper.load(getEnv());

        get("/",(req,res) -> "teste!!!");

        new AcampanteController(acampanteService);
    }

    private static String getEnv() {
        return System.getenv("ENV") == null ? "dev" : System.getenv("ENV");
    }
}
