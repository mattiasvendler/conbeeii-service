package com.vendler.conbeeiiservice;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.vendler.conbeeiiservice.message.*;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.vendler.conbeeiiservice.message.ConbeeIIMessage.*;

@Component
public class ConbeeMessageParser {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Gson parser = new Gson();
    private CopyOnWriteArrayList<ConbeeIIMessageLisener> listeners = new CopyOnWriteArrayList<ConbeeIIMessageLisener>();
    Channel channel;

    public ConbeeMessageParser() {
    }

    public void parse(String message) {
        try {
            System.out.println("New message: " + message);
            ConbeeIIMessage event = parser.fromJson(message, ConbeeIIEventMessage.class);
            event.extract();
            handleMessage(event, message);
        }catch(JsonSyntaxException e) {
            e.printStackTrace();
        }

    }

    private void handleMessage(ConbeeIIMessage message, String payload) {
        ConbeeIIMessage msg = null;
        if(channel == null) {
            try {
                connectionFactory = new ConnectionFactory();
                connectionFactory.setHost("192.168.49.2");
                connectionFactory.setPort(32024);
                connectionFactory.setUsername("conbee");
                connectionFactory.setPassword("conbee");
                connectionFactory.setVirtualHost("storage-collector-dev");
                connection = connectionFactory.newConnection();

                channel = connection.createChannel();
                channel.exchangeDeclare("exchange.conbeeII", "fanout");
//            String queueName = channel.queueDeclare().getQueue();
//            channel.queueBind(queueName, "exchange.conbeeII", "messages");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        switch (message.getEventType()) {
            case EVENT_TYPE_ONOFF:
                msg = parser.fromJson(payload, ConbeeIITradfriMotionMessage.class);
                if (!msg.allParsed()) {
                    msg = parser.fromJson(payload, ConbeeIIOpenMessage.class);
                }
                if (!msg.allParsed()) {
                    msg = parser.fromJson(payload, ConbeeIIButtonMessage.class);
                }

                break;
            case EVENT_TYPE_PRESENCE:
                msg = parser.fromJson(payload, ConbeeIIPresenceMessage.class);
                break;
            case EVENT_TYPE_HUMIDITY:
                msg = parser.fromJson(payload, ConbeeIIHumidityMessage.class);
                break;
            case EVENT_TYPE_TEMPERATURE:
                msg = parser.fromJson(payload, ConbeeIITemperatureMessage.class);
            default:
                System.out.println("Unhandled event type: " + message.getEventType());
        }

        if (msg != null && msg.allParsed()) {
            msg.extract();
            try {
                channel.basicPublish("exchange.conbeeII","",null,parser.toJson(msg).getBytes());
                System.out.println("Message published");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Message parsed " + msg.getClass().getName());
        }
    }
}
