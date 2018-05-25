package com.ibc.acamp.acampantecrud;

import java.sql.SQLException;
import java.util.List;

public interface IAcampanteService {

    boolean save(Acampante acampante) throws AcampanteInvalidoException, SQLException;
    List<Acampante> fetch() throws SQLException;
    boolean update(Acampante acampante);

}
