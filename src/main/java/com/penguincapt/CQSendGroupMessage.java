package com.penguincapt;

import com.google.gson.Gson;

public class CQSendGroupMessage {
    public CQSendGroupMessage(String message){
        params = new msgParams(message);
    }
    private transient Gson gson = new Gson();
    final String action = "send_group_msg";
    msgParams params;
    public static class msgParams{
        final long group_id = 1059594804L;
        String message;
        public msgParams(String message){
            this.message = message;
        }
    }
    public String getMessageJson(){
        return gson.toJson(this);
    }
}
