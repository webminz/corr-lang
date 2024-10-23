package io.corrlang.plugins;

import io.corrlang.engine.reporting.PrintStreamReportFacade;
import io.corrlang.engine.reporting.ReportFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.corrlang.engine.execution.goals",  "io.corrlang.engine.execution.traverser"})
public class CorrLangConfiguration {



    @Bean
    @Qualifier("defaultReportFacade")
    public ReportFacade defaultReportFacade() {
        return new PrintStreamReportFacade(System.out);
    }

}
