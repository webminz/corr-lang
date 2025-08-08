package io.corrlang.di;

import no.hvl.past.attributes.DataOperation;
import no.hvl.past.attributes.UserValue;
import no.hvl.past.graph.GraphOperation;
import no.hvl.past.graph.GraphPredicate;
import no.hvl.past.ExtensionPoint;
import no.hvl.past.MetaRegistry;
import io.corrlang.techspaces.TechSpace;
import io.corrlang.techspaces.TechSpaceAdapterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegistryImpl implements MetaRegistry {

    private Logger logger = LoggerFactory.getLogger(RegistryImpl.class);

    private final Map<Class<? extends ExtensionPoint>, Map<String, ExtensionPoint>> registryMap;

    public RegistryImpl() {
        this.registryMap = new HashMap<>();
        this.registryMap.put(TechSpace.class, new HashMap<>());
        this.registryMap.put(TechSpaceAdapterFactory.class, new HashMap<>());
        this.registryMap.put(UserValue.class, new HashMap<>());
        this.registryMap.put(GraphPredicate.class, new HashMap<>());
        this.registryMap.put(GraphOperation.class, new HashMap<>());
        this.registryMap.put(DataOperation.class, new HashMap<>());
    }

    @Override
    public <X extends ExtensionPoint> void register(String key, X extension) {
        Optional<Class<? extends ExtensionPoint>> subRegistry = this.registryMap.keySet().stream().filter(clazz -> clazz.isAssignableFrom(extension.getClass())).findFirst();
        if (subRegistry.isPresent()) {
            registryMap.get(subRegistry.get()).put(key, extension);
        } else {
            logger.info("Plugin fo r TechSpace '"  + key + "' registered");
            Map<String, ExtensionPoint> newRegistry = new HashMap<>();
            newRegistry.put(key, extension);
            registryMap.put(extension.getClass(), newRegistry);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X extends ExtensionPoint> Optional<X> getExtension(String key, Class<X> extensionType) {
        Optional<Class<? extends ExtensionPoint>> subRegistry = this.registryMap.keySet().stream().filter(clazz -> clazz.isAssignableFrom(extensionType)).findFirst();
        return subRegistry.flatMap(clazz -> {
            if (registryMap.get(clazz).containsKey(key)) {
                return Optional.of((X) registryMap.get(clazz).get(key));
            } else {
                return Optional.empty();
            }
        });
    }

    @Override
    public String printPluginInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("[PLUGINS]\n");
        Map<String, ExtensionPoint> stringExtensionPointMap = this.registryMap.get(GraphPredicate.class);
        for (String key : stringExtensionPointMap.keySet()) {
            builder.append("Graph Predicate: ");
            builder.append(key);
            builder.append(": ");
            builder.append(stringExtensionPointMap.get(key).getClass().getName());
        }
        stringExtensionPointMap = this.registryMap.get(GraphOperation.class);
        for (String key : stringExtensionPointMap.keySet()) {
            builder.append("Graph Operation: ");
            builder.append(key);
            builder.append(": ");
            builder.append(stringExtensionPointMap.get(key).getClass().getName());
        }
        stringExtensionPointMap = this.registryMap.get(UserValue.class);
        for (String key : stringExtensionPointMap.keySet()) {
            builder.append("Data Type: ");
            builder.append(key);
            builder.append(": ");
            builder.append(stringExtensionPointMap.get(key).getClass().getName());
            builder.append('\n');
        }
        stringExtensionPointMap = this.registryMap.get(DataOperation.class);
        for (String key : stringExtensionPointMap.keySet()) {
            builder.append("Data Operation: ");
            builder.append(key);
            builder.append(": ");
            builder.append(stringExtensionPointMap.get(key).getClass().getName());
            builder.append('\n');
        }
        stringExtensionPointMap = this.registryMap.get(TechSpace.class);
        for (String key : stringExtensionPointMap.keySet()) {
            builder.append("Tech Space: ");
            builder.append(key);
            builder.append(": ");
            builder.append(stringExtensionPointMap.get(key).getClass().getName());
            builder.append(" --> ");
            builder.append(this.registryMap.get(TechSpaceAdapterFactory.class).get(key).getClass().getName());
            builder.append('\n');

        }
        return builder.toString();
    }
}
