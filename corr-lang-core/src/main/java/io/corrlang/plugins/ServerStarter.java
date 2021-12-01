package io.corrlang.plugins;

import io.javalin.core.event.EventHandler;
import no.hvl.past.di.PropertyHolder;
import io.corrlang.components.server.Webserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class ServerStarter {

    @Autowired
    private PropertyHolder propertyHolder;
    private Webserver webserver;
    private boolean isRunning;
    private Logger logger;

    private Logger getLogger() {
        if (logger == null) {
            this.logger = LogManager.getLogger(getClass());
        }
        return logger;
    }

    private EventHandler startedHandler = new EventHandler() {
        @Override
        public void handleEvent() throws Exception {
            getLogger().info("Webserver successfully started");
        }
    };

    private EventHandler stoppedHandler = new EventHandler() {
        @Override
        public void handleEvent() throws Exception {
            getLogger().info("Webserver has stopped");

        }
    };




    public ServerStarter() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public synchronized Webserver getWebserverStartIfNecessary(int port) {
        if (!isRunning) {
            this.webserver = Webserver.start(port,startedHandler , stoppedHandler);
            this.isRunning = true;
        }
        return this.webserver;
    }

    public synchronized void stopServer() {
        this.webserver.shutdown();
        this.webserver = null;
        this.isRunning = false;
    }

    public void setStartedHandler(EventHandler startedHandler) {
        this.startedHandler = startedHandler;
    }

    public void setStoppedHandler(EventHandler stoppedHandler) {
        this.stoppedHandler = stoppedHandler;
    }
}
