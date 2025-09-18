package io.corrlang.di;

import java.util.Map;

public record CorrLangConfig(
        ProjectConfig project,
        LoggingConfig logging,
        SystemConfig system,

        Map<String, Object> plugins

) {

    public record ProjectConfig(
            String name,
            boolean projectDaemon,
            String dataDir
    ) {}
    public record LoggingConfig(
            String level,
            String mode,
            String logbackConfig
    ) { }

    public record SystemConfig(
            String path,
            Integer daemonPort
    ) {}



}


