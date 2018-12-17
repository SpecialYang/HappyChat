package com.specialyang.consolecommand;

import com.specialyang.packet.JoinGroupRequestPacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by Fan Yang in 2018/12/1 4:15 PM.
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        LogUtil.print("输入groupId，加入群聊：");

        String groupId = scanner.next();

        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
