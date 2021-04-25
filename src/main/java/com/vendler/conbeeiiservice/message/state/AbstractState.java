package com.vendler.conbeeiiservice.message.state;

public abstract class AbstractState {
    private String lastupdated;

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }
}
