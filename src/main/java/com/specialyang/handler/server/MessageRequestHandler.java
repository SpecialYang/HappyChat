package com.specialyang.handler.server;

import com.specialyang.packet.MessageRequestPacket;
import com.specialyang.packet.MessageResponsePacket;
import com.specialyang.session.Session;
import com.specialyang.util.LogUtil;
import com.specialyang.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/11/30 2:16 PM.
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    protected MessageRequestHandler() {}

    /**
     * 消息转发
     *
     * 发送方 -> 服务器 -> 接收方
     * @param ctx
     * @param messageRequestPacket
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        //拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());
        //构造消息给接收方的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUsername());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        //拿到消息接收方的管道
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        //向消息接收方的管道里写消息
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            LogUtil.errPrint("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败");
        }
    }
}
