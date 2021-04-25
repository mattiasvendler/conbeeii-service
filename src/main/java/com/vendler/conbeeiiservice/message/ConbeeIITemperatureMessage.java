package com.vendler.conbeeiiservice.message;

import com.vendler.conbeeiiservice.message.state.TemperatureState;

public class ConbeeIITemperatureMessage extends ConbeeIIMessage{
    private TemperatureState state;

    public ConbeeIITemperatureMessage() {
        messageType = MessageType.TEMPERATURE;
    }

    public TemperatureState getState() {
        return state;
    }

    public void setState(TemperatureState state) {
        this.state = state;
    }

    @Override
    public boolean allParsed() {
        if (state == null) {
            return false;
        }
        return state.getTemperature() != null && state.getLastupdated() != null;
    }
}
