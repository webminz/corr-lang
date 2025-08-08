package io.corrlang.techspaces;

import io.corrlang.domain.data.Data;
import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.Endpoint;
import no.hvl.past.names.Name;

public interface InstanceParser<I> {

    public static class TypeHints {

        private final Name rootTypeName;
        private final boolean rootIsCollectionValued;

        private final Schema schemaRef;

        public TypeHints(Name rootTypeName, boolean rootIsCollectionValued, Schema schemaRef) {
            this.rootTypeName = rootTypeName;
            this.rootIsCollectionValued = rootIsCollectionValued;
            this.schemaRef = schemaRef;
        }

        public Name getRootTypeName() {
            return rootTypeName;
        }

        public boolean isRootIsCollectionValued() {
            return rootIsCollectionValued;
        }

        public Schema getSchemaRef() {
            return schemaRef;
        }
    }

    Data parseData(I source, Endpoint endpoint, TypeHints typeHints) throws Exception;

}
