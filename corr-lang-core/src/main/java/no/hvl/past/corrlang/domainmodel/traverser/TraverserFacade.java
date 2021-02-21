package no.hvl.past.corrlang.domainmodel.traverser;

import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.graph.Universe;
import no.hvl.past.plugin.MetaRegistry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TraverserFacade {

    interface TraverserFactory {

        AbstractTraverser create(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe);

    }

    private final ReportFacade reportFacade;
    private final MetaRegistry metaRegistry;
    private final Universe universe;
    private final Map<Class<? extends AbstractTraverser>, TraverserFactory> factories;

    public TraverserFacade(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe) {
        this.reportFacade = reportFacade;
        this.metaRegistry = metaRegistry;
        this.universe = universe;
        this.factories = new LinkedHashMap<>();
        this.factories.put(CreateFormalAlignmentTraverser.class, new TraverserFactory() {
            @Override
            public AbstractTraverser create(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe) {
                return new CreateFormalAlignmentTraverser(reportFacade,metaRegistry,universe);
            }
        });
        this.factories.put(IdentifyTechSpaceTraverser.class, new TraverserFactory() {
            @Override
            public AbstractTraverser create(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe) {
                return new IdentifyTechSpaceTraverser(reportFacade, metaRegistry);
            }
        });
        this.factories.put(LinkElementsTraverser.class, new TraverserFactory() {
            @Override
            public AbstractTraverser create(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe) {
                return new LinkElementsTraverser(reportFacade);
            }
        });
        this.factories.put(LinkEndpointsTraverser.class, new TraverserFactory() {
            @Override
            public AbstractTraverser create(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe) {
                return new LinkEndpointsTraverser(reportFacade);
            }
        });
        this.factories.put(LoadTechSpaceTraverser.class, new TraverserFactory() {
            @Override
            public AbstractTraverser create(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe) {
                return new LoadTechSpaceTraverser(reportFacade, metaRegistry);
            }
        });
        this.factories.put(ParseEndpointSchemaTraverser.class, new TraverserFactory() {
            @Override
            public AbstractTraverser create(ReportFacade reportFacade, MetaRegistry metaRegistry, Universe universe) {
                return new ParseEndpointSchemaTraverser(reportFacade);
            }
        });

    }

    public void runOn(Class<? extends AbstractTraverser> traverserType, SyntacticalResult syntacticalResult, Set<AbstractTraverser> alreadyExecuted) {
        AbstractTraverser abstractTraverser = this.factories.get(traverserType).create(reportFacade, metaRegistry, universe);
        if (abstractTraverser.canRun(alreadyExecuted)) {
            abstractTraverser.run(syntacticalResult, alreadyExecuted);
        } else {
            Set<Class<? extends AbstractTraverser>> dependencies = abstractTraverser.dependenciesThatMustBeRun(alreadyExecuted);
            if (!dependencies.isEmpty()) {
                for (Class<? extends AbstractTraverser> dep : dependencies) {
                    runOn(dep, syntacticalResult, alreadyExecuted);
                }
                abstractTraverser.run(syntacticalResult, alreadyExecuted);
            } else {
                abstractTraverser.failed();
            }
        }
        alreadyExecuted.add(abstractTraverser);
    }

}
