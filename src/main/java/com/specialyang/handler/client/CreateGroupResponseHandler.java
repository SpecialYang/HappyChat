package com.specialyang.handler.client;

import com.specialyang.packet.CreateGroupResponsePacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/12/1 4:03 PM.
 */
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    protected CreateGroupResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        LogUtil.print("群创建成功，id为 [" + createGroupResponsePacket.getGroupId()
                + "], 群里面有: " + createGroupResponsePacket.getUserNameList());
    }
}
