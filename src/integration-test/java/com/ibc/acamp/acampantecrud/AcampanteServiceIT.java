package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.support.AcampanteRepositoryHelper;
import com.ibc.acamp.support.PropertiesHelper;
import com.ibc.acamp.support.SimpleModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AcampanteServiceIT {

    @Inject private AcampanteService acampanteService;
    @Inject private AcampanteRepositoryHelper helper;

    @Before
    public void setUp() throws Exception {
        PropertiesHelper.load("test");
        Guice.createInjector(new SimpleModule()).injectMembers(this);

        helper.createTables();
    }

    @Test
    public void shouldListAllAcampantesSuccessfully(){
        helper.insertDumbData();
        List<Acampante> acampantes = acampanteService.fetch();

        assertThat(acampantes.size(), is(2));
    }

    @Test
    public void shouldSaveAcamapante(){
        Acampante acampante = Acampante.builder().idade(12).nome("saved-acampante").sexo("Masculino").build();
        assertThat(acampanteService.save(acampante),is(true));
    }

    @After
    public void tearDown() throws Exception {
        helper.dropTables();
    }

}