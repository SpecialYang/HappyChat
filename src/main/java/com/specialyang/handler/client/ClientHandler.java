package com.specialyang.handler.client;

import com.specialyang.codec.PacketCodeC;
import com.specialyang.packet.LoginRequestPacket;
import com.specialyang.packet.LoginResponsePacket;
import com.specialyang.packet.MessageResponsePacket;
import com.specialyang.packet.Packet;
import com.specialyang.util.LogUtil;
import com.specialyang.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * Created by Fan Yang in 2018/11/30 9:34 AM.
 */
@Deprecated
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.print("客户端开始登录");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("SpecialYang");
        loginRequestPacket.setPassword("123456");

        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), loginRequestPacket);

        //netty对连接抽象为channel
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        /*
            我们只有通过判断消息的类型是什么，才能选择具体的处理逻辑
            所以我们可以把这些处理逻辑抽离为多个不同的handler
            但还是存在一个问题，我们还是需要判断当前的消息的类型
            是否是本处理器来处理的，所以我们的代码还是很慵懒
            所以netty封装了SimpleChannelInboundHandler
            自动帮助我们判断是否是感兴趣的对象类型和消息事件传播机制
         */
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                //客户端保存登录状态吗？？
                LoginUtil.markAsLogin(ctx.channel());
                LogUtil.print("客户端登录成功");
            } else {
                LogUtil.print("客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            LogUtil.print("收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }
}
