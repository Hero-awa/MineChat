package com.penguincapt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.penguincapt.MCBody.MCBody;
import com.penguincapt.MCBody.MCCommand;
import com.penguincapt.MCBody.MCSubscribe;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.penguincapt.MineChat.mineChatLogger;

public class MCWSServer extends WebSocketServer {
    CopyOnWriteArrayList<String> uuid = new CopyOnWriteArrayList<>();
    public CQWSServer cqwsServer;
    public MCWSServer(InetSocketAddress address){
        super(address);
    }
    private WebSocket client;
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        client = conn;
        mineChatLogger.log(Level.INFO,"Minecraft client connected with IP Address: {0}", conn.getRemoteSocketAddress().getHostString()+":"+conn.getRemoteSocketAddress().getPort());
        for (int i = 0;i<4;i++){
            subscribeMessage(conn);
        }
        sendCmd("/tellraw @a { \"rawtext\" : [ { \"text\" : \"§aMineChat已成功连接\" } ] }");
        this.stopConnectionLostTimer();
    }

    public void onClose(WebSocket conn, int i, String s, boolean b) {
        mineChatLogger.log(Level.INFO,"Minecraft client with IP Address: {0} disconnected", conn.getRemoteSocketAddress().getHostString()+":"+conn.getRemoteSocketAddress().getPort());
    }

    public void onMessage(WebSocket conn, String s) {
        JsonElement element = new JsonParser().parse(s);
        JsonObject header = element.getAsJsonObject();
        header = header.getAsJsonObject("header");
        String reqid = header.get("requestId").getAsString();
        String purpose = header.get("messagePurpose").getAsString();
        System.out.println(s);
        if (purpose.equals("event")){
            JsonObject body = element.getAsJsonObject();
            body = body.getAsJsonObject("body");
            String eventName = body.get("eventName").getAsString();
            if (eventName.equals("PlayerMessage")){
                String sender = (body.getAsJsonObject("properties")).get("Sender").getAsString();
                if (sender.equals("外部")){
                    return;
                }
                String msg = (body.getAsJsonObject("properties")).get("Message").getAsString();
                cqwsServer.sendQQMsg("<"+sender+"> "+msg);

            }
        }else if (uuid.remove(reqid)){
            return;
        }
    }

    public void onError(WebSocket conn, Exception e) {

    }

    public void onStart() {

    }
    public void sendChat(String sender,String message,String prefix){
        String rawMsg="{ \"rawtext\" : [ { \"text\" : \""+prefix+"<"+sender+"> "+"§r"+message+"\" } ] }";
        sendCmd("/tellraw @a "+ rawMsg);
    }
    public void sendCmd(String cmd){
        MCBody body = new MCCommand("command_block",cmd,1);
        MCMessage msg = body.getAsMessage();
        String msgJSON = msg.getMessageText();
        uuid.add(msg.getHeader().getRequestId().toString());
        client.send(msgJSON);
    }
    private void subscribeMessage(WebSocket conn){
        MCBody body = new MCSubscribe("PlayerMessage");
        MCMessage msg = body.getAsMessage();
        String msgJSON = msg.getMessageText();
        System.out.println(msgJSON);
        conn.send(msgJSON);
    }
    public boolean isOpened(){
        return client != null;
    }
}
