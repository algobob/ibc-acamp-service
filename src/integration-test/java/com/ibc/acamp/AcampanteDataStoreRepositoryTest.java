package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.suport.SimpleModule;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

public class AcampanteDataStoreRepositoryTest {

    @Inject
    private DataStoreRepository repository;

    @Before
    public void setUp() throws Exception {
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void save() throws Exception {
        Acampante 

    }

}