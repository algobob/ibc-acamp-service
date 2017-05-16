package com.ibc.acamp.acampantecrud;

import com.google.inject.Guice;
import com.ibc.acamp.suport.SimpleModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AcampanteCrudServiceTest {

    @Mock
    private DataStoreRepository repository;

    @InjectMocks
    private AcampanteCrudService service;

    @Before
    public void setUp(){
        Guice.createInjector(new SimpleModule()).injectMembers(this);
    }

    @Test
    public void testSaveAcampante(){

        Acampante acampante = Acampante.builder()
                .nome("acampante-1")
                .idade(15)
                .sexo("masculino")
                .build();

        when(repository.save(acampante)).thenReturn(true);

        assertThat(service.save(acampante),is(true));
        verify(repository).save(acampante);
    }
}