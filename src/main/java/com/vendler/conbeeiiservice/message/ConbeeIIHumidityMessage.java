package com.vendler.conbeeiiservice.message;

import com.vendler.conbeeiiservice.message.state.HumidityState;

public class ConbeeIIHumidityMessage extends ConbeeIIMessage {
    private HumidityState state;

    public ConbeeIIHumidityMessage() {
        messageType = MessageType.HUMIDITY;
    }

    public HumidityState getState() {
        return state;
    }

    public void setState(HumidityState state) {
        this.state = state;
    }

    @Override
    public boolean allParsed() {
        if (state == null) {
            return false;
        }
        return state.getHumidity() != null && state.getLastupdated() != null;
    }
}
