package support;

import com.ibc.acamp.acampantecrud.Acampante;
import com.ibc.acamp.support.PropertiesHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class AcampanteRepositoryHelper {

    private static final String DB_CONNECTION = PropertiesHelper.getProps("db.jdbc.url");
    private static final String DB_USER = PropertiesHelper.getProps("db.jdbc.user");
    private static final String DB_PASSWORD = PropertiesHelper.getProps("db.jdbc.password");

    private Dao<Acampante,String> acampanteDao;

    public AcampanteRepositoryHelper() throws SQLException {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(DB_CONNECTION, DB_USER, DB_PASSWORD);
        acampanteDao = DaoManager.createDao(connectionSource, Acampante.class);
    }

    public void createTables() throws SQLException {

        TableUtils.createTable(acampanteDao);
    }

    public void insertDumbData() throws SQLException {
        acampanteDao.create(buildAcamapante("João",15, "Masculino"));
        acampanteDao.create(buildAcamapante("Maria",17, "Feminino"));
    }

    private Acampante buildAcamapante(String nome, int idade, String sexo) {
        return Acampante.builder().nome(nome).idade(idade).sexo(sexo).build();
    }

    public void dropTables() throws SQLException {
        TableUtils.dropTable(acampanteDao, true);
    }
}
