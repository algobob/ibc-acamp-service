package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.DBMigrations;
import com.ibc.acamp.support.AcampanteRepositoryHelper;
import com.ibc.acamp.support.PropertiesHelper;
import com.ibc.acamp.support.SimpleModule;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AcampanteServiceIT {

    @Inject private AcampanteService acampanteService;
    @Inject private AcampanteRepositoryHelper helper;

    @BeforeClass
    public static void init() throws IOException {
        PropertiesHelper.load("local_test");
        DBMigrations.initFlywayForTest();
    }

    @Before
    public void setUp() throws Exception {
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void shouldFetchAllAcampantesSuccessfully() throws SQLException {
        helper.insertDumbData("maria", "feminino", 12);
        helper.insertDumbData("joao", "masculino", 13);
        List<Acampante> acampantes = acampanteService.fetch();

        assertThat(acampantes.size(), is(2));
    }

    @Test
    public void shouldFindAcampanteById() throws SQLException {
        helper.insertDumbData("maria", "feminino", 12);
        List<Acampante> acampantes = acampanteService.fetch();

        Acampante acampante = acampantes.get(0);
        Acampante acampanteFinded = acampanteService.findById(acampante.getId());
        assertThat(acampanteFinded, is(notNullValue()));
        assertThat(acampanteFinded.getId(), is(acampante.getId()));

    }

    @Test
    public void shouldSaveAcamapante() throws AcampanteInvalidoException, SQLException {
        Acampante acampante = Acampante.builder().idade(12).nome("saved-acampante").sexo("Masculino").build();
        assertThat(acampanteService.save(acampante),is(true));
    }

    @Test( expected = AcampanteInvalidoException.class)
    public void shouldThrowExceptionIfAcamapanteWithoutName() throws AcampanteInvalidoException, SQLException {
        Acampante acampante = Acampante.builder().idade(12).sexo("Masculino").build();
        acampanteService.save(acampante);
    }

    @Test( expected = AcampanteInvalidoException.class)
    public void shouldThrowExceptionIfAcamapanteWithoutSexo() throws AcampanteInvalidoException, SQLException {
        Acampante acampante = Acampante.builder().nome("saved-acampante").build();
        acampanteService.save(acampante);
    }

    @Test
    public void shouldDeleteAcamapante() {
        helper.insertDumbData("maria", "feminino", 12);
        List<Acampante> allAcampantes = helper.fetch();
        assertThat(acampanteService.delete(allAcampantes.get(0).getId()), is(true));
        assertThat(helper.fetch().contains(allAcampantes.get(0).getId()), is(false));
    }

        @Test
    public void shouldUpdateAcamapanteSuccessfully() throws SQLException, AcampanteInvalidoException {
        helper.insertDumbData("maria", "feminino", 12);

        Acampante acampante = acampanteService.fetch().get(0);
        Acampante acampanteUpdated = Acampante.builder()
                .nome("alterado!")
                .sexo(acampante.getSexo())
                .idade(acampante.getIdade())
                .id(acampante.getId())
                .build();

        assertThat(acampanteService.update(acampanteUpdated), is(true));

    }

    @Test( expected = AcampanteInvalidoException.class)
    public void shouldThrowExceptionWhenTryUpdateInvalidAcampante() throws SQLException, AcampanteInvalidoException {
        helper.insertDumbData("maria", "feminino", 12);

        Acampante acampante = acampanteService.fetch().get(0);
        Acampante acampanteUpdated = Acampante.builder()
                .sexo(acampante.getSexo())
                .idade(acampante.getIdade())
                .id(acampante.getId())
                .build();

        acampanteService.update(acampanteUpdated);
    }

    @After
    public void tearDown() throws Exception {
        helper.cleanAcampanteTable();
    }

}