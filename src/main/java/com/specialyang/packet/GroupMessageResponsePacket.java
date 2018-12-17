package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import com.specialyang.session.Session;
import lombok.Data;

/**
 * Created by Fan Yang in 2018/12/1 6:10 PM.
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE.getCode();
    }
}
