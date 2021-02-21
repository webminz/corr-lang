package no.hvl.past.corrlang.goals;

import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.domainmodel.traverser.CreateFormalAlignmentTraverser;
import no.hvl.past.corrlang.domainmodel.traverser.TraverserFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.trees.QueryHandler;
import no.hvl.past.server.WebserviceRequestHandler;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceAdapterFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FederationRuntime extends ServerGoal {

    static final String GOAL_NAME = "FEDERATION_RUNTIME";
    private static final String SHORT_DESC = "Runs a web server providing a federated endpoint for the defined correspondence (Requires <1> parameter = The name of the technology that should be used to create the federation)";

    private final String argument;

    public FederationRuntime(
            ReportFacade reportFacade,
            DependencyInjectionContainer container,
            SyntacticalResult model,
            TraverserFacade traverserFacade,
            String argument) {
        super(reportFacade, GOAL_NAME, SHORT_DESC, container, model, traverserFacade);
        this.argument = argument;
    }

    @Override
    protected void doIt() throws Throwable {
        getTraverserFacade().runOn(CreateFormalAlignmentTraverser.class, getModel(), new HashSet<>()); // TODO make framework handle traverser dependencies
        Optional<TechSpaceAdapterFactory> extension = getContainer().getPluginRegistry().getExtension(argument, TechSpaceAdapterFactory.class);
        if (extension.isPresent()) {
            TechSpaceAdapter adapter = extension.get().createAdapter();
            CorrSpec corrSpec = getModel().allSpecs().iterator().next();
            List<QueryHandler> queryHandlers = new ArrayList<>();
            List<GraphMorphism> embeddings = new ArrayList<>();
            for (String ep : corrSpec.getEndpointsList()) {
                Endpoint endpoint = corrSpec.getEndpointRefs().get(ep);
                queryHandlers.add(endpoint.getAdaptor().get().queryHandler(endpoint.getLocationURL().getUrl(), null));
                embeddings.add(corrSpec.getSchemaTypeEmbeddings().get(ep));
            }
            WebserviceRequestHandler webserviceRequestHandler = adapter.federationQueryHandler(corrSpec.getComprehensiveSchema(),embeddings, queryHandlers);
            logger.debug("Request Handler for " + argument + " created");
            getWebserver().registerHandler(webserviceRequestHandler);
            Map<String, String> options = new HashMap<>();
            options.put("Access-Control-Allow-Origin", "*");
            options.put("Access-Control-Allow-Methods", "POST");
            options.put("Access-Control-Allow-Headers", "content-type,x-apollo-tracing");
            options.put("Vary", "Access-Control-Request-Headers");
            options.put("Connection", "keep-alive");
            options.put("Content-Length", "0");

            getWebserver().registerOptions(webserviceRequestHandler, options);
        }
    }
}
