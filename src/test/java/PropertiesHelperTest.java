import com.ibc.acamp.support.PropertiesHelper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PropertiesHelperTest {

    @Test
    public void shouldGetPropertiesFromLocalEnv() throws IOException {
        PropertiesHelper.load("local_test");
        assertThat(PropertiesHelper.getProps("env_name"),is("local_test"));
    }
}
