package com.specialyang.consolecommand;

import com.specialyang.packet.LoginRequestPacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by Fan Yang in 2018/12/1 3:10 PM.
 */
public class LoginConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        LogUtil.print("输入用户名进行登录：");
        loginRequestPacket.setUsername(scanner.nextLine());
        loginRequestPacket.setPassword("123456");

        channel.writeAndFlush(loginRequestPacket);

        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
