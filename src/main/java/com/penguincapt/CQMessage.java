package com.penguincapt;

public class CQMessage {
    Long group_id;
    String message;
    String message_type;
    msgSender sender;
    public static class msgSender{
        String nickname;
        String card;
        String role;

        public String getNickname() {
            return nickname;
        }

        public String getRole() {
            return role;
        }

        public String getCard() {
            return card;
        }
    }

    public String getMessage() {
        return message;
    }

    public msgSender getSender() {
        return sender;
    }

    public Long getGroup_id() {
        return group_id;
    }
}
