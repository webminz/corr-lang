package io.corrlang.engine.execution.goals;

import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.domainmodel.CorrSpec;
import io.corrlang.engine.reporting.ReportFacade;
import io.corrlang.plugins.ServerStarter;
import io.corrlang.domain.QueryHandler;
import no.hvl.past.MetaRegistry;
import no.hvl.past.UnsupportedFeatureException;
import io.corrlang.components.server.HttpMethod;
import io.corrlang.components.server.Webserver;
import io.corrlang.components.server.WebserviceRequestHandler;
import io.corrlang.domain.ComprSys;
import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceAdapter;
import io.corrlang.plugins.techspace.TechSpaceException;
import no.hvl.past.util.FileWatcher;
import no.hvl.past.util.GenericIOHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ServerGoal extends LanguageGoal {

    @Autowired
    private ReportFacade reportFacade;

    @Autowired
    private MetaRegistry pluginRegistry;

    private final Map<String, FileWatcher> activeDirectoryWatchers;

    @Autowired
    private ServerStarter serverStarter;
    private Webserver webserver;
    private String contextPath = "/";
    private int port = 9001;
    private int listenerInterval = 5_000; // all 5 seconds

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setListenerInterval(int listenerInterval) {
        this.listenerInterval = listenerInterval;
    }

    @Override
    protected void executeTransformation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {
        // TODO
    }

    @Override
    protected void executeSynchronize(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {
        // TODO
    }

    @Override
    protected void executeVerify(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {
        // TODO
    }

    @Override
    protected void executeSchema(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {
        // TODO

    }

    @Override
    protected void executeMatch(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {
        // TODO

    }

    @Override
    protected void executeFederation(CorrSpec correspondence,TechSpaceAdapter<? extends TechSpace> techSpace) throws TechSpaceException, UnsupportedFeatureException {
        ComprSys comprSys = correspondence.getComprSys();
        QueryHandler queryHandler = techSpace.queryHandler(comprSys);
        WebserviceRequestHandler handler = new WebserviceRequestHandler(contextPath, HttpMethod.POST, WebserviceRequestHandler.ResponseType.JSON) {
            @Override
            protected GenericIOHandler createHandler(Map<String, String> headers, Map<String, List<String>> queryParams, Map<String, String> cookies, Map<String, Object> sessionData) {
                return queryHandler;
            }
        };
        getWebserver().registerHandler(handler);

    }

    public ServerGoal() {
        super("SERVER");
        this.activeDirectoryWatchers = new HashMap<>();
    }


    @Override
    protected void executeTransitive(SyntacticalResult syntaxGraph) throws Throwable {
        super.executeTransitive(syntaxGraph);
        synchronized (this) {
            reportFacade.reportInfo("Server is listening at http://127.0.0.1:" + port + "/"+ contextPath + " now! Press CTR-C to quit...");
            this.wait();
        }
    }

    @Override
    public boolean isServerGoal() {
        return true;
    }

    @Override
    public boolean isCodegenGoal() {
        return false;
    }

    @Override
    public boolean isFileGoal() {
        return false;
    }

    @Override
    public boolean isBatchGoal() {
        return false;
    }

    public Webserver getWebserver() {
        if (this.webserver == null) {
            this.webserver =  serverStarter.getWebserverStartIfNecessary(port);
        }
        return webserver;
    }

    public void registerFileWatcher(String directory, String file, Consumer<LocalDateTime> consumer) {
        if (activeDirectoryWatchers.containsKey(directory)) {
        // TODO
            activeDirectoryWatchers.get(directory).addWatchedFile(file);
        } else {
            FileWatcher watch = FileWatcher.watch(new File(directory, file).getAbsolutePath(), consumer);
            this.activeDirectoryWatchers.put(directory, watch);
        }


    }
}
