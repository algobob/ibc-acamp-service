package com.ibc.acamp.support;

import com.ibc.acamp.acampantecrud.Acampante;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static com.ibc.acamp.support.PropertiesHelper.getJdbcPassword;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcSchema;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcUrl;
import static com.ibc.acamp.support.PropertiesHelper.getJdbcUsername;

public class AcampanteRepositoryHelper {

    private static final String DB_CONNECTION = getJdbcUrl();
    private static final String DB_USER = getJdbcUsername();
    private static final String DB_PASSWORD = getJdbcPassword();
    private static final String DB_SCHEMA = getJdbcSchema();

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

    public List<Acampante> fetch(){
        String sql = String.format("select * from %s.acampantes;",DB_SCHEMA);

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Acampante.class);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }
}
