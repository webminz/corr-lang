package io.corrlang.engine.execution.goals;

import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.reporting.ReportFacade;
import org.springframework.beans.factory.annotation.Autowired;



public class HelpGoal extends SystemGoal {

    @Autowired
    private ReportFacade reportFacade;

    static final String NAME = "HELP";

    public HelpGoal() {
        super(NAME);
    }


    @Override
    public void executeTransitive(SyntacticalResult syntaxGraph) {
        reportFacade.reportInfo("Usage:\n\nshell> {$corrlang-bin} <NON-EMPTY LIST OF CORR_SPEC_FILEs> <GOAL> <LIST OF OPTIONAL PARAMETERs>\n\n");
        reportFacade.reportInfo("CORR_SPEC_FILEs can be referenced using relative or absolute addresses or URIs, e.g.: \n" +
                " *  test1.corrlang\n" +
                " * ../../docs/test2.corrlang\n" +
                " * C:\\Users\\admin\\Documents\\test4.corrlang\n" +
                " * file:///home/users/test4.corrlang\n" +
                " * http://www.example.org/content/test5.corrlang\n" +
                " ...\n\n");
        reportFacade.reportInfo("A GOAL is identified via its name and starts with the prefix 'g:...'. The goal  must defined in one of the given corrspec files. Moreover, there exist the following built-in goals:\n" +
                " * HELP: The goal you are currently executing...\n" +
                " * INFO: Prints system information (build & version data, installed plugins, and active parameters and their value) in the command line.\n" +
                " * PARSE: Only parses the content of the given corrspec files and performs some simple validations.\n\n");
        reportFacade.reportInfo("\nFinally, PARAMETERs start with '--'. For a complete list of all parameters resort to the developer documentation ;-P");
        reportFacade.reportInfo("That's all folks! (questions, discussions, ideas? --> contact past@hvl.no!) ");
    }


}
