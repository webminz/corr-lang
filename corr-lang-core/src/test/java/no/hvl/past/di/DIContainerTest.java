package no.hvl.past.di;

import no.hvl.past.TestBase;
import no.hvl.past.util.FileSystemAccessPoint;
import no.hvl.past.util.IOStreamUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;


public class DIContainerTest extends TestBase {


    @Test
    public void testDefaultStartup() throws Exception {
        DependencyInjectionContainer demo = DependencyInjectionContainer.create("demo", new Properties());
        File executionDir = new File(System.getProperty("user.dir"));
        assertEquals(demo.getPropertyHolder().getBaseDir().getAbsolutePath(), executionDir.getAbsolutePath());
        assertEquals(Level.INFO,demo.getPropertyHolder().getLogLevel());
        assertTrue(new File(executionDir, "demo.conf").exists());
        FileSystemAccessPoint fap = demo.getBean(FileSystemAccessPoint.class);
        assertNotNull(fap);
        assertEquals(fap.absolutePath(), executionDir.getAbsolutePath());
        // clean up
        new File(executionDir, "demo.conf").delete();
    }

    @Test
    public void testStartupWithExistingConfig() throws Exception {
        File subdir = getResourceFolderItem("demoDir/subdir");
        File logFile = new File(subdir, "logs/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log");
        if (logFile.exists()) {
            logFile.delete();
        }

        Properties p = new Properties();
        p.put(DependencyInjectionContainer.USE_CONFIG_COMMAND, getResourceFolderItem("demoDir/app.conf").getAbsolutePath());
        p.put("overwritten", "thisOne");
        DependencyInjectionContainer demo = DependencyInjectionContainer.create("demo", p);
        File executionDir = new File(System.getProperty("user.dir"));
        assertEquals(demo.getPropertyHolder().getBaseDir().getAbsolutePath(), executionDir.getAbsolutePath());
        assertEquals(42, demo.getPropertyHolder().getIntegerProperty("key.a"));
        assertEquals("/42/23/42", demo.getPropertyHolder().getProperty("key.c"));
        assertEquals(executionDir.getAbsolutePath() + "/important", demo.getPropertyHolder().getProperty("plugin.importantPath"));
        assertEquals(Level.WARN,demo.getPropertyHolder().getLogLevel());
        assertEquals(logFile.getParentFile().getAbsolutePath(), demo.getPropertyHolder().getProperty(PropertyHolder.LOG_LOCATION));
        assertEquals(executionDir.getAbsolutePath(), demo.getBean(FileSystemAccessPoint.class).absolutePath());
        assertEquals("thisOne", demo.getPropertyHolder().getProperty("overwritten"));
        assertEquals("demo", demo.getPropertyHolder().getProperty(PropertyHolder.APP_NAME));

        File logDir = new File(getResourceFolderItem("demoDir/subdir"), "logs");
        assertEquals(logDir.getAbsolutePath(), demo.getPropertyHolder().getLogDir().getAbsolutePath());

        Logger loggerA = LogManager.getLogger(DIContainerTest.class);
        Logger loggerB = LogManager.getLogger(PropertyHolder.class);
        loggerA.info("You not see me");
        loggerB.warn("You see me!");
        loggerA.error("You see me as well!");

        assertTrue(logFile.exists());
        FileInputStream fis = new FileInputStream(logFile);

        String actual = IOStreamUtils.readInputStreamAsString(fis);
        fis.close();

        String expected = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM")) + " WARN " + PropertyHolder.class.getCanonicalName() + " [Test worker] You see me! \n";
        expected += LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM")) + " ERROR " + DIContainerTest.class.getCanonicalName() + " [Test worker] You see me as well! \n";
        assertEquals(expected,actual);
    }

    @Test
    public void testStartupWithWritingConfig() throws Exception {
        File demoDir = getResourceFolderItem("demoDir");
        File configFile = new File(demoDir, "newapp.conf");
        if (configFile.exists()) {
            configFile.delete();
        }

        Properties p = new Properties();
        p.put(DependencyInjectionContainer.UPDATE_CONFIG_COMMAND, "");
        p.put(PropertyHolder.SSL_ALLOW_ALL, "true");
        p.put(PropertyHolder.BASE_DIR, demoDir.getAbsolutePath());
        p.put(PropertyHolder.LOG_LEVEL, "ERROR");
        p.put("randomProperty", "randomValue");

        DependencyInjectionContainer dependencyInjectionContainer = DependencyInjectionContainer.create("NewApp",p);
        FileSystemAccessPoint fsAccessPoint = dependencyInjectionContainer.getFSAccessPoint();
        assertTrue(fsAccessPoint.file("File.txt").exists());
        assertTrue(fsAccessPoint.file("subdir/File.txt").exists());
        assertEquals(configFile.getAbsolutePath(), dependencyInjectionContainer.getPropertyHolder().getConfigFile().getAbsolutePath());

        String expectedContent = mkExpected();
        FileInputStream fis = new FileInputStream(configFile);
        String actual = IOStreamUtils.readInputStreamAsString(fis);
        fis.close();

        assertEquals(expectedContent,actual);
    }

    private String mkExpected() {
        return "# NewApp configuration file\n" +
                "\n" +
                "# This file allows a key-value based configuration of core application parameters for the NewApp  application\n" +
                "# Parameters are defined as follows:\n" +
                "# key=value\n" +
                "# you can also use parameter substitution using the ${...} syntax\n" +
                "# The following variables are implicitly defined\n" +
                "#    ${appname} --> Contains the string 'NewApp'\n" +
                "#    ${jvm} --> Contains the name of the JVM that is used to run this application\n" +
                "#    ${workdir} --> Contains the current working directory from where you called NewApp\n" +
                "#    ${config} --> Contains the path to this configuration file\n" +
                "#    ${configdir} --> Contains the path to the directory that contains this file\n" +
                "# ${appname}, ${jvm}, ${config} and ${configdir} cannot be overwritten, but ${workdir} can. See below...\n" +
                "\n" +
                "# ${workdir} points to the the directory which is used as the reference point for all relative file system references in the application.\n" +
                "# By default it is the current working directory upon calling NewApp\n" +
                "workdir=" +getResourceFolderItem("demoDir").getAbsolutePath()  + "\n" +
                "\n" +
                "# Logging configuration\n" +
                "log.dir=${configdir}/logs\n" +
                "log.level=ERROR\n" +
                "log.pattern=%d %p %c [%t] %m %ex%n\n" +
                "log.rollover.fileSize=1MB\n" +
                "log.rollover.backupIndex=4\n" +
                "\n" +
                "# SSL/TLS handling of the HTTP client\n" +
                "http.ssl.acceptAll=true\n" +
                "#http.ssl.acceptAll/path/to/trusted/X509/root/certificate...\n" +
                "\n" +
                "\n" +
                "# Custom configuration parameters\n" +
                "randomProperty=randomValue\n\n";

    }


}
