package com.ibc.acamp.acampantecrud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class AcampanteService implements IAcampanteService {

    private Logger LOGGER = LoggerFactory.getLogger("AcampanteService");
    private DataStoreRepository repository;

    @Inject
    public AcampanteService(DataStoreRepository repository){
        this.repository = repository;
    }

    public boolean save(Acampante acampante) {

        if (isInvalidAcampante(acampante))
            return false;

        return repository.save(acampante);
    }

    private boolean isInvalidAcampante(Acampante acampante) {
        return !Optional.ofNullable(acampante.getNome()).isPresent() ||
                !Optional.ofNullable(acampante.getSexo()).isPresent();
    }

    public List<Acampante> fetch() {
        LOGGER.info("[Get all acampantes][service] Requesting all acampantes from data store...");
        List<Acampante> acampantes = repository.fetch();
        LOGGER.info("[Get all acampantes][service] Result = {}", namesFrom(acampantes));
        return acampantes;
    }

    private List<String> namesFrom(List<Acampante> acampantes) {
        return acampantes.stream().map(Acampante::getNome).collect(Collectors.toList());
    }

    public boolean update(Acampante acampante) {
        return repository.update(acampante);
    }
}
