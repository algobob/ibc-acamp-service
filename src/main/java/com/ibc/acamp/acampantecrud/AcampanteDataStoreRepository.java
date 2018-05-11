package com.ibc.acamp.acampantecrud;

import com.ibc.acamp.suport.PropertiesHelper;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class AcampanteDataStoreRepository implements DataStoreRepository {

    private static final String DB_DRIVER = PropertiesHelper.getProps("db.jdbc.driver");
    private static final String DB_CONNECTION = PropertiesHelper.getProps("db.jdbc.url");
    private static final String DB_USER = PropertiesHelper.getProps("db.jdbc.user");
    private static final String DB_PASSWORD = PropertiesHelper.getProps("db.jdbc.password");

    private Sql2o sql2o;

    public AcampanteDataStoreRepository(){
        sql2o = new Sql2o(DB_CONNECTION,DB_USER, DB_PASSWORD);
    }


    @Override
    public boolean save(Acampante acampante) {

        String sql =
                "INSERT INTO acampantes(nome, sexo, idade) " +
                        "VALUES (:nome, :sexo, :idade)";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("nome", acampante.getNome())
                    .addParameter("sexo", acampante.getSexo())
                    .addParameter("idade", acampante.getIdade())
                    .executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Acampante> fetch() {
        String sql = "SELECT * FROM acampantes";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Acampante.class);
        }
    }

    @Override
    public boolean update(Acampante acampante) {

        String sql =
                "update acampantes set nome = :nome, sexo = :sexo, idade = :idade where id = :id;";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("nome", acampante.getNome())
                    .addParameter("sexo", acampante.getSexo())
                    .addParameter("idade", acampante.getIdade())
                    .addParameter("id", acampante.getId())
                    .executeUpdate();

            return true;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }
}
