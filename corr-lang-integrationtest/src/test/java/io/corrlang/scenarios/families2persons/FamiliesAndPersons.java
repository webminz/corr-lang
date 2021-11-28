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

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class FamiliesAndPersons extends SystemTest {

    private static final String CORRSPEC_FILE = "familiesPersons.corr";

    @Test
    public void testRunHelpGoal() throws Throwable {
        runGoal(CORRSPEC_FILE, "HELP");
        String infos = reportFacade.getInfos();
        String expected = "aa";
        assertEquals(expected, infos);
    }

    @Test
    public void testRunVerify() throws Throwable {
        Properties properties = new Properties();
        properties.put("useConfig", "/Users/past/Documents/dev/bx/corrlang-performance/checkFileBased-execution/solutions/corrlang/corrlang.conf");
        runGoal(CORRSPEC_FILE, "Check", properties);
        // TODO check expected file
    }



}
