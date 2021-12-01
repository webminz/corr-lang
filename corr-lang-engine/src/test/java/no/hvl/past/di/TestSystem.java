package no.hvl.past.di;

import no.hvl.past.graph.Sketch;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;
import io.corrlang.domain.MessageType;
import io.corrlang.domain.Sys;

import java.util.Optional;
import java.util.stream.Stream;

public class TestSystem implements Sys  {

    private final String url;
    private final Sketch schema;

    public TestSystem(String url, Sketch schema) {
        this.url = url;
        this.schema = schema;
    }

    @Override
    public String displayName(Name name) {
        return name.print(PrintingStrategy.IGNORE_PREFIX);
    }

    @Override
    public Optional<Triple> lookup(String... path) {
        return schema.carrier().get(Name.identifier(path[path.length-1]));
    }

    @Override
    public Sketch schema() {
        return schema;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public Stream<MessageType> messages() {
        return Stream.empty();
    }
}
