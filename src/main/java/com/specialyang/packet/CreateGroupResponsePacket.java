package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import lombok.Data;

import java.util.List;

/**
 * Created by Fan Yang in 2018/12/1 3:02 PM.
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE.getCode();
    }
}
