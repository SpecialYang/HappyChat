package com.specialyang.consolecommand;

import com.specialyang.packet.CreateGroupRequestPacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Fan Yang in 2018/12/1 2:54 PM.
 */
public class CreateGroupConsoleCommand implements ConsoleCommand{

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        LogUtil.print("[拉人群聊] 输入userId 列表，userId 之间用逗号隔开");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
