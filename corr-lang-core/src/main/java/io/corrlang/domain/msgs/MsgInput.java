package io.corrlang.domain.msgs;

import no.hvl.past.serde.Format;
import no.hvl.past.trees.TreeReceiver;

public interface MsgInput {

    void read(MsgHeader header, Format format, TreeReceiver receiver);
}
