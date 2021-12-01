package io.corrlang.plugins;

import no.hvl.past.MetaRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class BuiltinPlugins {


    @Bean
    public PlottingTechSpace.AdapterFactory plotPNGTechSpace() {
        return new PlottingTechSpace.AdapterFactory(PlottingTechSpace.IMAGE);
    }


    @Bean
    @Scope("singleton")
    public MetaRegistry registry() {
        return new RegistryImpl();
    }


    @Bean
    @Scope("singleton")
    @Lazy
    public ServerStarter serverStarter() {
        return new ServerStarter();
    }


    // TODO register beans for all the builtin stuff




}
