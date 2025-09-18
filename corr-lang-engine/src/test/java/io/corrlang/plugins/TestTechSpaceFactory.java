package io.corrlang.plugins;

import no.hvl.past.graph.Universe;
import no.hvl.past.MetaRegistry;
import io.corrlang.techspaces.TechSpaceAdapter;
import io.corrlang.techspaces.TechSpaceAdapterFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class TestTechSpaceFactory implements TechSpaceAdapterFactory<TestTechSpace> {

    private TestTechSpace testTechSpace;
    private String stringTypeName;
    private String intTypeName;
    private String floatTypeName;
    private String boolTypeName;

    TestTechSpaceFactory(String testTechSpaceName,
                         String stringTypeName,
                         String intTypeName,
                         String floatTypeName,
                         String boolTypeName) {
        this.testTechSpace = new TestTechSpace(testTechSpaceName);
        this.stringTypeName = stringTypeName;
        this.intTypeName = intTypeName;
        this.floatTypeName = floatTypeName;
        this.boolTypeName = boolTypeName;
    }





    @Override
    @PostConstruct
    public void register(MetaRegistry pluginRegistry) {
        pluginRegistry.register(testTechSpace.ID(),testTechSpace);
        pluginRegistry.register(testTechSpace.ID(), this);
    }

    @Override
    public TechSpaceAdapter<TestTechSpace> createAdapter() {
        return new TestTechSpaceAdapter(testTechSpace, stringTypeName, intTypeName, floatTypeName, boolTypeName);
    }



}
