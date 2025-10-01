package io.corrlang.techspaces;

import no.hvl.past.graph.Universe;


import java.util.Map;
import java.util.Set;

/**
 * An Adaptor translates between the formal mdegraphlib-sketch-graph-representation
 * and a concrete technology. The TechSpace informs CorrLang about its _capabilities_
 * when initialized.
 *
 */
public interface TechSpace {

    /**
     * Each TechSpace must provide an (ideally) unique name.
     * By convention these names are written in SCREAMING_SNAKE_CASE.
     */
    String name();


    /**
     * Gives the plugin the opportunity to initialize itself and do preparatory actions,
     * e.g. set up databases connections, sockets etc. The tech space gets access to
     * the  universe of known structures, which can be used to register and look up
     * the graphs of schemas. Moreover, it receives a key-value property object,
     * which is used to configure the tech space. When the set-up is completed,
     * this method shall return the capabilities of this TechSpace.
     * This method is allowed to block.
     */
    Set<TechSpaceCapability> initialize(Universe universe, Map<String, Object> configProperties);


    /**
     * Before the application is shutdown the plugin is notified to safely terminate
     * all its open sessions, connections, etc. and possibly persist transient data to permanent storage.
     * This method is allowed to block!
     */
    void prepareShutdown();


}
