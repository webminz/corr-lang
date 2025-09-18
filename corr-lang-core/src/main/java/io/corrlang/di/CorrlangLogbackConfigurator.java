package io.corrlang.di;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.spi.ContextAwareBase;
import org.slf4j.Logger;

public class CorrlangLogbackConfigurator extends ContextAwareBase implements Configurator {

    public static String level = "INFO";
    public static String mode = "CONSOLE";

    public static String logbackConfigLocation = null;

    public enum LogMode {
        CONSOLE,
        FILE,
        CUSTOM
    }

    @Override
    public ExecutionStatus configure(LoggerContext loggerContext) {
        System.out.println("Invoked ....");

        LogMode logMode = LogMode.valueOf(mode);

        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setContext(loggerContext);
        layoutEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{10} - %msg%n");
        layoutEncoder.start();

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(loggerContext);
        appender.setEncoder(layoutEncoder);
        appender.setName("CONSOLE");
        appender.start();

        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(appender);
        rootLogger.setLevel(Level.toLevel(level));


        return ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY;
    }
}
