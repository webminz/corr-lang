package io.corrlang.di;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Central key-value-based storage of configuration parameters that is shared globally.
 * The property holder will at least contain configuration parameters for the execution directories
 * and logging.
 *
 * Other components can simply add their own properties.
 * But they should make sure to avoid naming conflicts, e.g. by adding prefixes.
 *
 * The property holder is the foundation for the {@link DependencyInjectionContainer}.
 */
public class PropertyHolder {

    // TODO: better integration with Spring


    public static final String VIRTUAL_CONFIG = "config.virtual"; // bool, if exists no file is read, limited functionality
    public static final String BOOTSTRAP_CONFIG = "config.bootstrap"; // bool, if file does not exist, default config is created
    public static final String CONFIG_LOCATION = "config.location"; // path, file path to where to location is stored

    // Implicit params
    public static final String BASE_DIR = "dir.work";
    public static final String SYSTEM_DIR = "dir.sys";
    public static final String CONFIG_FILE = "general.config.file";
    public static final String TEMP_DIR = "dir.temp";

    public static final String APP_NAME = "general.app.name";
    public static final String JVM = "sys.jvm";

    public static final String LOG_CONFIG = "general.log.config";


    private final Map<String, Object> properties;

    private final Environment environment;


    private PropertyHolder(Environment environment, Map<String, Object> properties) {
        this.properties = properties;
        this.environment = environment;
    }

    // Generic getter and setter

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public String getProperty(String key) {
        if (this.properties.containsKey(key)) {
            Object value = this.properties.get(key);
            if (value instanceof String) {
                return (String) value;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getSubPropertyMap(String key) {
        if (this.properties.containsKey(key)) {
            Object value = this.properties.get(key);
            if (value instanceof Map<?, ?>) {
                return (Map<String, Object>) value;
            }
        }
        return null;
    }

    public String getPropertyAndSetDefaultIfNecessary(String property, String defaultValue) {
        if (!this.properties.containsKey(property)) {
            setProperty(property, defaultValue);
        }
        return getProperty(property);
    }

    public boolean getBooleanProperty(String propertyName) {
        if (properties.containsKey(propertyName)) {
            return Boolean.parseBoolean(getProperty(propertyName));
        }
        return false;
    }

    public int getIntegerProperty(String propertyName) {
        if (properties.containsKey(propertyName)) {
            return Integer.parseInt(getProperty(propertyName));
        }
        return -1;
    }

    public File getFileProperty(String propertyName) {
        if (properties.containsKey(propertyName)) {
            return new File(getProperty(propertyName));
        }
        return null;
    }

    // Special getters

    public File getBaseDir() {
        return getFileProperty(BASE_DIR);
    }


    // Reporting, reading and writing

    public String reportContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[PARAMETERS]");
        stringBuilder.append("\n");
        for (Map.Entry<String, Object> entry : this.properties.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append('=');
            stringBuilder.append(entry.getValue());
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }


    @SuppressWarnings("unchecked")
    public static PropertyHolder bootstrap(String appName, Environment environment) throws IOException, JoranException {
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put(APP_NAME, appName);

        properties.put(BASE_DIR, environment.getProperty("CORRLANG_CWD", new File(System.getProperty("user.dir")).getAbsolutePath()));
        properties.put(SYSTEM_DIR, environment.getProperty("CORRLANG_HOME", new File(System.getProperty("user.home") + "/.corrlang").getAbsolutePath()));
        properties.put(CONFIG_FILE, environment.getProperty("CORRLANG_CONF", properties.get(SYSTEM_DIR).toString() + "/config.toml"));
        properties.put(TEMP_DIR, new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());
        properties.put(JVM, System.getProperty("java.version"));

        File systemDir = new File(properties.get(SYSTEM_DIR).toString());

        // bootstrap when empty
        if (!systemDir.exists()) {
            System.out.printf("ATTENTION: System directory ('%s') does not exist! Hence, it will be boostrapped now!", systemDir.getAbsolutePath());
            systemDir.mkdirs();

            File logDir = new File(systemDir, "logs");
            logDir.mkdirs();

            String logbackConf =  new String(PropertyHolder.class.getResourceAsStream("/log-config-template.xml").readAllBytes(), StandardCharsets.UTF_8);
            logbackConf = logbackConf.replaceAll("\\$\\{LOG_FILE}", logDir.getAbsolutePath() + "/corrlang");
            File defaultLogConfig = new File(systemDir, "logback.xml");
            Files.writeString(defaultLogConfig.toPath(), logbackConf, StandardCharsets.UTF_8);

            File defaultConfig = new File(systemDir, "config.toml");
            Files.writeString(defaultConfig.toPath(), "[general]\nloggingConfig = \"" + defaultLogConfig.getAbsolutePath() + "\"", StandardCharsets.UTF_8);
        }


        File configFile = new File(properties.get(CONFIG_FILE).toString());
        if (!configFile.exists()) {
            System.err.printf("ERROR: Configuration file '%s' does not exist! Terminating", configFile.getAbsoluteFile());
            System.exit(88);
        }

        TomlMapper om = new TomlMapper();
        Map configValues = om.readValue(configFile, Map.class);

        for (Object key : configValues.keySet()) {

            if (key.equals("general")) {
                Object generalConfig =  configValues.get("general");
                if (generalConfig instanceof Map<?,?>) {
                    Map<String, Object> genConfig = (Map<String, Object>) generalConfig;
                    if (genConfig.containsKey("loggingConfig")) {
                        File f = new File(genConfig.get("loggingConfig").toString());
                        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
                        JoranConfigurator configurator = new JoranConfigurator();
                        configurator.setContext(lc);
                        lc.reset();
                        configurator.doConfigure(f);

                    }
                }
            } else {
                properties.put(key.toString(), configValues.get(key));
            }
        }


        return new PropertyHolder(environment, properties);
    }


}
