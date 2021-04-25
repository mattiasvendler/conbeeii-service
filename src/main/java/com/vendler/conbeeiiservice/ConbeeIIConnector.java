package com.vendler.conbeeiiservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class ConbeeIIConnector {
    @Autowired
    private ConbeeMessageParser parser;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Channel channel;
    Logger logger = LoggerFactory.getLogger(ConbeeIIConnector.class);
    public ConbeeIIConnector() {

    }

    public void open() {
        WebSocketClient webSocketClient = new WebSocketClient(URI.create("ws://192.168.1.68:443/api/16BF6AEABD/")) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                logger.info("Connected");
            }

            @Override
            public void onMessage(String s) {
                if(channel == null) {
                    try {
                        connectionFactory = new ConnectionFactory();
                        connectionFactory.setHost("192.168.1.181");
                        connectionFactory.setPort(5672);
//                        connectionFactory.setUsername("conbee");
//                        connectionFactory.setPassword("conbee");
//                        connectionFactory.setVirtualHost("storage-collector-dev");
                        connection = connectionFactory.newConnection();

                        channel = connection.createChannel();
                        channel.exchangeDeclare("exchange.conbeeII", "fanout");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    logger.info(String.format("Message: %s" , s));
                    channel.basicPublish("exchange.conbeeII","",null,s.getBytes());
                    logger.debug("Published");
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    channel = null;
                }

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                logger.info("Close");
                open();
            }

            @Override
            public void onError(Exception e) {
                logger.error(e.getMessage());
            }
        };
        webSocketClient.close();
        webSocketClient.connect();
    }

}
