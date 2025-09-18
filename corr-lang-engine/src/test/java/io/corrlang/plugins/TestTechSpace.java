package io.corrlang.plugins;

import io.corrlang.techspaces.TechSpace;

public class TestTechSpace implements TechSpace {


    private final String id;

    public TestTechSpace(String id) {
        this.id = id;
    }

    @Override
    public String ID() {
        return id;
    }
}
