package com.specialyang.handler.client;

import com.specialyang.packet.JoinGroupResponsePacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/12/1 4:36 PM.
 */
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    protected JoinGroupResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if (joinGroupResponsePacket.isSuccess()) {
            LogUtil.print("加入群 [" + joinGroupResponsePacket.getGroupId() + "] 成功");
        } else {
            LogUtil.print("加入群 [" + joinGroupResponsePacket.getGroupId() + "] 失败，原因为："
                    + joinGroupResponsePacket.getReason());

        }
    }
}
