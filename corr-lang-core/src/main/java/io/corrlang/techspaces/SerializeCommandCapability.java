package io.corrlang.techspaces;

import io.corrlang.domain.data.Command;

import java.io.OutputStream;

public interface SerializeCommandCapability extends TechSpaceCapability {

    CommandSerializer commandSerializer();

    @FunctionalInterface
    interface CommandSerializer {

        void serialize(Command command, OutputStream outputStream) throws Exception;
    }

}
