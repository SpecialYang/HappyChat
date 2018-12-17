package com.specialyang.handler.client;

import com.specialyang.packet.ListGroupMembersResponsePacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/12/1 5:33 PM.
 */
@ChannelHandler.Sharable
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    public static final ListGroupMembersResponseHandler INSTANCE = new ListGroupMembersResponseHandler();

    protected ListGroupMembersResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
        LogUtil.print("群 [" + listGroupMembersResponsePacket.getGroupId() + "] 中的人包括："
                + listGroupMembersResponsePacket.getSessionList());
    }
}
