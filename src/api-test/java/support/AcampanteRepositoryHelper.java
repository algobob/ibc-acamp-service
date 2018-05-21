package support;

import com.ibc.acamp.support.PropertiesHelper;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class AcampanteRepositoryHelper {

    private static final String DB_CONNECTION = PropertiesHelper.getProps("db.jdbc.url");
    private static final String DB_USER = PropertiesHelper.getProps("db.jdbc.user");
    private static final String DB_PASSWORD = PropertiesHelper.getProps("db.jdbc.password");

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
