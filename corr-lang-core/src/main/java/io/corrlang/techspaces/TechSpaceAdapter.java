package io.corrlang.techspaces;

import io.corrlang.di.PropertyHolder;
import no.hvl.past.graph.Universe;
import no.hvl.past.UnsupportedFeatureException;


import java.util.Set;

/**
 * An Adaptor translates between the formal mdegraphlib-sketch-graph-representation
 * and a concrete technology.
 *
 * This interfaces defines all possible translations possible.
 * If a concrete TechSpace implementation cannot or does want to implement some functionality
 * it can raise an {@link UnsupportedFeatureException}.
 *
 * A tech space should at least allow to read its schema.
 *
 * @param <X> the type capture representing the actual technological space.
 */
public interface TechSpaceAdapter<X extends TechSpace> {

    /**
     * Gives the plugin the opportunity to initialize itself and do preparatory actions,
     * e.g. set up databases, connections etc.
     * This method is allowed to block.
     */
    void doSetUp(Universe universe, PropertyHolder propertyHolder);

    Set<TechSpaceCapability> capabilities();


    /**
     * Before the application is shutdown the plugin is notified to safely terminate
     * all its open sessions, connections, etc. and possibly persist transient data to permanent storage.
     * This method is allowed to block!
     */
    void prepareShutdown();


}
