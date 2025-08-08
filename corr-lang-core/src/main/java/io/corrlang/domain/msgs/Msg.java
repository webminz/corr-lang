package io.corrlang.domain.msgs;

import no.hvl.past.trees.Tree;

import java.util.Objects;


/**
 * Represents any kind of data that is sent between systems.
 * We call this entity _message_ and it comprises
 * a message header and a payload.
 * The message header contains metadata to identify a message (timestamp, uuid)
 * and the payload is abstractly described by a {@link Tree}.
 */
public final class Msg {

    private final MsgHeader header;
    private final Tree tree;

    public Msg(MsgHeader header, Tree tree) {
        this.header = header;
        this.tree = tree;
    }

    public MsgHeader getHeader() {
        return header;
    }

    public Tree getTree() {
        return tree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Msg msg = (Msg) o;
        return Objects.equals(header, msg.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }
}
