package no.hvl.past.di;

import no.hvl.past.graph.Universe;
import no.hvl.past.graph.UniverseImpl;
import no.hvl.past.util.FileSystemAccessPoint;
import no.hvl.past.util.FileSystemUtils;
import no.hvl.past.util.ShouldNotHappenException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;


import java.io.IOException;

@Configuration
public class DIConfiguration {


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
    @Lazy
    public FileSystemAccessPoint fileSystemAccessPoint(PropertyHolder propertyHolder) {
        try {
            return FileSystemAccessPoint.create(propertyHolder.getBaseDir(), fileSystemUtils());
        } catch (IOException e) {
            throw new ShouldNotHappenException(DIConfiguration.class,e);
        }
    }

}
