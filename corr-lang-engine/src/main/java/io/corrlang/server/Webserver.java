package io.corrlang.server;

import io.javalin.Javalin;
import io.javalin.config.EventConfig;
import io.javalin.event.LifecycleEventListener;

import java.util.function.Consumer;

public class Webserver {

    private final Javalin javalin;

    private Webserver(Javalin javalin) {
        this.javalin = javalin;
    }


    public void registerHandler(WebserviceRequestHandler handler) {
        switch (handler.getMethod()) {
            case GET:
                this.javalin.get(handler.getContextPath(), handler);
                break;
            case PUT:
                this.javalin.put(handler.getContextPath(), handler);
                break;
            case POST:
                this.javalin.post(handler.getContextPath(), handler);
                break;
            case DELETE:
                this.javalin.delete(handler.getContextPath(), handler);
                break;
                case HEAD:
                this.javalin.head(handler.getContextPath(), handler);
                break;
            case PATCH:
                this.javalin.patch(handler.getContextPath(), handler);
                break;
        }

    }

    public void shutdown() {
        this.javalin.stop();
    }

    public static Webserver start(int port, LifecycleEventListener startedHandler,LifecycleEventListener stoppedHandler) {
        Javalin javalin = Javalin.create(config -> {
            config.bundledPlugins.enableCors(corsConfig ->
                    corsConfig.addRule(corsRule -> {
                        corsRule.anyHost();
                        corsRule.allowCredentials = true;
                    }
                    ));
        }).events(event -> {
            event.serverStarted(startedHandler);
            event.serverStopped(stoppedHandler);
        }).start(port);
        return new Webserver(javalin);
    }
}
