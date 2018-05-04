package com.ibc.acamp.acampantecrud;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AcampanteService implements AcampanteServiceInterface{

    private DataStoreRepository repository;

    @Inject
    public AcampanteService(DataStoreRepository repository){
        this.repository = repository;
    }

    public boolean save(Acampante acampante) {
        return repository.save(acampante);
    }

    public List<Acampante> fetch() {
        return repository.fetch();
    }

    public boolean update(Acampante acampante) {
        return repository.update(acampante);
    }
}
