package no.hvl.past.corrlang.goals;

import no.hvl.past.corrlang.domainmodel.traverser.TraverserFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.server.Webserver;
import no.hvl.past.corrlang.reporting.ReportFacade;

public abstract class ServerGoal extends LanguageGoal {

    private final Webserver webserver;

    public ServerGoal(ReportFacade reportFacade,
                      String goalName,
                      String shortDesc,
                      DependencyInjectionContainer container,
                      SyntacticalResult model,
                      TraverserFacade traverserFacade) {
        super(reportFacade, goalName, shortDesc, container, model, traverserFacade);
        this.webserver = container.getServer().getWebserverStartIfNecessary();
    }

    public Webserver getWebserver() {
        return webserver;
    }
}
