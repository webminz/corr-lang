package io.corrlang.di;

import no.hvl.past.TestWithDIContainer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainerTest extends TestWithDIContainer {


    @Test
    public void testColdStart() throws Exception {
        File alternativeRun = new File(baseDir, "blankRun");
        File cwd = new File(alternativeRun, "cwd");
        File home = new File(alternativeRun, "home");
        cwd.mkdirs();
        home.mkdirs();
        System.setProperty("user.dir", cwd.getAbsolutePath());
        System.setProperty("user.home", home.getAbsolutePath());

        DependencyInjectionContainer container = DependencyInjectionContainer.create();

        PropertyHolder propertyHolder = container.getPropertyHolder();

        Logger logger = LoggerFactory.getLogger(ContainerTest.class);

        File cwdDir = propertyHolder.getBaseDir();
        File homeDir = propertyHolder.getFileProperty(PropertyHolder.SYSTEM_DIR);
        File tempDir = propertyHolder.getFileProperty(PropertyHolder.TEMP_DIR);

        assertTrue(cwdDir.isDirectory());
        assertTrue(cwdDir.exists());
        assertTrue(cwdDir.getAbsolutePath().contains("blankRun"));
        logger.info("Current Working Directory: {}", cwdDir.getAbsolutePath());

        assertTrue(homeDir.exists());
        assertTrue(homeDir.isDirectory());
        assertTrue(homeDir.getAbsolutePath().contains("blankRun"));
        logger.info("System home directory is: {}", homeDir.getAbsolutePath());

        assertTrue(tempDir.isDirectory());
        assertTrue(tempDir.exists());
        logger.info("Temp directory is: {}; existing: {}", tempDir.getAbsolutePath(), tempDir.exists());

        // TODO: assert that respective files are created
    }

    @Test
    public void testStartExistingConfig() {
        PropertyHolder propertyHolder = getDiContainer().getPropertyHolder();

        Logger logger = LoggerFactory.getLogger(ContainerTest.class);

        File cwdDir = propertyHolder.getBaseDir();
        File homeDir = propertyHolder.getFileProperty(PropertyHolder.SYSTEM_DIR);
        File tempDir = propertyHolder.getFileProperty(PropertyHolder.TEMP_DIR);

        assertTrue(cwdDir.isDirectory());
        assertTrue(cwdDir.exists());
        assertTrue(cwdDir.getAbsolutePath().contains("defaultRun"));
        logger.info("Current Working Directory: {}", cwdDir.getAbsolutePath());

        assertTrue(homeDir.exists());
        assertTrue(homeDir.isDirectory());
        assertTrue(homeDir.getAbsolutePath().contains("defaultRun"));
        logger.info("System home directory is: {}", homeDir.getAbsolutePath());

        assertTrue(tempDir.isDirectory());
        assertTrue(tempDir.exists());
        logger.info("Temp directory is: {}; existing: {}", tempDir.getAbsolutePath(), tempDir.exists());
    }

}
