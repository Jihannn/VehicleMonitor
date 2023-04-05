package com.jihan.vehicle.server.vehicleserver.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.InetSocketAddress;

@WebListener
public class MyWebSocketListener implements ServletContextListener {

    private MyWebSocketServer webSocketServer;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        int port = 8090;
        InetSocketAddress address = new InetSocketAddress(port);
        webSocketServer = new MyWebSocketServer(address);
        webSocketServer.start();
        System.out.println("WebSocket Server started on port: " + webSocketServer.getPort());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (webSocketServer != null) {
            try {
                webSocketServer.stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("WebSocket Server stopped.");
        }
    }
}