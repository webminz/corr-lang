package no.hvl.past;

import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.di.PropertyHolder;
import org.junit.jupiter.api.BeforeEach;

import java.util.Properties;

public abstract class TestWithDIContainer extends TestBase {

    private DependencyInjectionContainer diContainer;

    public DependencyInjectionContainer getDiContainer() {
        return diContainer;
    }

    protected void configure(Properties properties) {
        String resourceRoot =  TestBase.getResourceFolderItem(".").getAbsolutePath();
        properties.put(PropertyHolder.BASE_DIR, resourceRoot);
        properties.put(PropertyHolder.SSL_ALLOW_ALL, true);
    }

    public void setUp() throws Exception {
        Properties p = new Properties();
        configure(p);
        diContainer = DependencyInjectionContainer.create("UnitTest", p);
    }
}
