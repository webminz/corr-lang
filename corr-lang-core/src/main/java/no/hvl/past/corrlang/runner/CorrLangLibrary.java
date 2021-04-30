package no.hvl.past.corrlang.runner;

import no.hvl.past.corrlang.reporting.ReportFacade;

import java.util.List;
import java.util.Properties;

public class CorrLangLibrary extends AbstractRun {

    public CorrLangLibrary(Properties properties, List<String> corrSpecs) {
        super(ReportFacade.NULL_REPORT_FACADE, properties, corrSpecs);
    }

    public static <R> R execute(
            Properties properties,
            List<String> corrLangFiles,
            String goal,
            Class<R> expectedResultType) throws Throwable {
        CorrLangLibrary lib = new CorrLangLibrary(properties, corrLangFiles);
        lib.initialise(true);
        return lib.executeGoal(goal, expectedResultType);
    }

}
