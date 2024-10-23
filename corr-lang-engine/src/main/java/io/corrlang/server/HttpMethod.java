package io.corrlang.server;

/**
 * The request methods defined in the HTTP protocol.
 * OPTIONS, TRACE, CONNECT are considered technical details handled by the underlying framework.
 */
public enum HttpMethod {

    /**
     * The GET method requests a representation of the specified resource. Requests using GET should only retrieve data.
     */
    GET,

    /**
     * The HEAD method asks for a response identical to that of a GET request, but without the response body.
     */
    HEAD,

    /**
     * The POST method is used to submit an entity to the specified resource, often causing a change in state or side effects on the io.corrlang.components.server.
     */
    POST,

    /**
     * The DELETE method deletes the specified resource.
     */
    DELETE,

    /**
     * The PUT method replaces all current representations of the target resource with the request payload.
     */
    PUT,

    /**
     * The PATCH method is used to apply partial modifications to a resource.
     */
    PATCH
}
