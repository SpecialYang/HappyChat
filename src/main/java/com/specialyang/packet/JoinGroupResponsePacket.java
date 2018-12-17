package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import lombok.Data;

/**
 * Created by Fan Yang in 2018/12/1 4:19 PM.
 */
@Data
public class JoinGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE.getCode();
    }
}
