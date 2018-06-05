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
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcampanteDataStoreRepositoryIT {

    @Inject
    private DataStoreRepository repository;

    @Inject
    private AcampanteRepositoryHelper helper;

    @BeforeClass
    public static void runMigrations(){
        DBMigrations.initForTest();
    }

    @Before
    public void setUp() throws Exception {
        PropertiesHelper.load("local_test");
        Guice.createInjector(new SimpleModule()).injectMembers(this);
        helper.insertDumbData();
    }

    @Test
    public void save() throws Exception {

        Acampante acampante = buildAcampante(null, "teste","masc",13);

        boolean result = repository.save(acampante);

        assertThat(result,is(true));

    }

    @Test
    public void fetch() throws Exception {

         List result = repository.fetch();
        assertThat(result.size()>0,is(true));

    }

    @Test
    public void update() throws Exception {

        Acampante returned = repository.fetch().get(0);
        Acampante expected = buildAcampante(returned.getId(), "updated", returned.getSexo(), returned.getIdade());

         assertThat(repository.update(expected),is(true));
    }

    @After
    public void tearDown() throws Exception {
        helper.cleanAcampanteTable();
    }

    private Acampante buildAcampante(Integer id, String nome, String sexo, int idade) {
        return Acampante.builder()
                .id(id)
                .nome(nome)
                .sexo(sexo)
                .idade(idade)
                .build();
    }
}