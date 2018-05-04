package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.suport.SimpleModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AcampanteServiceTest {

    @Mock
    private DataStoreRepository repository;

    @InjectMocks
    private AcampanteService service;

    @Before
    public void setUp(){
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void testSaveAcampante(){

        Acampante acampante = buildAcampante("acampante-1", 15, "masculino");

        when(repository.save(acampante)).thenReturn(true);

        assertThat(service.save(acampante),is(true));
        verify(repository).save(acampante);
    }

    @Test
    public void fetch() throws Exception {

        when(repository.fetch()).thenReturn(asList(Acampante.builder().build()));

        List<Acampante> result = service.fetch();
        assertThat(result.isEmpty(),is(false));
        verify(repository).fetch();

    }

    @Test
    public void update() throws Exception {

        Acampante acampanteUpdated = buildAcampante("acampante-1", 18, "masculino");
        when(repository.update(acampanteUpdated)).thenReturn(true);

        boolean result = service.update(acampanteUpdated);

        assertThat(result,is(true));
        verify(repository).update(acampanteUpdated);

    }

    private Acampante buildAcampante(String nome, int idade, String sexo) {
        return Acampante.builder()
                .nome(nome)
                .idade(idade)
                .sexo(sexo)
                .build();
    }
}