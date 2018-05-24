import com.ibc.acamp.support.PropertiesHelper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PropertiesHelperTest {

    @Test
    public void shouldGetPropertiesFromTestEnv() throws IOException {
        PropertiesHelper.load("test");
        assertThat(PropertiesHelper.getProps("env_name"),is("test"));
    }
}
