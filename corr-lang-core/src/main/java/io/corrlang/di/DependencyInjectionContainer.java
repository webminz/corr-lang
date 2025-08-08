package io.corrlang.di;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import no.hvl.past.graph.Universe;
import no.hvl.past.MetaRegistry;
import no.hvl.past.util.FileSystemAccessPoint;
import no.hvl.past.util.FileSystemUtils;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

public class DependencyInjectionContainer {

    private final ApplicationContext applicationContext;

    private DependencyInjectionContainer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // Generic Bean getters

    public <B> B getBean(Class<B> type) {
        return this.applicationContext.getBean(type);
    }

    public <B> B getBean(String beanName, Class<B> returnType) {
        return this.applicationContext.getBean(beanName, returnType);
    }

    // Getters for the Beans that are always there

    public MetaRegistry getPluginRegistry() {
        return this.applicationContext.getBean(MetaRegistry.class);
    }

    public Universe getUniverse() {
        return this.applicationContext.getBean(Universe.class);
    }

    public PropertyHolder getPropertyHolder() {
        return this.applicationContext.getBean(PropertyHolder.class);
    }

    public FileSystemUtils getFSUtils() {
        return applicationContext.getBean(FileSystemUtils.class);
    }



    public static DependencyInjectionContainer create() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DIConfiguration.class);
        return new DependencyInjectionContainer(applicationContext);
    }


    public FileSystemAccessPoint getFSAccessPoint() {
        return getBean(FileSystemAccessPoint.class);
    }

    public ObjectMapper getObjectMapper() {
        return applicationContext.getBean(ObjectMapper.class);
    }

}
