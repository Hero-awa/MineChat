package com.penguincapt.MCBody;

import com.penguincapt.MCMessage;
import com.penguincapt.MessagePurposeType;

public abstract class MCBody {
    private transient MCMessage parentMessage;
    private transient MessagePurposeType purpose;

    public MCBody(MessagePurposeType purpose) {
        this.purpose = purpose;
        parentMessage = new MCMessage(this);
    }
    public MessagePurposeType getPurpose() {
        return purpose;
    }
    public MCMessage getAsMessage() {
        return parentMessage;
    }
}
