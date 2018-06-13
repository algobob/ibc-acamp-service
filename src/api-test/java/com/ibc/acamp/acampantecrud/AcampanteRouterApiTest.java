package com.ibc.acamp.acampantecrud;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.ibc.acamp.Main;
import com.ibc.acamp.support.AcampanteRepositoryHelper;
import com.ibc.acamp.support.SimpleModule;
import com.ibc.acamp.support.StandardResponse;
import com.ibc.acamp.support.StatusResponse;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;

import static com.jayway.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AcampanteRouterApiTest {

    @Inject private AcampanteRepositoryHelper helper;

    @BeforeClass
    public static void init() throws IOException {
        Main.main(null);
    }

    @Before
    public void setUp() throws IOException, SQLException {
        RestAssured.baseURI="http://localhost:4567";
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void shouldReturnSuccessCodeWhenFetchAcampantes(){
        given().when().get("/acampantes").then().statusCode(200);
    }

    @Test
    public void shouldReturnSuccessCodeWhenSaveAcampantes(){
        String acamapanteJson = new Gson().toJson(Acampante.builder().idade(12).nome("saved-acamp").sexo("masc").build());
        Response response = given().contentType("application/json").body(acamapanteJson).when().post("/acampantes");
        String expectedJsonResponse = new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, emptyList()));
        assertThat(response.body().print(), is(expectedJsonResponse));
        assertThat(response.statusCode(), is(200));
    }

    @Test
    public void shouldReturnFailCodeWhenSaveAcampantes(){
        String acamapanteJson = new Gson().toJson(Acampante.builder().idade(12).sexo("masc").build());
        Response response = given().contentType("application/json").body(acamapanteJson).when().post("/acampantes");
        String expectedJsonResponse = new Gson().toJson(new StandardResponse(StatusResponse.FAIL, "Nome do acampante é obrigatório."));
        assertThat(response.body().print(), is(expectedJsonResponse));
        assertThat(response.statusCode(), is(200));
    }

    @Test
    public void shouldReturnSuccessResponseWhenUpdateAcampantes(){

        helper.insertDumbData();
        Acampante existentAcampante = helper.fetch().get(0);

        String acamapanteJson = new Gson().toJson(Acampante.builder()
                .id(existentAcampante.getId())
                .idade(12)
                .nome("mario-alterado")
                .sexo("masc").build());

        Response response = given().contentType("application/json").body(acamapanteJson).when().put("/acampantes/:id");
        String expectedJsonResponse = new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, emptyList()));
        assertThat(response.body().print(), is(expectedJsonResponse));
        assertThat(response.statusCode(), is(200));
    }

    @Test
    public void shouldReturnFailResponseWhenUpdateAcampantes(){

        helper.insertDumbData();
        Acampante existentAcampante = helper.fetch().get(0);

        String acamapanteWithoutNameJson = new Gson().toJson(Acampante.builder()
                .id(existentAcampante.getId())
                .idade(12)
                .sexo("masc").build());

        Response response = given().contentType("application/json").body(acamapanteWithoutNameJson).when().put("/acampantes/:id");
        String expectedJsonResponse = new Gson().toJson(new StandardResponse(StatusResponse.FAIL, "Nome do acampante é obrigatório."));
        assertThat(response.body().print(), is(expectedJsonResponse));
        assertThat(response.statusCode(), is(200));
    }

    @After
    public void tearDown() throws SQLException {
        helper.cleanAcampanteTable();
    }

    @AfterClass
    public static void stop(){
        Spark.stop();
    }
}
