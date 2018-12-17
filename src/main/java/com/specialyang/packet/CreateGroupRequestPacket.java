package com.specialyang.packet;

import com.specialyang.enumeration.Command;
import lombok.Data;

import java.util.List;

/**
 * Created by Fan Yang in 2018/12/1 2:59 PM.
 */
@Data
public class CreateGroupRequestPacket extends Packet {


    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST.getCode();
    }
}
