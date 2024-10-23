package no.hvl.past.di;

import no.hvl.past.graph.Universe;
import no.hvl.past.MetaRegistry;
import no.hvl.past.util.FileSystemAccessPoint;
import no.hvl.past.util.FileSystemUtils;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class DependencyInjectionContainer {

    public static final String SPRING_BEAN_CONFIG_XML = "spring-bean-config.xml";
    public static final String UPDATE_CONFIG_COMMAND = "saveConfig";
    public static final String USE_CONFIG_COMMAND = "useConfig";
    private final ApplicationContext applicationContext;

    private DependencyInjectionContainer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // Generic Bean getters

    public <B> B getBean(Class<B> type) {
        return this.applicationContext.getBean(type);
    }

    public <B> B getBean(String beanName, Class<B> returnType) {
        return this.applicationContext.getBean(beanName, returnType);
    }

    // Getters for the Beans that are always there

    public MetaRegistry getPluginRegistry() {
        return this.applicationContext.getBean(MetaRegistry.class);
    }

    public Universe getUniverse() {
        return this.applicationContext.getBean(Universe.class);
    }

    public PropertyHolder getPropertyHolder() {
        return this.applicationContext.getBean(PropertyHolder.class);
    }

    public FileSystemUtils getFSUtils() {
        return applicationContext.getBean(FileSystemUtils.class);
    }


    private void setUpHttps() throws KeyManagementException, NoSuchAlgorithmException {
        if (getPropertyHolder().isSSLAllowAll()) {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            @Override
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }};
                SSLContext context = SSLContext.getInstance("SSL");
                context.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        }
    }

    @SuppressWarnings("unchecked raw")
    private void setUpLogging() throws IOException {
//        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//        final Configuration config = ctx.getConfiguration();
//        PatternLayout layout = PatternLayout.newBuilder().withConfiguration(config).withPattern(getPropertyHolder().getLogLayoutPattern()).build();
//        SizeBasedTriggeringPolicy policy = SizeBasedTriggeringPolicy.createPolicy(getPropertyHolder().getLogMaxFileSize());
//        DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder().withConfig(config).withFileIndex(getPropertyHolder().getLogMaxBackupIndex()).build();
////        RollingFileManager fileManager = RollingFileManager.getFileManager(getPropertyHolder().getLogDir().getAbsolutePath() + "/%d{yyyy-MM-dd}.log",
////                getPropertyHolder().getLogDir().getAbsolutePath() + "/%d{yyyy-MM-dd}.%i.log",
////                true,
////                false,
////                policy,
////                rolloverStrategy,
////                null,
////                layout,
////                128,
////                false,
////                true,
////                null,
////                null,
////                null,
////                config);
////        policy.initialize(fileManager);
//
//
//
//
//
//        RollingFileAppender fileAppender = RollingFileAppender.newBuilder()
//                .setName("main")
//                .withPolicy(policy)
//                .withStrategy(rolloverStrategy)
//                .withAppend(true)
//                .withFileName(getPropertyHolder().getLogDir().getAbsolutePath() + "/" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".log")
//                .withFilePattern(getPropertyHolder().getLogDir().getAbsolutePath() + "/" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".%i.log")
//                .setLayout(layout)
//                .build();
//
//
//
//        fileAppender.start();
//        config.addAppender(fileAppender);
//        config.getRootLogger().removeAppender("Console");
//        config.getRootLogger().addAppender(fileAppender, null, null);
//
//        config.getLoggerConfig("org.eclipse.jetty").setLevel(Level.OFF);
//        config.getLoggerConfig("graphql.execution.ExecutionStrategy").setLevel(Level.OFF);
//        config.getRootLogger().setLevel(getPropertyHolder().getLogLevel());
//
//
//        ctx.updateLoggers();
//        // TODO FIXME the following must be moved somewhere else...
//       // Configurator.setLevel(LogManager.getLogger("io.javalin.Javalin").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.http.HttpParser").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("notprivacysafe.graphql.GraphQL").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("graphql.GraphQL").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("notprivacysafe.graphql.execution.Execution").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("graphql.execution.ExecutionStrategy").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.corrlang.components.server.HttpOutput").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.corrlang.components.server.HttpConnection").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.corrlang.components.server.HttpInput").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.corrlang.components.server.HttpChannelState").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.ChannelEndPoint").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.corrlang.components.server.session").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.WriteFlusher").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.corrlang.components.server.Request").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.io.AbstractConnection").getName(), Level.OFF);
//        Configurator.setLevel(LogManager.getLogger("org.eclipse.jetty.http.HttpCookie").getName(), Level.OFF);
    }


    public static DependencyInjectionContainer create(String appName, Properties configArgs) throws Exception {
        PropertyHolder propertyHolder = setUpPropertyHolder(appName, configArgs);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("propertyHolder", propertyHolder);
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext(beanFactory);
        genericApplicationContext.refresh();

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {SPRING_BEAN_CONFIG_XML},genericApplicationContext);
        DependencyInjectionContainer dependencyInjectionContainer = new DependencyInjectionContainer(applicationContext);

        dependencyInjectionContainer.setUpLogging();
        dependencyInjectionContainer.setUpHttps();


        return dependencyInjectionContainer;
    }

    private static PropertyHolder setUpPropertyHolder(String appName, Properties configArgs) throws IOException {
        FileSystemUtils fileSystemUtils = FileSystemUtils.getInstance();
        File configFile;
        if (configArgs.containsKey(USE_CONFIG_COMMAND)) {
            configFile = fileSystemUtils.accessPoint("").file(configArgs.getProperty(USE_CONFIG_COMMAND));
        } else {
            if (configArgs.containsKey(PropertyHolder.BASE_DIR)) {
                configFile = new File(configArgs.getProperty(PropertyHolder.BASE_DIR) + "/" + appName.toLowerCase() + ".conf");
            } else {
                configFile = fileSystemUtils.accessPoint("").file(appName.toLowerCase() + ".conf");
            }
        }
        PropertyHolder propertyHolder = PropertyHolder.bootstrap(appName, configFile);
        for (Object o : configArgs.keySet()) {
            if (!o.equals(UPDATE_CONFIG_COMMAND) && !o.equals(USE_CONFIG_COMMAND)) {
                propertyHolder.setProperty(o.toString(), configArgs.get(o).toString());
            }
        }
        if (configArgs.containsKey(UPDATE_CONFIG_COMMAND)) {
            propertyHolder.write();
        }
        return propertyHolder;
    }


    public FileSystemAccessPoint getFSAccessPoint() {
        return getBean(FileSystemAccessPoint.class);
    }
}
