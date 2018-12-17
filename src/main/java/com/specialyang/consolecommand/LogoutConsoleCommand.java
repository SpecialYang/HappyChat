package com.specialyang.consolecommand;

import com.specialyang.packet.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by Fan Yang in 2018/12/1 3:18 PM.
 */
public class LogoutConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
