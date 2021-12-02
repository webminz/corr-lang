package io.corrlang.plugins;

import no.hvl.past.graph.Universe;
import no.hvl.past.MetaRegistry;
import io.corrlang.plugins.techspace.TechSpaceAdapter;
import io.corrlang.plugins.techspace.TechSpaceAdapterFactory;
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

    @Autowired
    Universe universe;

    @Autowired
    MetaRegistry pluginReg;

    @Override
    public void doSetUp() {
    }

    @Override
    public TechSpaceAdapter<TestTechSpace> createAdapter() {
        return new TestTechSpaceAdapter(testTechSpace, universe, stringTypeName, intTypeName, floatTypeName, boolTypeName);
    }

    @Override
    public void prepareShutdown() {
    }

    @PostConstruct
    public void setUp() {
        pluginReg.register(testTechSpace.ID(),testTechSpace);
        pluginReg.register(testTechSpace.ID(), this);
    }
}
