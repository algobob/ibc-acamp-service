package com.ibc.acamp.acampantecrud;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.ibc.acamp.support.JsonResponseBuilder;
import com.ibc.acamp.support.StandardResponse;
import com.ibc.acamp.support.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Collections.emptyList;
import static spark.Spark.get;
import static spark.Spark.post;

public class AcampanteRouter {

    private Logger LOGGER = LoggerFactory.getLogger("AcampanteRouter");
    private IAcampanteService acampanteService;

    @Inject
    public AcampanteRouter(IAcampanteService acampanteService){
        this.acampanteService = acampanteService;
        registerRoutes();
    }

    private void registerRoutes() {
        get("/acampantes", (req,res) -> {
            LOGGER.info("[Get all acampantes][controller] Requesting all acampantes...");
            res.type("application/json");
            return buildFailedResponseAsJson(acampanteService.fetch());
        });

        post("/acampantes", (req,res) -> {
            LOGGER.info("[Add acampante][controller] Requesting creation of acampante...");
            res.type("application/json");

            Acampante acampante = new Gson().fromJson(req.body(), Acampante.class);

            try {
                acampanteService.save(acampante);
                return new JsonResponseBuilder(StatusResponse.SUCCESS, emptyList()).build();
            } catch (AcampanteInvalidoException acamapanteEx) {
                return new JsonResponseBuilder(StatusResponse.FAIL, acamapanteEx.getMessage()).build();
            } catch (Exception exception) {
                return new JsonResponseBuilder(StatusResponse.ERROR, exception.getMessage()).build();
            }
        });
    }

    private String buildFailedResponseAsJson(Object response) {
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson()
                        .toJsonTree(response)));
    }
}
