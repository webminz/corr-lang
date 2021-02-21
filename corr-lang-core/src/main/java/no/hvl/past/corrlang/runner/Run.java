package no.hvl.past.corrlang.runner;

import no.hvl.past.corrlang.domainmodel.traverser.TraverserFacade;
import no.hvl.past.corrlang.goals.GoalFacade;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.corrlang.reporting.ReportFacade;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Run {

    private static final String LOGO = "╔═╗┌─┐┬─┐┬─┐╦  ┌─┐┌┐┌┌─┐\n" +
            "║  │ │├┬┘├┬┘║  ├─┤││││ ┬\n" +
            "╚═╝└─┘┴└─┴└─╩═╝┴ ┴┘└┘└─┘";


    private static final String ARGUMENT_PREFIX = "--";
    private static final String GOAL_ARGUMENT = "--goal:";
    private static final String TECH_ARGUMENT = "--technology:";
    private static final String OUTOUT_ARGUMENT = "--out:";

    private final List<File> corrSpecs;
    private final Map<String, String> arguments;
    private final List<String> goalNames;
    private final ReportFacade reportFacade;
    private DependencyInjectionContainer container;
    private TraverserFacade traverserFacade;
    private GoalFacade goalFacade;
    private SyntacticalResult syntacticalResult;


    private Run(List<File> corrSpecs, Map<String, String> arguments, List<String> goalNames) {
        this.corrSpecs = corrSpecs;
        this.arguments = arguments;
        this.goalNames = goalNames;
        this.reportFacade = new ReportFacade(System.out);
    }

    private void run() {
        try {
            createDIContainer();
            printLogoAndSysInfo();
            executeSystemGoals();
            parseCorrSpecFiles();
            executeLanguageGoals();
        } catch (Throwable throwable) {
            reportFacade.reportError(throwable);
        }
    }

    private void executeLanguageGoals() {
        for (String current : goalNames) {
            if (current.contains(":")) {
                String goal = current.substring(0, current.indexOf(':'));
                String arg = current.substring(current.indexOf(':') + 1);
                if (goalFacade.isKnownGoal(goal)) {
                    goalFacade.runGoal(goal,arg,this.container,reportFacade,syntacticalResult,traverserFacade);
                } else {
                    reportFacade.reportError("Goal '" + goal + "' is not known");
                }
            } else {
                if (goalFacade.isKnownGoal(current)) {
                    goalFacade.runGoal(current,null,this.container,reportFacade,syntacticalResult,traverserFacade);
                } else {
                    reportFacade.reportError("Goal '" + current + "' is not known");
                }
            }
        }
    }

    private void parseCorrSpecFiles() throws IOException {
        this.syntacticalResult = new SyntacticalResult();
        for (File corrSpecFile : corrSpecs) {
            this.syntacticalResult = ParserChain.parseFromFile(corrSpecFile, reportFacade, this.syntacticalResult);
        }
        this.traverserFacade = new TraverserFacade(reportFacade, this.container.getPluginRegistry(), this.container.getUniverse());
    }

    private void executeSystemGoals() {
        Iterator<String> i = this.goalNames.iterator();
        while (i.hasNext()) {
            String current = i.next();
            if (current.contains(":")) {
                String goal = current.substring(0, current.indexOf(':'));
                String arg = current.substring(current.indexOf(':') + 1);
                if (goalFacade.isSystemGoal(goal)) {
                    i.remove();
                    goalFacade.runGoal(goal,arg,this.container,this.reportFacade,null,null);
                }
            } else {
                if (goalFacade.isSystemGoal(current)) {
                    i.remove();
                    goalFacade.runGoal(current, null, this.container, this.reportFacade, null, null);
                }
            }
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
        this.goalFacade = new GoalFacade();
    }

    private void printLogoAndSysInfo() {
        if (!this.container.getPropertyHolder().isHideLogo()) {
            System.out.print(LOGO);
            System.out.println("   Version: 1.0\n");
        }
    }



    public static void main(String[] args) {
        final List<File> corrSpecs = new ArrayList<>();
        final Map<String, String> arguments = new LinkedHashMap<>();
        final List<String> goalNames = new ArrayList<>();
        for (String arg : args) {
            if (arg.startsWith("g:") || arg.startsWith("GOAL:")) {
                goalNames.add(arg.substring(arg.indexOf(':') + 1));
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
                File file = new File(arg);
                if (file.exists()) {
                    corrSpecs.add(file);
                }
            }
        }
        Run run = new Run(corrSpecs, arguments, goalNames);
        run.run();
    }
}
