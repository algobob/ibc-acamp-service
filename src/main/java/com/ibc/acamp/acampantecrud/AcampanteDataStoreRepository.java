package com.ibc.acamp.acampantecrud;

import com.ibc.acamp.support.PropertiesHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.SQLException;
import java.util.List;

public class AcampanteDataStoreRepository implements DataStoreRepository {

    private Logger LOGGER = LoggerFactory.getLogger("AcampanteDataStoreRepository");

    private static final String DB_DRIVER = PropertiesHelper.getProps("db.jdbc.driver");
    private static final String DB_CONNECTION = PropertiesHelper.getProps("db.jdbc.url");
    private static final String DB_USER = PropertiesHelper.getProps("db.jdbc.user");
    private static final String DB_PASSWORD = PropertiesHelper.getProps("db.jdbc.password");

    private Dao<Acampante,String> acampanteDao;
    private Sql2o sql2o;
    public AcampanteDataStoreRepository() throws SQLException {

        JdbcConnectionSource connectionSource = new JdbcConnectionSource(DB_CONNECTION, DB_USER, DB_PASSWORD);
        acampanteDao = DaoManager.createDao(connectionSource, Acampante.class);
    }

    @Override
    public boolean save(Acampante acampante) throws SQLException {

        LOGGER.info("[Acampante Repository] saving acampante...acampante = {}",acampante);


        try {
            acampanteDao.create(acampante);

            LOGGER.info("[Acampante Repository] Acampante saved.");
            return true;

        }catch (SQLException ex){
            LOGGER.error("[Acamapante Respository] {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Acampante> fetch() throws SQLException {
        LOGGER.info("[Get all acampantes][data repository] fetching acampantes from db...");

        try{
            return acampanteDao.queryForAll();
        }catch (SQLException ex){
            LOGGER.error("[Get all acampantes][data repository] {}", ex.getMessage());
            throw ex;
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
