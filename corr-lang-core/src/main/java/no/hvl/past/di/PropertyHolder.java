package no.hvl.past.di;

import no.hvl.past.util.FileSystemAccessPoint;
import no.hvl.past.util.FileSystemUtils;

import java.io.*;
import java.util.*;
import java.util.logging.Level;


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

    private static final String VARIABLE_PREFIX = "${";
    private static final String VARIABLE_DELIMITTER = "}";

    // Implicit params
    public static final String BASE_DIR = "workdir";
    public static final String CONFIG_FILE = "config";
    public static final String CONFIG_DIR = "configdir";
    public static final String APP_NAME = "appname";
    public static final String JVM = "jvm";

    // Logging params
    public static final String LOG_LOCATION = "log.dir";
    public static final String LOG_LEVEL = "log.level";
    public static final String LOG_LAYOUT_PATTERN = "log.pattern";
    public static final String LOG_MAX_FILE_SIZE = "log.rollover.fileSize";
    public static final String LOG_MAX_BACKUP_INDEX = "log.rollover.backupIndex";

    // SSL params
    public static final String SSL_ALLOW_ALL = "http.ssl.acceptAll";
    public static final String SSL_ROOT_CERTS = "http.ssl.rootCerts";

    private static final String[] BUILTIN_PARAMS = new String[]{
            BASE_DIR,
            CONFIG_FILE,
            CONFIG_DIR,
            APP_NAME,
            JVM,
            LOG_LOCATION,
            LOG_LEVEL,
            LOG_MAX_FILE_SIZE,
            LOG_MAX_BACKUP_INDEX,
            LOG_LAYOUT_PATTERN,
            SSL_ALLOW_ALL,
            SSL_ROOT_CERTS
    };

    private final String appName;
    private final Properties properties;

    private PropertyHolder(String appName, Properties properties) {
        this.appName = appName;
        this.properties = properties;
    }

    // Generic getter and setter

    public void setProperty(String key, String value) {
        this.properties.setProperty(key, value);
    }

    public String getProperty(String key) {
        if (this.properties.containsKey(key)) {
            String value = this.properties.getProperty(key);
            while (value.contains(VARIABLE_PREFIX)) {
                int startIdx = value.indexOf(VARIABLE_PREFIX);
                int endIdx = value.indexOf(VARIABLE_DELIMITTER);
                String var = value.substring(startIdx + VARIABLE_PREFIX.length(), endIdx);
                String val = getProperty(var);
                value = value.substring(0, startIdx) + val + value.substring(endIdx + VARIABLE_DELIMITTER.length());
            }
            return value;
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

    public File getConfigFile() {
        return getFileProperty(CONFIG_FILE);
    }

    public File getConfigFileDirectory() {
        return getFileProperty(CONFIG_DIR);
    }


    public File getLogDir() throws IOException {
        return getFileProperty(LOG_LOCATION);
    }

    public Level getLogLevel() {
        String logLevel = getProperty(LOG_LEVEL);
            switch (logLevel) {
                case "ERROR":
                    return Level.SEVERE;
                case "WARN":
                    return Level.WARNING;
                case "TRACE":
                    return Level.FINEST;
                case "DEBUG":
                    return Level.FINER;
                case "OFF":
                    return Level.OFF;
                case "ALL":
                    return Level.ALL;
                default:
                case "INFO":
                    return Level.INFO;
            }
    }

    public String getLogLayoutPattern() {
        return getProperty(LOG_LAYOUT_PATTERN);
    }

    public String getLogMaxFileSize() {
        return getProperty(LOG_MAX_FILE_SIZE);
    }

    public String getLogMaxBackupIndex() {
        return getProperty(LOG_MAX_BACKUP_INDEX);
    }

    public boolean isSSLAllowAll() {
        return getBooleanProperty(SSL_ALLOW_ALL);
    }



    // Reporting, reading and writing

    public String reportContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[PARAMETERS]");
        stringBuilder.append("\n");
        for (Map.Entry<Object, Object> entry : this.properties.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append('=');
            stringBuilder.append(entry.getValue());
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public void write() throws IOException {
        File target = getConfigFile();
        if (!target.getParentFile().exists()) {
            target.mkdirs();
        }
        if (target.exists()) {
            target.delete();
        }
        FileOutputStream fos = new FileOutputStream(target);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
        bufferedWriter.append(getExplanationText(appName));

        if (getBaseDir().getAbsolutePath().equals(new File(System.getProperty("user.dir")).getAbsolutePath())) {
            bufferedWriter.append("#");
            bufferedWriter.append(BASE_DIR);
            bufferedWriter.append("=/home/user/working/directory");
        } else {
            bufferedWriter.append(BASE_DIR);
            bufferedWriter.append("=");
            bufferedWriter.append(getProperty(BASE_DIR));
        }
        bufferedWriter.append("\n\n# Logging configuration\n");
        bufferedWriter.append(LOG_LOCATION);
        bufferedWriter.append("=");
        if (!getProperty(LOG_LOCATION).equals(getConfigFileDirectory().getAbsolutePath() + "/logs")) {
            bufferedWriter.append(getProperty(LOG_LOCATION));
        } else {
            bufferedWriter.append("${configdir}/logs");
        }
        bufferedWriter.append("\n");
        bufferedWriter.append(LOG_LEVEL);
        bufferedWriter.append("=");
        bufferedWriter.append(getProperty(LOG_LEVEL));
        bufferedWriter.append("\n");
        bufferedWriter.append(LOG_LAYOUT_PATTERN);
        bufferedWriter.append("=");
        bufferedWriter.append(getProperty(LOG_LAYOUT_PATTERN));
        bufferedWriter.append("\n");
        bufferedWriter.append(LOG_MAX_FILE_SIZE);
        bufferedWriter.append("=");
        bufferedWriter.append(getProperty(LOG_MAX_FILE_SIZE));
        bufferedWriter.append("\n");
        bufferedWriter.append(LOG_MAX_BACKUP_INDEX);
        bufferedWriter.append("=");
        bufferedWriter.append(getProperty(LOG_MAX_BACKUP_INDEX));
        bufferedWriter.append("\n");
        bufferedWriter.append("\n# SSL/TLS handling of the HTTP client\n");
        if (this.properties.containsKey(SSL_ALLOW_ALL)) {
            bufferedWriter.append(SSL_ALLOW_ALL);
            bufferedWriter.append("=");
            bufferedWriter.append(getProperty(SSL_ALLOW_ALL));
            bufferedWriter.append("\n");
        } else {
            bufferedWriter.append("#");
            bufferedWriter.append(SSL_ALLOW_ALL);
            bufferedWriter.append("=true # e.g., if you want to trust all SSL certificates\n");
        }
        if (this.properties.containsKey(SSL_ROOT_CERTS)) {
            bufferedWriter.append(SSL_ROOT_CERTS);
            bufferedWriter.append("=");
            bufferedWriter.append(getProperty(SSL_ROOT_CERTS));
        } else {
            bufferedWriter.append("#");
            bufferedWriter.append(SSL_ALLOW_ALL);
            bufferedWriter.append("/path/to/trusted/X509/root/certificate...\n");
        }
        bufferedWriter.append("\n\n# Custom configuration parameters\n");
        TreeSet<String> otherKeys = new TreeSet<>();
        List<String> builtin = Arrays.asList(BUILTIN_PARAMS);
        this.properties.keySet().stream()
                .map(Object::toString)
                .filter(key -> !builtin.contains(key))
                .filter(this.properties::containsKey)
                .forEach(otherKeys::add);
        for (String o : otherKeys) {
            if (!builtin.contains(o)) {
                bufferedWriter.append(o);
                bufferedWriter.append("=");
                bufferedWriter.append(getProperty(o));
                bufferedWriter.append("\n");
            }
        }
        bufferedWriter.append("\n");
        bufferedWriter.flush();
        bufferedWriter.close();
        fos.flush();
        fos.close();
    }



    public static PropertyHolder bootstrap(String appName, File configFilePath) throws IOException {
        Properties properties = new Properties();
        if (configFilePath.exists()) {
            FileInputStream fis = new FileInputStream(configFilePath);
            properties.load(fis);
            fis.close();
        }
        properties.put(APP_NAME, appName);
        properties.put(CONFIG_FILE, configFilePath.getAbsolutePath());
        properties.put(CONFIG_DIR, configFilePath.getParentFile().getAbsolutePath());
        properties.put(JVM, System.getProperty("java.runtime.version"));
        if (!properties.containsKey(BASE_DIR)) {
            properties.put(BASE_DIR, new File(System.getProperty("user.dir")).getAbsolutePath());
        }

        if (!properties.containsKey(LOG_LOCATION)) {
            properties.put(LOG_LOCATION, "${" + CONFIG_DIR + "}/logs");
        }
        if (!properties.containsKey(LOG_LEVEL)) {
            properties.put(LOG_LEVEL, "INFO");
        }
        if (!properties.containsKey(LOG_LAYOUT_PATTERN)) {
            properties.put(LOG_LAYOUT_PATTERN, "%d %p %c [%t] %m %ex%n");
        }
        if (!properties.containsKey(LOG_MAX_FILE_SIZE)) {
            properties.put(LOG_MAX_FILE_SIZE, "1MB");
        }
        if (!properties.containsKey(LOG_MAX_BACKUP_INDEX)) {
            properties.put(LOG_MAX_BACKUP_INDEX, "4");
        }

        PropertyHolder propertyHolder = new PropertyHolder(appName, properties);
        if (!configFilePath.exists()) {
            propertyHolder.write();
        }
        return propertyHolder;
    }


    private static String getExplanationText(String appName) {
        return "# " + appName + " configuration file\n" +
                "\n" +
                "# This file allows a key-value based configuration of core application parameters for the " + appName + "  application\n" +
                "# Parameters are defined as follows:\n" +
                "# key=value\n" +
                "# you can also use parameter substitution using the ${...} syntax\n" +
                "# The following variables are implicitly defined\n" +
                "#    ${appname} --> Contains the string '"+ appName + "'\n" +
                "#    ${jvm} --> Contains the name of the JVM that is used to run this application\n" +
                "#    ${workdir} --> Contains the current working directory from where you called " + appName + "\n" +
                "#    ${config} --> Contains the path to this configuration file\n" +
                "#    ${configdir} --> Contains the path to the directory that contains this file\n" +
                "# ${appname}, ${jvm}, ${config} and ${configdir} cannot be overwritten, but ${workdir} can. See below...\n" +
                "\n" +
                "# ${workdir} points to the the directory which is used as the reference point for all relative file system references in the application.\n" +
                "# By default it is the current working directory upon calling "+ appName+"\n";
    }




}
