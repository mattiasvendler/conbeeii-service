package com.vendler.conbeeiiservice.message;

import com.vendler.conbeeiiservice.message.state.OpenState;

public class ConbeeIIOpenMessage extends ConbeeIIMessage {

    private OpenState state;

    public ConbeeIIOpenMessage() {
        messageType = MessageType.OPENCLOSE;
    }

    public OpenState getState() {
        return state;
    }

    public void setState(OpenState state) {
        this.state = state;
    }

    @Override
    public boolean allParsed() {
        if (state == null) {
            return false;
        }
        return state.getOpen() != null && state.getLastupdated() != null;
    }
}
