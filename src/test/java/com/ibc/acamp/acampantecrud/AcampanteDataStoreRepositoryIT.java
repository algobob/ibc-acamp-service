package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.suport.SimpleModule;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcampanteDataStoreRepositoryIT {

    @Inject
    private DataStoreRepository repository;

    @Before
    public void setUp() throws Exception {
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void save() throws Exception {
        Acampante acampante = Acampante.builder()
                .nome("joao")
                .sexo("masc")
                .idade(15)
                .build();

        boolean result = repository.save(acampante);

        assertThat(result,is(true));

    }

}