package no.hvl.past.corrlang.goals;

import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.DependencyInjectionContainer;

public class HelpGoal extends SystemGoal {

    static final String NAME = "HELP";
    private static final String DESC = "Prints out more information about existing Goals and how to use CorrLang in general";

    public HelpGoal(ReportFacade reportFacade, DependencyInjectionContainer container) {
        super(reportFacade, NAME, DESC, container);
    }

    @Override
    protected void doIt() throws Throwable {
        getReportFacade().reportInfo("Usage:\n\nshell> {$corrlang-bin} <LIST OF CORR_SPEC_FILEs> <LIST OF GOALs> <PARAMETERS>\n\n");
        getReportFacade().reportInfo("CORR_SPEC_FILEs are referenced as regular file references (e.g. 'test1.corrlang', '../../docs/test2.corrlang', 'file:///home/users/test3.corrlang' etc.\n\n");
        getReportFacade().reportInfo("GOALs have the form GOAL:<GOAL_NAME>:<OPTIONAL GOAL PARAM> ('GOAL:...' can be replaced by 'g:...')\n");
        getReportFacade().reportInfo("The following goals exist:");
        // TODO add reference to the foal facade to execute them
    }
}
