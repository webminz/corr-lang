package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.plugin.MetaRegistry;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapterFactory;
import no.hvl.past.techspace.TechSpaceAdapter;

import java.util.Optional;

public enum Platform {
    ECORE {
        @Override
        public String getKey() {
            return "ECORE";
        }
    }
    ,
    GRAPH_QL {
        @Override
        public String getKey() {
            return "GRAPH_QL";
        }
    },
    JAVA {
        @Override
        public String getKey() {
            return "JAVA";
        }
    },
    XML {
        @Override
        public String getKey() {
            return "XML";
        }
    },
    REST_OPENAPI {
        @Override
        public String getKey() {
            return "SWAGGER";
        }
    },
    PROTOBUFFERS {
        @Override
        public String getKey() {
            return "PROTOBUFFERS";
        }
    },
    TYPESCRIPT {
        @Override
        public String getKey() {
            return "TYPESCRIPT";
        }
    },
    SQL {
        @Override
        public String getKey() {
            return "SQL";
        }
    };

    public Optional<TechSpaceAdapter<? extends TechSpace>> createAdaptor(MetaRegistry pluginRegistry) {
        Optional<TechSpaceAdapterFactory> optional = pluginRegistry.getExtension(getKey(), TechSpaceAdapterFactory.class);
        return optional.map(factory -> factory.createAdapter());
    }

    public abstract String getKey();

    public static Platform parse(String text) {
        switch (text) {
            case "ECORE":
                return Platform.ECORE;
            case "XML":
                return Platform.XML;
            case "GRAPH_QL":
                return Platform.GRAPH_QL;
            case "REST_OPENAPI":
                return Platform.REST_OPENAPI;
            case "PROTOCOL_BUFFERS":
                return Platform.PROTOBUFFERS;
            case "TYPESCRIPT_JSON":
                return Platform.TYPESCRIPT;
            case "SQL":
                return Platform.SQL;
            case "JAVA":
                return Platform.JAVA;
            default:
                throw new Error("Not supported");
        }
    }
}
