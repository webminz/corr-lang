package io.corrlang.domain.msgs;


import no.hvl.past.trees.TreeReceiver;

import java.io.Closeable;

public interface MsgOutput extends Closeable {

    TreeReceiver asReceiver();

}
