package com.penguincapt;

import com.penguincapt.MCBody.MCCommand;
import com.penguincapt.MCBody.MCSubscribe;

import java.util.concurrent.ConcurrentHashMap;

public enum MessagePurposeType {
    COMMAND_REQUEST("commandRequest", MCCommand.class),
    SUBSCRIBE("subscribe", MCSubscribe.class);
    private final String purpose;
    private final Class clazz;

    private static ConcurrentHashMap<String, MessagePurposeType> messagePurposeTypes = new ConcurrentHashMap();

    static {
        for(MessagePurposeType type : values()) {
            messagePurposeTypes.put(type.getPurposeName(), type);
        }
    }
    MessagePurposeType(String purpose, Class clazz) {
        this.purpose = purpose;
        this.clazz = clazz;
    }
    public String getPurposeName() {
        return purpose;
    }
    public static MessagePurposeType fromString(String typeName) {
        if(typeName == null) {
            return null;
        }

        return messagePurposeTypes.get(typeName);
    }
}
