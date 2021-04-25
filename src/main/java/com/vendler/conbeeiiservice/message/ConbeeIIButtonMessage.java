package com.vendler.conbeeiiservice.message;

import com.vendler.conbeeiiservice.message.state.ButtonState;

import java.awt.*;

public class ConbeeIIButtonMessage extends ConbeeIIMessage{
    private ButtonState state;

    public ConbeeIIButtonMessage() {
        messageType = MessageType.OPENCLOSE;
    }

    public ButtonState getState() {
        return state;
    }

    public void setState(ButtonState state) {
        this.state = state;
    }

    @Override
    public boolean allParsed() {
        if (state == null) {
            return false;
        }
        return state.getButtonevent() != null && state.getLastupdated() != null;
    }
}
