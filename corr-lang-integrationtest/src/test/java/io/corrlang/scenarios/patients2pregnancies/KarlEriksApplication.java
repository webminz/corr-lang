package no.hvl.past.corrlang;

import no.hvl.past.TestBase;
import no.hvl.past.corrlang.execution.ExecutionFacade;
import no.hvl.past.corrlang.execution.goals.AbstractGoal;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.PrintStreamReportFacade;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.DependencyInjectionContainer;
import org.junit.Test;

public class KarlEriksApplication extends SystemTest {

    private static final String CORRSPEC = "karlErikDemo.corrlang";


    @Test
    public void testKarlErikDemo() throws Throwable {
        runGoal(CORRSPEC, "GQLFederation");

    }
}
