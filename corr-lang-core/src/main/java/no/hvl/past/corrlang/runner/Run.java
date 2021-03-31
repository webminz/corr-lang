package no.hvl.past.corrlang.runner;

import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.execution.ExecutionFacade;
import no.hvl.past.corrlang.execution.goals.AbstractGoal;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Run {

    private static final String LOGO = "╔═╗┌─┐┬─┐┬─┐╦  ┌─┐┌┐┌┌─┐\n" +
            "║  │ │├┬┘├┬┘║  ├─┤││││ ┬\n" +
            "╚═╝└─┘┴└─┴└─╩═╝┴ ┴┘└┘└─┘";


    private static final String ARGUMENT_PREFIX = "--";

    private final List<String> corrSpecs;
    private final String goal;
    private Map<String, String> arguments;
    private final ReportFacade reportFacade;
    private DependencyInjectionContainer container;
    private ExecutionFacade executor;
    private SyntacticalResult syntacticalResult;


    private Run(List<String> corrSpecs, Map<String, String> arguments, String goal) {
        this.corrSpecs = corrSpecs;
        this.goal = goal;
        this.arguments = arguments;
        this.reportFacade = new ReportFacade(System.out);
    }

    private void run() {
        try {
            createDIContainer();
            printLogoAndSysInfo();
            parseCorrSpecFiles();
            executeLanguageGoals();
        } catch (Throwable throwable) {
            reportFacade.reportError(throwable);
        }
    }



    private void executeLanguageGoals() {
        try {
            AbstractGoal.runGoal(container, executor, syntacticalResult, goal);
        } catch (Throwable throwable) {
            reportFacade.reportError(throwable);
            System.exit(99);
        }
    }

    private void parseCorrSpecFiles() throws IOException {
        this.syntacticalResult = new SyntacticalResult();
        FileSystemUtils fsUtils = this.container.getFSUtils();
        for (String corrSpecFile : corrSpecs) {
            File file = fsUtils.file(corrSpecFile);
            if (!file.exists()) {
                reportFacade.reportError("File '" + file.getAbsolutePath() + "' does not exist! It will be ignored!");
            }
            this.syntacticalResult = ParserChain.parseFromFile(file, reportFacade, this.syntacticalResult);
        }
    }



    private void createDIContainer() throws IOException {
        if (arguments.containsKey(PropertyHolder.CONFIG_FILE)) {
            this.container = DependencyInjectionContainer.create(new File(arguments.get(PropertyHolder.CONFIG_FILE)));
        } else if (arguments.containsKey(PropertyHolder.BASE_DIR)) {
            this.container = DependencyInjectionContainer.create(arguments.get(PropertyHolder.BASE_DIR));
        } else {
            this.container = DependencyInjectionContainer.create();
        }
        this.executor = new ExecutionFacade(container);
    }

    private void printLogoAndSysInfo() {
        if (!this.container.getPropertyHolder().isHideLogo()) {
            System.out.print(LOGO);
            System.out.println("   Version: 1.0\n");
        }
    }



    public static void main(String[] args) {
        final List<String> corrSpecs = new ArrayList<>();
        final Map<String, String> arguments = new LinkedHashMap<>();
        String goal = null;
        for (String arg : args) {
            if (arg.startsWith("\"")) {
                if (goal != null) {
                    System.err.println("ERROR: Cannot execute more than one goal!");
                    System.exit(99);
                } else {
                    goal = arg.substring(1, arg.length() - 1);
                }
            } else if (arg.startsWith("-")) {
                String rest = arg;
                while (rest.startsWith("-")) {
                    rest = rest.substring(1);
                }
                int divider;
                if (rest.contains(":")) {
                    divider = rest.indexOf(":");
                    arguments.put(rest.substring(0, divider), rest.substring(divider + 1));
                } else if (rest.contains("=")) {
                    divider = rest.indexOf("=");
                    arguments.put(rest.substring(0, divider), rest.substring(divider + 1));
                } else {
                    arguments.put(rest, "");
                }
            } else {
                corrSpecs.add(arg);
            }
        }
        if (goal == null) {
            goal = "HELP";
        }
        Run run = new Run(corrSpecs, arguments, goal);
        run.run();
    }
}
