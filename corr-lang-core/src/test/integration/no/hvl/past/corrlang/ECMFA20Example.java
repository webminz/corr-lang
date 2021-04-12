package no.hvl.past.corrlang;

import no.hvl.past.TestBase;
import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.execution.ExecutionFacade;
import no.hvl.past.corrlang.execution.goals.AbstractGoal;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.keys.Key;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Tests the functionality on the old scenario used in the ECMFA20 paper
 * comprising SALES, INVOICES and EMPLOYEES
 */
public class ECMFA20Example extends TestBase {

    private String SALES_URL = "http://localhost:4011";
    private String INVOICES_URL = "http://localhost:4011";
    private String EMPLOYEES_URL = "http://localhost:4011";
    private String CORRSPEC_FILE = "spec.corr";

    @Test
    public void testWriteFile() throws Throwable {
        DependencyInjectionContainer diContainer = DependencyInjectionContainer.create(System.getProperty("user.dir") + "/execution");
        diContainer.setUpLogging();
        ReportFacade reportFacade = new ReportFacade(System.out);
        ExecutionFacade executor = new ExecutionFacade(diContainer);
        SyntacticalResult result = new SyntacticalResult();
        result = ParserChain.parseFromFile(getResourceFolderItem(CORRSPEC_FILE), reportFacade, result);
        AbstractGoal.runGoal(diContainer,executor,result,"GQLFile");
        CorrSpec corrSpec = result.getCorrSpecWithName("Fed").get();
        List<Key> keys = corrSpec.getComprSys().keys().collect(Collectors.toList());
        assertEquals(4, keys.size());
    }

    @Test
    public void testAll() throws Throwable {
       // requiresSpecificInstalledComponents();

        DependencyInjectionContainer diContainer = DependencyInjectionContainer.create(System.getProperty("user.dir") + "/execution");
     //   diContainer.getPropertyHolder().getPropertyAndSetDefaultIfNecessary("log.level", "DEBUG");
        diContainer.setUpLogging();
        ReportFacade reportFacade = new ReportFacade(System.out);
        ExecutionFacade executor = new ExecutionFacade(diContainer);
        SyntacticalResult result = new SyntacticalResult();
        result = ParserChain.parseFromFile(getResourceFolderItem(CORRSPEC_FILE), reportFacade, result);
        AbstractGoal.runGoal(diContainer,executor,result,"GQLFederation");

        // TODO test the running endpoint
    }


    @Test
    public void testKarlErikDemo() throws Throwable {
        DependencyInjectionContainer diContainer = DependencyInjectionContainer.create(System.getProperty("user.dir") + "/execution");
        diContainer.setUpLogging();
        ReportFacade reportFacade = new ReportFacade(System.out);
        ExecutionFacade executor = new ExecutionFacade(diContainer);
        SyntacticalResult result = new SyntacticalResult();
        result = ParserChain.parseFromFile(getResourceFolderItem("karlErikDemo.corrlang"), reportFacade, result);
        AbstractGoal.runGoal(diContainer,executor,result,"GQLFederation");

    }


}
