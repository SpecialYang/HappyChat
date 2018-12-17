package com.specialyang.handler.server;

import com.specialyang.codec.PacketCodeC;
import com.specialyang.packet.*;
import com.specialyang.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Fan Yang in 2018/11/30 9:46 AM.
 */
@Deprecated
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    @Deprecated
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);


        if (packet instanceof LoginRequestPacket) {
            LogUtil.print("收到客户端登录请求......");
            //构造登录请求的响应包
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号或者密码错误");
            }
            //写回被客户端
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            LogUtil.print("收到客户端信息：" + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf messageResponseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), messageResponsePacket);
            ctx.channel().writeAndFlush(messageResponseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
