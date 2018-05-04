package com.ibc.acamp.acampantecrud.helper;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class AcampanteRepositoryHelper {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test.db;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    private Sql2o sql2o;

    public AcampanteRepositoryHelper(){
        sql2o = new Sql2o(DB_CONNECTION,DB_USER, DB_PASSWORD);
    }

    public void createTables(){
        String sql = "CREATE TABLE acampantes (" +
                "  id int NOT NULL AUTO_INCREMENT," +
                "  nome varchar(255)," +
                "  sexo varchar(15)," +
                "  idade int" +
                ");";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void insertDumbData() {
        String sql =
                "INSERT INTO acampantes(nome, sexo, idade) " +
                        "VALUES ('maria', 'feminino', 12)," +
                        "('joao', 'masculino', 13) ";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void dropTables(){
        String sql = "drop table if exists acampantes;";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
