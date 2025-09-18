package io.corrlang.di;

import ch.qos.logback.core.util.StatusPrinter2;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoggingTest {

    @Test
    public void testLog() {
        CorrlangLogbackConfigurator.level = "WARN";
        Logger l = LoggerFactory.getLogger(LoggingTest.class);
        l.trace("super technical");
        l.debug("quite technical");
        l.info("normal system message");
        l.warn("careful");
        l.error("abnormal situation");
    }

    @Test
    public void testLoad() throws IOException {
        TomlMapper mapper = new TomlMapper();
        try (InputStream is = LoggingTest.class.getResourceAsStream("/testconfig.toml")) {
            CorrLangConfig config = mapper.readValue(is, CorrLangConfig.class);
            assertEquals("FILE", config.logging().mode());
            assertEquals("INFO", config.logging().level());

            assertEquals(6969L, Long.valueOf(config.system().daemonPort()).longValue());
            Map<String, Object> plugins = config.plugins();

            assertTrue(plugins.containsKey("plantuml"));
            Map plantuml = (Map) plugins.get("plantuml");
            assertEquals(true, plantuml.get("showServices"));
        }
    }

}
