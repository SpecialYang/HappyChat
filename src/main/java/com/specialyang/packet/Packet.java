package com.specialyang.packet;

import lombok.Data;

/**
 * Created by Fan Yang in 2018/11/30 8:29 AM.
 */
@Data
public abstract class Packet {

    private Byte version = 1;

    public abstract Byte getCommand();
}
