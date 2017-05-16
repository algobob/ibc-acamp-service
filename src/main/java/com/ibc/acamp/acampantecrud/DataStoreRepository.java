package com.ibc.acamp.acampantecrud;

@FunctionalInterface
public interface DataStoreRepository {
    boolean save(Acampante data);
}
