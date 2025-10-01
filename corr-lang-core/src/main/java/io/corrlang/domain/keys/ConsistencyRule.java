package io.corrlang.domain.keys;

import io.corrlang.domain.data.Data;
import no.hvl.past.names.Name;

import java.util.stream.Stream;

public interface ConsistencyRule {

    Name commonality();

    Stream<Name> violations(Data instance);

}
