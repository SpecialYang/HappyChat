package com.specialyang.packet;

import com.specialyang.enumeration.Command;

/**
 * Created by Fan Yang in 2018/12/1 3:20 PM.
 */
public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST.getCode();
    }
}
