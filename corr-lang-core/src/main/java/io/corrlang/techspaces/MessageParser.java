package io.corrlang.techspaces;

import io.corrlang.domain.msgs.Msg;
import io.corrlang.domain.msgs.MsgHeader;

public interface MessageParser<I> {

    Msg parseMessage(I input, MsgHeader header) throws Exception;
}
