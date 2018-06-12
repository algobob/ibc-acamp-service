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
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AcampanteServiceIT {

    @Inject private AcampanteService acampanteService;
    @Inject private AcampanteRepositoryHelper helper;

    @BeforeClass
    public static void runMigrations(){
        DBMigrations.initFlywayForTest();
    }

    @Before
    public void setUp() throws Exception {
        PropertiesHelper.load("local_test");
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void shouldListAllAcampantesSuccessfully() throws SQLException {
        helper.insertDumbData();
        List<Acampante> acampantes = acampanteService.fetch();

        assertThat(acampantes.size(), is(2));
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
    public void shouldUpdateAcamapanteSuccessfully() throws SQLException, AcampanteInvalidoException {
        helper.insertDumbData();

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
        helper.insertDumbData();

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