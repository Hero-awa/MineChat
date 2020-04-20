package com.penguincapt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.penguincapt.MCBody.MCBody;

import java.util.UUID;

public class MCMessage {
    protected static Gson gson = new Gson();
    private MCBody body;
    private MCHeader header;
    public MCMessage(MCBody body){
        header = new MCHeader(1, UUID.randomUUID(), body.getPurpose(), MessagePurposeType.COMMAND_REQUEST);
        this.body = body;
    }
    public static MCMessage getAsMessage(JsonObject JSON) {
        return gson.fromJson(JSON, MCMessage.class);
    }
    public String getMessageText() {
        return gson.toJson(this);
    }

    public MCHeader getHeader() {
        return header;
    }
}
