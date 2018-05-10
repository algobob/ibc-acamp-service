package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.AcampanteRepositoryHelper;
import com.ibc.acamp.Treino;
import com.ibc.acamp.suport.SimpleModule;
import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Spark;

import javax.inject.Inject;

import static com.jayway.restassured.RestAssured.given;

public class AcampanteControllerApiTest {

    @Inject private AcampanteRepositoryHelper helper;

    @Before
    public void setUp() {
        RestAssured.baseURI="http://localhost:4567";
        Treino.main(null);
        Guice.createInjector(new SimpleModule()).injectMembers(this);
        helper.createTables();
        helper.insertDumbData();

    }

    @Test
    public void shouldReturn200HttpCode(){
        given().when().get("/acampantes").then().statusCode(200);
    }

    @After
    public void tearDown() {
        Spark.stop();
        helper.dropTables();
    }
}
