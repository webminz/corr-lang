package io.corrlang.engine.execution.goals;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GoalConfiguration {


    @Bean
    public InfoGoal info() {
        return new InfoGoal();
    }

    @Bean
    public HelpGoal helpGoal() {
        return new HelpGoal();
    }

    @Bean
    public ParseGoal parseGoal() {
        return new ParseGoal();
    }

    @Bean
    @Scope("prototype")
    public ServerGoal serverGoal() {
        return new ServerGoal();
    }

    @Bean
    @Scope("prototype")
    public BatchGoal batchGoal() {
        return new BatchGoal();
    }

    @Bean
    @Scope("prototype")
    public CodegenGoal codegenGoal() {
        return new CodegenGoal();
    }

    @Bean
    @Scope("prototype")
    public FileGoal fileGoal() {
        return new FileGoal();
    }



}
