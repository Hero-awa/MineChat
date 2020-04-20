package com.penguincapt;

import java.net.InetSocketAddress;
import java.util.TimerTask;
import java.util.logging.Logger;

public class MineChat {
    public static final Logger mineChatLogger = Logger.getLogger("com.penguincapt.MineChat-Logging");
    public static void main(String[] args) {
        MCWSServer mcserver = new MCWSServer(new InetSocketAddress(args[0],19232));
        CQWSServer cqserver = new CQWSServer(new InetSocketAddress("localhost",6700));
        mcserver.cqwsServer = cqserver;
        cqserver.mcwsServer = mcserver;
        cqserver.start();
        mcserver.start();
    }
}
