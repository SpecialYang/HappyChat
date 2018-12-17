package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import lombok.Data;

/**
 * Created by Fan Yang in 2018/11/30 8:33 AM.
 */
@Data
public class LoginRequestPacket extends Packet{

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST.getCode();
    }
}


