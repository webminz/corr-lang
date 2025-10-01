package io.corrlang.techspaces;

import io.corrlang.domain.data.Command;
import io.corrlang.domain.data.CommandBuilder;
import io.corrlang.domain.msgs.Msg;
import io.corrlang.domain.msgs.MsgHeader;

import java.io.InputStream;

public interface ParseCommandCapability extends TechSpaceCapability {

    /**
     * Ability to parse a binary request.
     */
    CommandParser parseCommand();

    interface CommandParser {

        Command parseMessage(InputStream input, CommandBuilder builder) throws Exception;
    }
}
