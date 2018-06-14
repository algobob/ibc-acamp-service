package com.ibc.acamp.acampantecrud;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.ibc.acamp.support.JsonResponseBuilder;
import com.ibc.acamp.support.StandardResponse;
import com.ibc.acamp.support.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Collections.emptyList;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class AcampanteRouter {

    private Logger LOGGER = LoggerFactory.getLogger("AcampanteRouter");
    private AcampanteService acampanteService;

    @Inject
    public AcampanteRouter(AcampanteService acampanteService){
        this.acampanteService = acampanteService;
        registerRoutes();
    }

    private void registerRoutes() {
        get("/acampantes", (req,res) -> {
            LOGGER.info("[Get all acampantes][controller] Requesting all acampantes...");
            res.type("application/json");

            try {
                return new JsonResponseBuilder(StatusResponse.SUCCESS, acampanteService.fetch()).build();
            } catch (Exception exception) {
                return new JsonResponseBuilder(StatusResponse.ERROR, exception.getMessage()).build();
            }

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

        put("/acampantes/:id", (req,res) -> {
            LOGGER.info("[Add acampante][controller] Requesting creation of acampante...");
            res.type("application/json");

            Acampante acampante = new Gson().fromJson(req.body(), Acampante.class);

            try {
                acampanteService.update(acampante);
                return new JsonResponseBuilder(StatusResponse.SUCCESS, emptyList()).build();
            } catch (AcampanteInvalidoException acamapanteEx) {
                return new JsonResponseBuilder(StatusResponse.FAIL, acamapanteEx.getMessage()).build();
            } catch (Exception exception) {
                return new JsonResponseBuilder(StatusResponse.ERROR, exception.getMessage()).build();
            }
        });

        delete("/acampantes/:id", (req,res) -> {
            LOGGER.info("[Delete acampante][controller] Requesting deletion of acampante...");
            res.type("application/json");

            Integer idAcampante = Integer.valueOf(req.params("id"));

            try {
                acampanteService.delete(idAcampante);
                return new JsonResponseBuilder(StatusResponse.SUCCESS, emptyList()).build();
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
