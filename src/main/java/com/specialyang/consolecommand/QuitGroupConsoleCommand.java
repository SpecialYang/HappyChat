package com.specialyang.consolecommand;

import com.specialyang.packet.QuitGroupRequestPacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by Fan Yang in 2018/12/1 9:12 PM.
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();

        LogUtil.print("输入 groupId，退出群聊：");
        String groupId = scanner.next();

        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
