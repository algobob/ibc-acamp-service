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
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcampanteDataStoreRepositoryIT {

    @Inject
    private DataStoreRepository repository;

    @Inject
    private AcampanteRepositoryHelper helper;

    @BeforeClass
    public static void runMigrations() throws IOException {
        PropertiesHelper.load("local_test");
        DBMigrations.initFlywayForTest();
    }

    @Before
    public void setUp() throws Exception {
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void save() throws Exception {

        Acampante acampante = buildAcampante(null, "teste","masc",13);

        boolean result = repository.save(acampante);

        assertThat(result,is(true));

    }

    @Test
    public void fetch() throws Exception {
        helper.insertDumbData("maria", "feminino", 12);

         List<Acampante> result = repository.fetch();
        assertThat(result.size()>0,is(true));

    }

    @Test
    public void findAcampanteById() throws Exception {
        helper.insertDumbData("maria","feminino",12);

        List<Acampante> acampantes = repository.fetch();
        Acampante acampante = repository.findById(acampantes.get(0).getId());
        assertThat(acampante, is(notNullValue()));

    }

    @Test
    public void didNotfindAcampanteById() throws Exception {
        helper.insertDumbData("maria","feminino",12);
        Integer unknownAcampanteId = -1;
        Acampante acampante = repository.findById(unknownAcampanteId);
        assertThat(acampante, is(notNullValue()));

    }

    @Test
    public void update() throws Exception {
        helper.insertDumbData("maria", "feminino", 12);

        Acampante returned = repository.fetch().get(0);
        Acampante expected = buildAcampante(returned.getId(), "updated", returned.getSexo(), returned.getIdade());

         assertThat(repository.update(expected),is(true));
    }

    @Test
    public void delete() throws Exception {
        Acampante returned = repository.fetch().get(0);

        assertThat(repository.delete(returned.getId()), is(true));
        assertThat(repository.fetch().contains(returned), is(false) );

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