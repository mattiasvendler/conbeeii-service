package com.vendler.conbeeiiservice.message;

import com.vendler.conbeeiiservice.message.state.PresenceState;

public class ConbeeIIPresenceMessage extends ConbeeIIMessage{
    private PresenceState state;

    public ConbeeIIPresenceMessage() {
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
        return state.getPresence() != null && state.getLastupdated() != null;
    }
}
