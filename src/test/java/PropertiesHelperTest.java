import com.ibc.acamp.support.PropertiesHelper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PropertiesHelperTest {

    @Test
    public void shouldGetPropertiesFromDevEnv() throws IOException {
        PropertiesHelper.load("dev");
        assertThat(PropertiesHelper.getProps("db.jdbc.url"),is("jdbc:postgresql://localhost:5432/"));
    }

    @Test
    public void shouldGetPropertiesFromTestEnv() throws IOException {
        PropertiesHelper.load("test");
        assertThat(PropertiesHelper.getProps("db.jdbc.url"),is("jdbc:h2:mem:test.db;DB_CLOSE_DELAY=-1"));
    }
}
