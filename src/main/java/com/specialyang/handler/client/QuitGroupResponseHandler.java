package com.specialyang.handler.client;

import com.specialyang.packet.QuitGroupResponsePacket;
import com.specialyang.util.LogUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/12/1 4:49 PM.
 */
@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();

    protected QuitGroupResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        if (quitGroupResponsePacket.isSuccess()) {
            LogUtil.print("退出群 [" + quitGroupResponsePacket.getGroupId() + "] 成功");
        } else {
            LogUtil.print("退出群 [" + quitGroupResponsePacket.getGroupId() + "] 失败，原因为："
                    + quitGroupResponsePacket.getReason());

        }
    }
}
