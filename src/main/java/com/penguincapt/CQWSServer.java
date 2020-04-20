package com.penguincapt;

import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.logging.Level;

import static com.penguincapt.MineChat.mineChatLogger;

public class CQWSServer extends WebSocketServer {
    private WebSocket client;
    private String currentSender;
    private boolean coldAlertEnabled;
    private String coldBeginningTime;
    public CQWSServer(InetSocketAddress address){
        super(address);
    }
    public MCWSServer mcwsServer;
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        client = webSocket;
        mineChatLogger.log(Level.INFO,"CQHTTP client connected with IP Address: {0}", webSocket.getRemoteSocketAddress().getHostString()+":"+webSocket.getRemoteSocketAddress().getPort());

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(s);
        if (mcwsServer.isOpened()){
            Gson gson = new Gson();
            CQMessage msg = gson.fromJson(s,CQMessage.class);
            if (msg.getGroup_id()!=1059594804L){
                return;
            }
            String msgString = msg.getMessage();
            if (msgString.contains("!!")){
                String operation = msgString.substring(msgString.indexOf("!!")+2,msgString.indexOf(" "));
                String operand = msgString.substring(msgString.indexOf(" "));
                System.out.println(operation);
                System.out.println(operand);
                switch(operation){
                    case "cmd":
                        if (msg.getSender().getRole().equals("admin")||msg.getSender().getRole().equals("owner")){
                            mcwsServer.sendCmd(operand);
                            return;
                        }
                }
            }
            String senderName = msg.getSender().getCard().isEmpty() ? msg.getSender().getNickname() : msg.getSender().getCard();
            mcwsServer.sendChat(senderName,msgString,"");
        }

    }
    public void sendQQMsg(String message){
        CQSendGroupMessage msg = new CQSendGroupMessage(message);
        client.send(msg.getMessageJson());
    }
    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }
    public boolean isOpened(){
        return client != null;
    }
    @Override
    public void onStart() {

    }
}
