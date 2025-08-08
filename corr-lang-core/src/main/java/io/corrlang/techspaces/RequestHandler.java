package io.corrlang.techspaces;

public interface RequestHandler<I, O> {

    /**
     * Handle a request.
     * May block.
     */
    O process(I input) throws Exception;
}
