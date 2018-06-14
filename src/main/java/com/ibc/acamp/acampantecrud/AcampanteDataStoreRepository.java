package com.ibc.acamp.acampantecrud;

import com.ibc.acamp.support.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class AcampanteDataStoreRepository implements DataStoreRepository {

    private Logger LOGGER = LoggerFactory.getLogger("AcampanteDataStoreRepository");

    private static final String DB_DRIVER = PropertiesHelper.getProps("db.jdbc.driver");
    private static final String DB_CONNECTION = PropertiesHelper.getProps("db.jdbc.url");
    private static final String DB_USER = PropertiesHelper.getProps("db.jdbc.user");
    private static final String DB_PASSWORD = PropertiesHelper.getProps("db.jdbc.password");
    private static final String DB_SCHEMA= PropertiesHelper.getProps("db.jdbc.schema");

    private Sql2o sql2o;

    public AcampanteDataStoreRepository() throws ClassNotFoundException {

        sql2o = new Sql2o(DB_CONNECTION,DB_USER, DB_PASSWORD);
    }

    @Override
    public boolean save(Acampante acampante) {

        LOGGER.info("[Acampante Repository] saving acampante...acampante = {}",acampante);
        String sql = String.format("INSERT INTO %s.acampantes(nome, sexo, idade) VALUES (:nome, :sexo, :idade)", DB_SCHEMA);

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("nome", acampante.getNome())
                    .addParameter("sexo", acampante.getSexo())
                    .addParameter("idade", acampante.getIdade())
                    .executeUpdate();

            LOGGER.info("[Acampante Repository] Acampante saved.");
            return true;

        }catch (Exception ex){
            LOGGER.error("[Acamapante Respository] {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Acampante> fetch() {
        LOGGER.info("[Get all acampantes][data repository] fetching acampantes from db...");

        String sql = String.format("SELECT * FROM %s.acampantes",DB_SCHEMA);

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Acampante.class);
        }catch (Exception ex){
            LOGGER.error("[Get all acampantes][data repository] {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public boolean update(Acampante acampante) {

        String sql = String.format("update %s.acampantes set nome = :nome, sexo = :sexo, idade = :idade where id = :id;",DB_SCHEMA);

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

    @Override
    public boolean delete(Integer id) {

        String sql = String.format("delete from %s.acampantes where id = :id;",DB_SCHEMA);

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

            return true;

        }catch (Exception ex){
            LOGGER.error("[Acamapante Respository] {}", ex.getMessage());
        }

        return false;
    }
}
