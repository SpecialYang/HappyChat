package com.specialyang.handler.client;

import com.specialyang.packet.GroupMessageResponsePacket;
import com.specialyang.session.Session;
import com.specialyang.util.LogUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/12/1 6:22 PM.
 */
@ChannelHandler.Sharable
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();

    protected GroupMessageResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        String fromUserId = groupMessageResponsePacket.getFromGroupId();
        Session fromUser = groupMessageResponsePacket.getFromUser();
        LogUtil.print("收到群 [" + fromUserId + "] 中 [" + fromUser + "] 发来消息：" + groupMessageResponsePacket.getMessage());
    }
}
