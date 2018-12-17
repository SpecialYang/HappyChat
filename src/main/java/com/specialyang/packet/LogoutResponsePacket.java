package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import lombok.Data;

/**
 * Created by Fan Yang in 2018/12/1 3:22 PM.
 */
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE.getCode();
    }
}
