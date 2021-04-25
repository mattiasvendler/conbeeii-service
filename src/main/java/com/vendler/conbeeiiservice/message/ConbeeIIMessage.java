package com.vendler.conbeeiiservice.message;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

public abstract class ConbeeIIMessage {
    public static final int EVENT_TYPE_ONOFF = 6;
    public static final int EVENT_TYPE_PRESENCE = 406;
    public static final int EVENT_TYPE_TEMPERATURE = 402;
    public static final int EVENT_TYPE_HUMIDITY = 405;
    private String e;
    private String id;
    private String r;
    private String t;
    private String uniqueid;
    private long deviceId;
    private int sensor;
    private int eventType;
    protected MessageType messageType;

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public void extract() {
        if (uniqueid == null) {
            return;
        }
        String[] s = uniqueid.split("-");
        sensor = Integer.parseInt(s[1]);
        if (s.length >= 3) {
            eventType = Integer.parseInt((s[2]));
        }
        String[] uid = s[0].split(":");
        int count = 7;
        StringBuilder sb = new StringBuilder();
        for(String b : uid) {
            sb.append(b);
        }
        try {
            byte[] b = Hex.decodeHex(sb.toString());
            deviceId=0;
            deviceId = new BigInteger(b).longValue();
        } catch (DecoderException decoderException) {
            decoderException.printStackTrace();
        }

    }
    public long getDeviceId() {
        extract();
        return deviceId;
    }

    public int getSensor() {
        extract();
        return sensor;
    }

    public int getEventType() {
        extract();
        return eventType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public abstract boolean allParsed();
}
