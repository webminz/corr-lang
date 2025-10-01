package io.corrlang.techspaces;

import io.corrlang.domain.data.Data;

import java.io.OutputStream;

public interface SerializeDataCapability extends TechSpaceCapability {

    @FunctionalInterface
    interface DataSerializer {

        void serializeData(Data data, OutputStream outputStream) throws Exception;
    }

}
