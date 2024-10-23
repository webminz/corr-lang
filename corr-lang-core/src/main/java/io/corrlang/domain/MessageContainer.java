package io.corrlang.domain;

import com.google.common.base.Objects;
import no.hvl.past.names.Name;

public class MessageContainer {

    private final Name name;

    public MessageContainer(Name name) {
        this.name = name;
    }

    public Name getTypeName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageContainer that = (MessageContainer) o;
        return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
