package no.hvl.past.di;

import io.corrlang.plugins.techspace.TechSpace;

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
