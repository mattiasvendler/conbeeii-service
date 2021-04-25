package com.vendler.conbeeiiservice.message;

import com.vendler.conbeeiiservice.message.ConbeeIIMessage;
import com.vendler.conbeeiiservice.message.state.PresenceState;

public class ConbeeIITradfriMotionMessage extends ConbeeIIMessage {
    private PresenceState state;

    public ConbeeIITradfriMotionMessage() {
        messageType = MessageType.PRESENCE;
    }

    public PresenceState getState() {
        return state;
    }

    public void setState(PresenceState state) {
        this.state = state;
    }

    @Override
    public boolean allParsed() {
        if (state == null) {
            return false;
        }

        return state.getLastupdated() != null && state.getPresence() != null;
    }
}
