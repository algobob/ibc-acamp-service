package com.ibc.acamp.acampantecrud;

import java.util.List;

public interface DataStoreRepository {
    boolean save(Acampante data);
    List<Acampante> fetch();
}
