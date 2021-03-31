package no.hvl.past.di;

import no.hvl.past.corrlang.reporting.ReportFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages = { "no.hvl.past.corrlang.execution.goals",  "no.hvl.past.corrlang.execution.traverser"})
public class CorrLangConfiguration {



    @Bean
    public ReportFacade defaultReportFacade() {
        return new ReportFacade(System.out);
    }

}
