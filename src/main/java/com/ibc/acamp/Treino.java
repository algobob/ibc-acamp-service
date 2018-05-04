package com.ibc.acamp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ibc.acamp.acampantecrud.AcampanteController;
import com.ibc.acamp.acampantecrud.AcampanteService;
import com.ibc.acamp.suport.SimpleModule;

import static spark.Spark.get;

public class Treino {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SimpleModule());
        AcampanteService acampanteService = injector.getInstance(AcampanteService.class);

        get("/",(req,res) -> "teste!!!");

        new AcampanteController(acampanteService);
    }
}
