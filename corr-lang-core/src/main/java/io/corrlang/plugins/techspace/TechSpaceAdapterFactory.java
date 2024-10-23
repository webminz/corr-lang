package io.corrlang.plugins.techspace;

import no.hvl.past.ExtensionPoint;

public interface TechSpaceAdapterFactory<X extends TechSpace> extends ExtensionPoint {

    /**
     * Gives the plugin the opportunity to initialize itself and do preparatory actions,
     * e.g. set up databases, connections etc.
     * This method is allowed to block.
     */
    void doSetUp();

    /**
     * Creates a concrete adaptor.
     */
    TechSpaceAdapter<X> createAdapter();

    /**
     * Before the application is shutdown the plugin is notified to safely terminate
     * all its open sessions, connections, etc. and possibly persist transient data to permanent storage.
     * This method is allowed to block!
     */
    void prepareShutdown();
}
