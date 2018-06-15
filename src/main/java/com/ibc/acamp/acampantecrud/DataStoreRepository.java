package com.ibc.acamp.acampantecrud;

import java.sql.SQLException;
import java.util.List;

public interface DataStoreRepository {
    boolean save(Acampante data) throws SQLException;
    List<Acampante> fetch() throws SQLException;

    boolean update(Acampante acampante);

    boolean delete(Integer id);

    Acampante findById(Integer id);
}
