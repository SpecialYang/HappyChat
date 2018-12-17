package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import com.specialyang.session.Session;
import lombok.Data;

import java.util.List;

/**
 * Created by Fan Yang in 2018/12/1 4:19 PM.
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE.getCode();
    }
}
