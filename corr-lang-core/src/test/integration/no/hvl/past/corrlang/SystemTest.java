package no.hvl.past.corrlang;

import no.hvl.past.TestBase;
import no.hvl.past.util.Pair;
import org.junit.Before;

import java.io.File;
import java.util.Properties;

public class SystemTest extends TestBase {

    protected File workdir;

    protected SystemTestRun testRunner;

    protected SystemTestRun.TestReportFacade reportFacade;

    @Before
    public void setUp() {
        workdir = getResourceFolderItem("systemtests/workdir");
    }

    protected void runGoal(String corrSpecFileName, String goalName) throws Throwable {
        File file = new File(getResourceFolderItem("systemtests"), corrSpecFileName);
        Pair<SystemTestRun, SystemTestRun.TestReportFacade> r = SystemTestRun.create(workdir, file);
        testRunner = r.getFirst();
        reportFacade = r.getSecond();
        testRunner.testRun(goalName, false);
    }

    protected void runGoal(String corrSpecFileName, String goalName, Properties properties) throws Throwable {
        File file = new File(getResourceFolderItem("systemtests"), corrSpecFileName);
        Pair<SystemTestRun, SystemTestRun.TestReportFacade> r = SystemTestRun.create(workdir, file, properties);
        testRunner = r.getFirst();
        reportFacade = r.getSecond();
        testRunner.testRun(goalName, false);
    }
}
