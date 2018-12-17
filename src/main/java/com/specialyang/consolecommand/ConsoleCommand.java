package com.specialyang.consolecommand;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by Fan Yang in 2018/12/1 2:50 PM.
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);
}
