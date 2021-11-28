package no.hvl.past.corrlang;

import no.hvl.past.TestBase;
import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.execution.ExecutionFacade;
import no.hvl.past.corrlang.execution.goals.AbstractGoal;
import no.hvl.past.corrlang.reporting.PrintStreamReportFacade;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.keys.Key;
import no.hvl.past.systems.Sys;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Tests the functionality on the old scenario used in the ECMFA20 paper
 * comprising SALES, INVOICES and EMPLOYEES
 */
public class ECMFA20Example extends SystemTest {

    private static final String CORRSPEC_FILE = "systemtests/ecmfa20GraphQLSpec.corr";
    private static final String CORRSPEC_EMPTY_FILE = "systemtests/ecmfa20GraphQLSpecEmpty.corr";

    @Test
    public void testWriteFile() throws Throwable {
        runGoal(CORRSPEC_FILE,"GQLFile");
        SyntacticalResult result = testRunner.getParseResult();
         CorrSpec corrSpec = result.getCorrSpecWithName("Fed").get();
        List<Key> keys = corrSpec.getComprSys().keys().collect(Collectors.toList());
        assertEquals(4, keys.size());
    }

    @Test
    public void testAll() throws Throwable {
        runGoal(CORRSPEC_FILE, "GQLFederation");
        // TODO test the running endpoint
    }


    @Test
    public void testPlottingEmpty() throws Throwable {
        runGoal(CORRSPEC_EMPTY_FILE, "GQLPlot");
        // TODO compare file contents
    }



    @Test
    public void testPlottingFull() throws Throwable {
        runGoal(CORRSPEC_FILE, "GQLPlot");
        // TODO compare file contents
    }


}
