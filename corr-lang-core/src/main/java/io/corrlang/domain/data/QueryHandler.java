package io.corrlang.domain.data;

import no.hvl.past.trees.Tree;
import no.hvl.past.util.GenericIOHandler;

import java.io.*;

public interface QueryHandler extends GenericIOHandler {

    default Tree resolve(Tree queryTree) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resolve(queryTree, bos);
        return deserialize(new ByteArrayInputStream(bos.toByteArray()));
    }

    default void resolve(Tree queryTree, OutputStream outputStream) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serialize(queryTree, bos);
        handle(new ByteArrayInputStream(bos.toByteArray()), outputStream);
    }

    default InputStream resolveAsStream(Tree queryTree) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resolve(queryTree, bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }

    void serialize(Tree queryTree, OutputStream outputStream) throws IOException, ProcessingException;

    Tree deserialize(InputStream inputStream) throws IOException, ProcessingException;
}
