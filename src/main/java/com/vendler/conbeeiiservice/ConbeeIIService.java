package com.vendler.conbeeiiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ConbeeIIService extends Thread{
    private String host;
    private int port;
    private String apiKey;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private ConbeeIIConnector conbeeIIConnector;
    public ConbeeIIService() {
        logger.info("ConbeeIIService created");
    }

    public ConbeeIIService(String host, int port, String apiKey) {
        this.host = host;
        this.port = port;
        this.apiKey = apiKey;
        logger.info("ConbeeIIService created");
    }

    @Override
    public void run() {
        conbeeIIConnector.open();
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
