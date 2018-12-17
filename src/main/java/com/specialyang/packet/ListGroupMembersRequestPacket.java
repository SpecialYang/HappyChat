package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import lombok.Data;

/**
 * Created by Fan Yang in 2018/12/1 5:07 PM.
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST.getCode();
    }
}
