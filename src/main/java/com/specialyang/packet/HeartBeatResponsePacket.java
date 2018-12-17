package com.specialyang.packet;

import com.specialyang.enumeration.Command;

/**
 * Created by Fan Yang in 2018/12/2 11:58 AM.
 */
public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE.getCode();
    }
}
