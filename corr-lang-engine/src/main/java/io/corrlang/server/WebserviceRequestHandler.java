package io.corrlang.server;


import io.javalin.http.Context;
import io.javalin.http.Handler;
import no.hvl.past.util.GenericIOHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public abstract class WebserviceRequestHandler implements Handler {

    public enum ResponseType {
        HTML,
        XML,
        JSON,
        PLAIN_TEXT,
        BINARY
    }

    private final String contextPath;
    private final HttpMethod method;
    private final ResponseType responseType;
    private Logger logger = LoggerFactory.getLogger(getClass());


    protected WebserviceRequestHandler(String contextPath, HttpMethod method, ResponseType responseType) {
        this.contextPath = contextPath;
        this.method = method;
        this.responseType = responseType;
    }

    String getContextPath() {
        return contextPath;
    }

    HttpMethod getMethod() {
        return method;
    }

    ResponseType getResponseType() {
        return responseType;
    }

    protected abstract GenericIOHandler createHandler(
            Map<String, String> headers,
            Map<String, List<String>> queryParams,
            Map<String, String> cookies,
            Map<String, Object> sessionData);

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(ctx.bodyAsBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GenericIOHandler handler = createHandler(
                ctx.headerMap(),
                ctx.queryParamMap(),
                ctx.cookieMap(),
                ctx.sessionAttributeMap());
            handler.handle(bis, bos);
            ctx.result(bos.toByteArray());
            switch (getResponseType()) {
                case HTML:
                    ctx.contentType("text/html");
                    break;
                case BINARY:
                    ctx.contentType("application/octet-stream");
                    break;
                case XML:
                    ctx.contentType("application/xml");
                    break;
                case JSON:
                    ctx.contentType("application/json");
                    break;
                case PLAIN_TEXT:
                    ctx.contentType("text/plain");
                    break;
            }

    }
}
