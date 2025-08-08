package io.corrlang.techspaces;

import no.hvl.past.ExtensionPoint;
import no.hvl.past.MetaRegistry;

public interface TechSpaceAdapterFactory<X extends TechSpace> extends ExtensionPoint {
    void register(MetaRegistry pluginRegistry);

    /**
     * Creates a concrete adaptor.
     */
    TechSpaceAdapter<X> createAdapter();

}
