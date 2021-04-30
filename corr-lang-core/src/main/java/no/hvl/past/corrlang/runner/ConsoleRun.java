package no.hvl.past.corrlang.runner;


import no.hvl.past.corrlang.reporting.PrintStreamReportFacade;
import no.hvl.past.corrlang.reporting.ReportFacade;
import java.util.*;

public class ConsoleRun extends AbstractRun {

    public static final String GOAL_PREFIX = "g:";
    public static final String SILENT_EXECUTION_PARAM = "silent";
    public static final String PARAM_PREFIX = "-";
    public static final String DEFAULT_GOAL = "HELP";
    public static final String KV_SEPARATOR_VARIANT_A = ":";
    public static final String KV_SEPARATOR_VARIANT_B = "=";

    public ConsoleRun(ReportFacade reportFacade,
                      Properties properties,
                      List<String> corrSpecs) {
        super(reportFacade, properties, corrSpecs);
    }

    public static void main(String[] args) {
        final List<String> corrSpecs = new ArrayList<>();
        Properties properties = new Properties();
        String goal = null;
        boolean isSilent = false;
        for (String arg : args) {
            if (arg.startsWith(GOAL_PREFIX)) {
                if (goal != null) {
                    System.out.println("ERROR: Cannot execute more than one goal!");
                    System.exit(99);
                } else {
                    goal = arg.substring(2);
                }
            } else if (arg.startsWith(PARAM_PREFIX)) {
                String rest = arg;
                while (rest.startsWith(PARAM_PREFIX)) {
                    rest = rest.substring(1);
                }
                if (rest.equals(SILENT_EXECUTION_PARAM)) {
                    isSilent = true;
                } else {
                    int divider;
                    if (rest.contains(KV_SEPARATOR_VARIANT_A)) {
                        divider = rest.indexOf(KV_SEPARATOR_VARIANT_A);
                        properties.put(rest.substring(0, divider), rest.substring(divider + 1));
                    } else if (rest.contains(KV_SEPARATOR_VARIANT_B)) {
                        divider = rest.indexOf(KV_SEPARATOR_VARIANT_B);
                        properties.put(rest.substring(0, divider), rest.substring(divider + 1));
                    } else {
                        properties.put(rest, null);
                    }
                }
            } else {
                corrSpecs.add(arg);
            }
        }
        if (goal == null) {
            goal = DEFAULT_GOAL;
        }
        ConsoleRun run = new ConsoleRun(new PrintStreamReportFacade(System.out), properties, corrSpecs);
        try {
            run.initialise(isSilent);
            run.executeGoal(goal, Void.class);
        } catch (Throwable t) {
            t.printStackTrace(System.out);
            System.exit(99);
        }
    }
}
