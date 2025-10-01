package io.corrlang.domain.data;


import io.corrlang.domain.Endpoint;
import no.hvl.past.names.Name;
import no.hvl.past.trees.QuerySchema;
import no.hvl.past.trees.Tree;

/**
 * A command is an instance of an action, i.e. it is the reification
 * of the invocation of an operation offered by a service endpoint.
 */
public class Command {

    /**
     * The system that is meant to receive this command.
     */
    private final Endpoint target;

    /**
     * The schema that is used for this command.
     */
    private final QuerySchema querySchema;

    /**
     * The abstract representation of the command invocation.
     */
    private final Tree representation;

    /**
     * The name of the action that is invoked, points to the original schema.
     */
    private final Name actionName;

    /**
     * The name of the return type in.
     */
    private final Name resultType;

    public Command(Endpoint target, QuerySchema querySchema, Tree representation, Name actionName, Name resultType) {
        this.target = target;
        this.querySchema = querySchema;
        this.representation = representation;
        this.actionName = actionName;
        this.resultType = resultType;
    }

    public Endpoint getTarget() {
        return target;
    }

    public QuerySchema getQuerySchema() {
        return querySchema;
    }

    public Tree getRepresentation() {
        return representation;
    }

    public Name getActionName() {
        return actionName;
    }

    public Name getResultType() {
        return resultType;
    }
}
