package com.ibc.acamp.acampantecrud;

import com.ibc.acamp.Treino;
import com.jayway.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import javax.inject.Inject;

import static com.jayway.restassured.RestAssured.given;

public class AcampanteServiceControllerIT {

    @Inject
    private AcampanteService acampanteService;

    @BeforeClass
    public static void setUp() throws Exception {
        RestAssured.port = 4567;
        RestAssured.basePath = "/";
        RestAssured.baseURI = "http://localhost";

        Treino.main(null);
    }

    @Test
    public void shouldListAllAcampantesSuccessfully(){
        given().when().get("/acampantes").then().statusCode(200);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Spark.stop();
    }

}