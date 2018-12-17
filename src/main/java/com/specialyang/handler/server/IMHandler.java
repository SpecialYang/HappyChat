package com.specialyang.handler.server;

import com.specialyang.enumeration.Command;
import com.specialyang.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fan Yang in 2018/12/2 10:09 AM.
 *
 * 服务端 平级处理器压缩
 * 主要用于缩短事件传播路径
 */
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(Command.MESSAGE_REQUEST.getCode(), MessageRequestHandler.INSTANCE);
        handlerMap.put(Command.CREATE_GROUP_REQUEST.getCode(), CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.JOIN_GROUP_REQUEST.getCode(), JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.QUIT_GROUP_REQUEST.getCode(), QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_MESSAGE_REQUEST.getCode(), GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST.getCode(), ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(Command.LOGOUT_REQUEST.getCode(), LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        //不能直接调用channelRead0，因为参数类型还是Packet，依赖channelRead作类型强转
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
