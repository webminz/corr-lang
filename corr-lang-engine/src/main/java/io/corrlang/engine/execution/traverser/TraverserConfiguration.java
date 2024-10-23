package io.corrlang.engine.execution.traverser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraverserConfiguration {

    @Bean
    public RetrieveUrls retrieveUrls() {
        return new RetrieveUrls();
    }

    @Bean
    public DesugarAliases desugarAliases() {
        return new DesugarAliases();
    }

    @Bean
    public IdentifyTechSpaceTraverser identifyTechSpaces() {
        return new IdentifyTechSpaceTraverser();
    }

    @Bean
    public LoadTechSpaceTraverser loadTechSpaces() {
        return new LoadTechSpaceTraverser();
    }

    @Bean
    public ParseEndpointSchemaTraverser parseEndpointSchemas() {
        return new ParseEndpointSchemaTraverser();
    }

    @Bean
    public LinkEndpointsTraverser linkToEndpointsTraverser() {
        return new LinkEndpointsTraverser();
    }

    @Bean
    public LinkElementsTraverser linkElementRefs() {
        return new LinkElementsTraverser();
    }

    @Bean(name="ALIGN_SCHEMA")
    public CreateFormalAlignmentTraverser alignModelsFormally() {
        return new CreateFormalAlignmentTraverser();
    }

    @Bean
    public LinkCommonalities linkCommonalities() {
        return new LinkCommonalities();
    }

    @Bean
    public AddImplicitCommonalities addImplicitCommonalities() {
        return new AddImplicitCommonalities();
    }

    @Bean
    public ApplyFinalTechnologySpecificRulesTraverser applyFinalTechnologySpecificRules() {
        return new ApplyFinalTechnologySpecificRulesTraverser();
    }


}
