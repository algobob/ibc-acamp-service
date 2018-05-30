package com.ibc.acamp.support;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class AcampanteRepositoryHelper {

    private static final String DB_CONNECTION = PropertiesHelper.getProps("db.jdbc.url");
    private static final String DB_USER = PropertiesHelper.getProps("db.jdbc.user");
    private static final String DB_PASSWORD = PropertiesHelper.getProps("db.jdbc.password");
    private static final String DB_SCHEMA = PropertiesHelper.getProps("db.jdbc.schema");

    private Sql2o sql2o;

    public AcampanteRepositoryHelper(){
        sql2o = new Sql2o(DB_CONNECTION,DB_USER, DB_PASSWORD);
    }

    public void insertDumbData() {
        String sql = String.format("INSERT INTO %s.acampantes(nome, sexo, idade) " +
                "VALUES ('maria', 'feminino', 12)," +
                "('joao', 'masculino', 13) ", DB_SCHEMA);

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cleanAcampanteTable() {
        String sql = String.format("truncate %s.acampantes;",DB_SCHEMA);

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}