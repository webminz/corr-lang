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

public class KarlEriksApplication extends TestBase {


    @Test
    public void testKarlErikDemo() throws Throwable {
        DependencyInjectionContainer diContainer = DependencyInjectionContainer.create(System.getProperty("user.dir") + "/execution");
        diContainer.setUpLogging();
        ReportFacade reportFacade = new PrintStreamReportFacade(System.out);
        ExecutionFacade executor = new ExecutionFacade(diContainer);
        SyntacticalResult result = new SyntacticalResult();
        result = ParserChain.parseFromFile(getResourceFolderItem("systemtests/karlErikDemo.corrlang"), reportFacade, result);
        AbstractGoal.runGoal(diContainer,executor,result,"GQLFederation");

    }
}
