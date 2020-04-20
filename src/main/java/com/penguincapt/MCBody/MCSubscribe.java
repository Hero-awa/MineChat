package com.penguincapt.MCBody;

import com.penguincapt.MessagePurposeType;

public class MCSubscribe extends MCBody {
    private String eventName;
    public MCSubscribe(String eventName) {
        super(MessagePurposeType.SUBSCRIBE);
        this.eventName = eventName;
    }
}
