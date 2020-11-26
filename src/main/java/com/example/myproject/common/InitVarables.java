package com.example.myproject.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;

public class InitVarables implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final  String props = "/sys-conf.properties";
        final Properties properties= new Properties();
        try {
            properties.load(getClass().getResourceAsStream(props));
        } catch (IOException e) {

        }
        for (String prop: properties.stringPropertyNames()){
            if (System.getProperty(prop)==null){
                System.setProperty(prop,properties.getProperty(prop));
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}