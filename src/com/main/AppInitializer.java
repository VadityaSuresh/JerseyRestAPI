package com.main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(" Jersey REST API initialized on WebSphere.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(" Jersey application shutting down.");
    }
}
