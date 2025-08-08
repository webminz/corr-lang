package io.corrlang.domain;

import io.corrlang.domain.data.ComprData;
import no.hvl.past.names.Name;

import java.util.stream.Stream;

public interface ConsistencyRule {

    Name commonality();

    Stream<Name> violations(ComprData instance);

}
