package com.ibc.acamp.acampantecrud;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AcampanteCrudService {

    private final DataStoreRepository repository;

    @Inject
    public AcampanteCrudService(DataStoreRepository repository){
        this.repository = repository;
    }

    public boolean save(Acampante acampante) {
        return repository.save(acampante);
    }
}
