package no.hvl.past.di;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.corrlang.plugins"})
public class CorrLangDIComponentScanActivator {
}
