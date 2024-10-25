package io.corrlang.server;

import io.javalin.event.LifecycleEventListener;
import io.corrlang.di.PropertyHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ServerStarter {

    @Autowired
    private PropertyHolder propertyHolder;
    private Webserver webserver;
    private boolean isRunning;
    private Logger logger;

    private Logger getLogger() {
        if (logger == null) {
            this.logger = LoggerFactory.getLogger(getClass());
        }
        return logger;
    }

    private LifecycleEventListener startedHandler = () -> getLogger().info("Webserver successfully started");

    private LifecycleEventListener stoppedHandler = () -> getLogger().info("Webserver has stopped");




    public ServerStarter() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public synchronized Webserver getWebserverStartIfNecessary(int port) {
        if (!isRunning) {
            this.webserver = Webserver.start(port, startedHandler , stoppedHandler);
            this.isRunning = true;
        }
        return this.webserver;
    }

    public synchronized void stopServer() {
        this.webserver.shutdown();
        this.webserver = null;
        this.isRunning = false;
    }

    public void setStartedHandler(LifecycleEventListener startedHandler) {
        this.startedHandler = startedHandler;
    }

    public void setStoppedHandler(LifecycleEventListener stoppedHandler) {
        this.stoppedHandler = stoppedHandler;
    }
}
