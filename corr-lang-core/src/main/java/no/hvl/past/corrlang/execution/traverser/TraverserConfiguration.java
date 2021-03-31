package no.hvl.past.corrlang.execution.traverser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TraverserConfiguration {

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



}
