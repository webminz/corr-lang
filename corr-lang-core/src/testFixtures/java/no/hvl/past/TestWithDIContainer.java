package no.hvl.past;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.corrlang.di.DependencyInjectionContainer;
import io.corrlang.di.PropertyHolder;
import no.hvl.past.graph.TestWithGraphLib;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public abstract class TestWithDIContainer extends TestWithGraphLib {

    protected File baseDir;

    protected File defaultRun;

    private DependencyInjectionContainer diContainer;


    @BeforeEach
    public void setUp() throws Exception {
        baseDir = new File(new File(System.getProperty("user.dir")).getAbsoluteFile() + "/temp/");
        if (baseDir.exists()) {
            System.out.printf("INFO: Temporary workdir %s is not empty! Going to clear it now...\n", baseDir.getAbsolutePath());
            try (Stream<Path> pathStream = Files.walk(baseDir.toPath())) {
                pathStream.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
        defaultRun = new File(baseDir, "defaultRun");
        File cwd = new File(defaultRun, "cwd");
        File home = new File(defaultRun, "home");
        cwd.mkdirs();
        home.mkdirs();
        System.setProperty("user.dir", cwd.getAbsolutePath());
        System.setProperty("user.home", home.getAbsolutePath());


        File corrlangDir = new File(home,".corrlang");
        corrlangDir.mkdirs();

        String logbackConf =  new String(PropertyHolder.class.getResourceAsStream("/logback-test.xml").readAllBytes(), StandardCharsets.UTF_8);
        File defaultLogConfig = new File(corrlangDir, "logback.xml");
        Files.writeString(defaultLogConfig.toPath(), logbackConf, StandardCharsets.UTF_8);

        File defaultConfig = new File(corrlangDir, "config.toml");
        Files.writeString(defaultConfig.toPath(), "[general]\nloggingConfig = \"" + defaultLogConfig.getAbsolutePath() + "\"", StandardCharsets.UTF_8);


        diContainer = DependencyInjectionContainer.create();

    }


    public DependencyInjectionContainer getDiContainer() {
        return diContainer;
    }

    protected ObjectMapper om() {
        return diContainer.getObjectMapper();
    }
}




