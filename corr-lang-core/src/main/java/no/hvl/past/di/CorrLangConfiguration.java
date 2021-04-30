package no.hvl.past.di;

import no.hvl.past.corrlang.reporting.PrintStreamReportFacade;
import no.hvl.past.corrlang.reporting.ReportFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "no.hvl.past.corrlang.execution.goals",  "no.hvl.past.corrlang.execution.traverser"})
public class CorrLangConfiguration {



    @Bean
    public ReportFacade defaultReportFacade() {
        return new PrintStreamReportFacade(System.out);
    }

}
