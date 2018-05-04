package com.ibc.acamp.acampantecrud;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.ibc.acamp.suport.StandardResponse;
import com.ibc.acamp.suport.StatusResponse;

import static spark.Spark.get;

public class AcampanteController {

    private AcampanteService acampanteService;

    @Inject
    public AcampanteController(AcampanteService acampanteService){
        this.acampanteService = acampanteService;
        registerRoutes();
    }

    private void registerRoutes() {
        get("/acampantes", (req,res) -> {
            res.type("application/json");
            return buildResponseAsJson(acampanteService.fetch());
        });
    }

    private String buildResponseAsJson(Object response) {
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson()
                        .toJsonTree(response)));
    }
}
