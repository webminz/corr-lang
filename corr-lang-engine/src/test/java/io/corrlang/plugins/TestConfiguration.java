package io.corrlang.plugins;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {


    @Bean
    public TestTechSpaceFactory testTechSpaceA() {
        return new TestTechSpaceFactory(
                "TEST_A",
                "String",
                "Integer",
                "Double",
                "boolean"
        );
    }

    @Bean
    public TestTechSpaceFactory testTechSpaceB() {
        return new TestTechSpaceFactory("TEST_B",
                "str",
                "int",
                "real",
                null);
    }


}
