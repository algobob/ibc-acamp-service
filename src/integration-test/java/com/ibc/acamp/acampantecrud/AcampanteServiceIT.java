package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.acampantecrud.helper.AcampanteRepositoryHelper;
import com.ibc.acamp.suport.SimpleModule;
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
        Guice.createInjector(new SimpleModule()).injectMembers(this);
        helper.createTables();
        helper.insertDumbData();
    }

    @Test
    public void shouldListAllAcampantesSuccessfully(){
        List<Acampante> acampantes = acampanteService.fetch();

        assertThat(acampantes.size(), is(2));
    }

    @After
    public void tearDown() throws Exception {
        helper.dropTables();
    }

}