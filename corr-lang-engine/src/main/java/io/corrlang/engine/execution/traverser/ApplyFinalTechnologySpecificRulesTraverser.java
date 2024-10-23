package io.corrlang.engine.execution.traverser;

import com.google.common.collect.Sets;
import io.corrlang.engine.domainmodel.Endpoint;
import io.corrlang.engine.domainmodel.Identification;
import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.execution.goals.AbstractGoal;
import io.corrlang.engine.domainmodel.Goal;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.MetaRegistry;
import io.corrlang.plugins.techspace.TechnologySpecificRules;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ApplyFinalTechnologySpecificRulesTraverser extends AbstractTraverser {

    @Autowired
    private PropertyHolder propertyHolder;

    @Autowired
    private MetaRegistry metaRegistry;


    public ApplyFinalTechnologySpecificRulesTraverser() {
        super("ApplyFinalTechnologySpecificRulesTraverser");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Sets.newHashSet(LinkCommonalities.class);
    }

    @Override
    public void handle(Goal goal) throws Throwable {
        if (goal.getName().equals(propertyHolder.getProperty(AbstractGoal.CURRENT_GOAL_PROPERTY))) {
            TechnologySpecificRules configure = new TechnologySpecificRules();
            goal.getAdapter().directives().additionalTechnologySpecificRules(configure);
            for (TechnologySpecificRules.TechnologySpecificRule rule : configure.getRules()) {
                if (rule instanceof TechnologySpecificRules.IdentifyAllWithName) {
                    TechnologySpecificRules.IdentifyAllWithName idRule = (TechnologySpecificRules.IdentifyAllWithName) rule;
                    Identification identification = new Identification();
                    identification.setName(idRule.getTypeName().printRaw());
                    for (String endpointName : goal.getCorrespondence().getEndpointsList()) {
                        Endpoint endpoint = goal.getCorrespondence().getEndpointRefs().get(endpointName);
                        if (endpoint.getTechnology().equals(goal.getTechnology())) {
                            if (endpoint.getSystem().get().schema().carrier().mentions(idRule.getTypeName())) {
                                AddImplicitCommonalities.addElementRef(identification,endpoint, endpointName, idRule.getTypeName());
                            }
                        }
                    }
                    if (!identification.getRelates().isEmpty() && !goal.getCorrespondence().getCommonalityWithName(idRule.getTypeName().printRaw()).isPresent()) {
                        String message = "ATTENTION: Technology '" + goal.getTechnology() + "' requires only one instance of a type with name '" + idRule.getTypeName().printRaw() + "'! Thus I am adding: ''" + identification.toString() + "''";
                        getReportFacade().reportInfo(message);
                        getLogger().info(message);
                        goal.getCorrespondence().addCommonality(identification);
                    }

                }
            }
        }
    }
}
