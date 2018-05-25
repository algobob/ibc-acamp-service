package com.ibc.acamp.acampantecrud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class AcampanteService implements IAcampanteService {

    private Logger LOGGER = LoggerFactory.getLogger("AcampanteService");
    private DataStoreRepository repository;

    @Inject
    public AcampanteService(DataStoreRepository repository){
        this.repository = repository;
    }

    public boolean save(Acampante acampante) throws AcampanteInvalidoException, SQLException {

        validateAcampante(acampante);
        return repository.save(acampante);
    }

    public List<Acampante> fetch() throws SQLException {
        LOGGER.info("[Get all acampantes][service] Requesting all acampantes from data store...");
        List<Acampante> acampantes = repository.fetch();
        LOGGER.info("[Get all acampantes][service] Result = {}", namesFrom(acampantes));
        return acampantes;
    }

    public boolean update(Acampante acampante) {
        return repository.update(acampante);
    }

    private List<String> namesFrom(List<Acampante> acampantes) {
        return acampantes.stream().map(Acampante::getNome).collect(Collectors.toList());
    }

    private boolean isNullOrEmptyField(String value) {
        return value == null || value.isEmpty();
    }

    private void validateAcampante(Acampante acampante) throws AcampanteInvalidoException {
        if (isNullOrEmptyField(acampante.getNome()))
            throw new AcampanteInvalidoException("Nome do acampante é obrigatório.");

        if (isNullOrEmptyField(acampante.getSexo()))
            throw new AcampanteInvalidoException("Sexo do acampante é obrigatório.");
    }
}
