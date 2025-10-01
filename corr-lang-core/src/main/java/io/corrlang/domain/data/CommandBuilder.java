package io.corrlang.domain.data;

import io.corrlang.domain.Endpoint;

public class CommandBuilder {

    private final Endpoint target;

    public CommandBuilder(Endpoint target) {
        this.target = target;
    }


    public Command build() {
        // TODO
        return new Command(target, null, null, null, null);
    }

}
