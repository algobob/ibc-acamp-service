package com.ibc.acamp.support;

import com.google.inject.AbstractModule;
import com.ibc.acamp.acampantecrud.AcampanteDataStoreRepository;
import com.ibc.acamp.acampantecrud.AcampanteService;
import com.ibc.acamp.acampantecrud.IAcampanteService;
import com.ibc.acamp.acampantecrud.DataStoreRepository;

public class SimpleModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataStoreRepository.class).to(AcampanteDataStoreRepository.class);
        bind(IAcampanteService.class).to(AcampanteService.class);
    }
}
