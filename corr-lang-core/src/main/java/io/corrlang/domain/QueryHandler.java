package io.corrlang.domain;

import no.hvl.past.graph.trees.QueryTree;
import no.hvl.past.graph.trees.Tree;
import no.hvl.past.graph.trees.TypedTree;
import no.hvl.past.util.GenericIOHandler;

import java.io.*;

public interface QueryHandler extends GenericIOHandler {

    default Tree resolve(QueryTree queryTree) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resolve(queryTree, bos);
        return deserialize(new ByteArrayInputStream(bos.toByteArray()));
    }

    default void resolve(QueryTree queryTree, OutputStream outputStream) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serialize(queryTree, bos);
        handle(new ByteArrayInputStream(bos.toByteArray()), outputStream);
    }

    default InputStream resolveAsStream(QueryTree queryTree) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resolve(queryTree, bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }

    void serialize(TypedTree queryTree, OutputStream outputStream) throws IOException, ProcessingException;

    Tree deserialize(InputStream inputStream) throws IOException, ProcessingException;
}
