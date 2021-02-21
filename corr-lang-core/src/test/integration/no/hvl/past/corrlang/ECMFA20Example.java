package no.hvl.past.corrlang;

import no.hvl.past.TestBase;
import no.hvl.past.corrlang.goals.GoalFacade;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.domainmodel.traverser.TraverserFacade;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import org.junit.Test;

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
    public void testAll() throws Exception {
        requiresSpecificInstalledComponents();

        DependencyInjectionContainer diContainer = DependencyInjectionContainer.create();
        diContainer.getPropertyHolder().getPropertyAndSetDefaultIfNecessary("log.level", "DEBUG");
        diContainer.setUpLogging();
        ReportFacade reportFacade = new ReportFacade(System.out);
        TraverserFacade traverserFacade = new TraverserFacade(reportFacade, diContainer.getPluginRegistry(), diContainer.getUniverse());
        SyntacticalResult result = new SyntacticalResult();
        result = ParserChain.parseFromFile(getResourceFolderItem(CORRSPEC_FILE), reportFacade, result);
        GoalFacade facade = new GoalFacade();
        facade.runGoal("FEDERATION_RUNTIME", "GRAPH_QL", diContainer, reportFacade,result, traverserFacade);

        Thread.sleep(1000 * 60 * 60);
        // TODO test the running endpoint
    }


}
