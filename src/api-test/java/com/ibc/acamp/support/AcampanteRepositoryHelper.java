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

    private String dbConnection;
    private String dbUser;
    private String dbPassword;
    private String dbSchema;

    private Sql2o sql2o;

    public AcampanteRepositoryHelper(){
        dbConnection = getJdbcUrl();
        dbUser = getJdbcUsername();
        dbPassword = getJdbcPassword();
        dbSchema = getJdbcSchema();
        sql2o = new Sql2o(dbConnection, dbUser, dbPassword);
    }

    public void insertDumbData() {
        String sql = String.format("INSERT INTO %s.acampantes(nome, sexo, idade) " +
                "VALUES ('maria', 'feminino', 12)," +
                "('joao', 'masculino', 13) ", dbSchema);

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cleanAcampanteTable() {
        String sql = String.format("truncate %s.acampantes;", dbSchema);

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<Acampante> fetch(){
        String sql = String.format("select * from %s.acampantes;", dbSchema);
        return sql2o.open().createQuery(sql).executeAndFetch(Acampante.class);
    }
}
