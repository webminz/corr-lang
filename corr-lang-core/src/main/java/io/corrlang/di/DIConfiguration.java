package io.corrlang.di;

import ch.qos.logback.core.joran.spi.JoranException;
import no.hvl.past.MetaRegistry;
import no.hvl.past.graph.Universe;
import no.hvl.past.graph.UniverseImpl;
import no.hvl.past.util.FileSystemAccessPoint;
import no.hvl.past.util.FileSystemUtils;
import no.hvl.past.util.ShouldNotHappenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;


import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = { "io.corrlang.plugins"})
public class DIConfiguration {

    @Bean
    @Scope("singleton")
    public MetaRegistry registry() {
        return new RegistryImpl();
    }

    @Bean
    @Scope("prototype")
    public Universe universe() {
        return new UniverseImpl(UniverseImpl.EMPTY);
    }

    @Bean
    @Scope("singleton")
    @Lazy
    public FileSystemUtils fileSystemUtils() {
        return FileSystemUtils.getInstance();
    }

    @Bean
    @Scope("singleton")
    public PropertyHolder propertyHolder(@Autowired Environment environment) throws IOException, JoranException {
        return PropertyHolder.bootstrap("corrlang", environment);
    }

    @Bean
    @Lazy
    public FileSystemAccessPoint fileSystemAccessPoint(PropertyHolder propertyHolder) {
        try {
            return FileSystemAccessPoint.create(propertyHolder.getBaseDir(), fileSystemUtils());
        } catch (IOException e) {
            throw new ShouldNotHappenException(DIConfiguration.class,e);
        }
    }

}
