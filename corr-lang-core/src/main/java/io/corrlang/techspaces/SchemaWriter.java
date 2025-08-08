package io.corrlang.techspaces;

import io.corrlang.domain.Endpoint;

public interface SchemaWriter<O> {

    void serialize(Endpoint schema, O target) throws Exception;
}
