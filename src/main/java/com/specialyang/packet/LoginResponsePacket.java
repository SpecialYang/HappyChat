package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import lombok.Data;

/**
 * Created by Fan Yang in 2018/11/30 9:52 AM.
 */
@Data
public class LoginResponsePacket extends Packet{

    private String userId;

    private String username;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE.getCode();
    }
}
