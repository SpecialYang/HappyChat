package com.specialyang.handler.client;

import com.specialyang.packet.LoginResponsePacket;
import com.specialyang.session.Session;
import com.specialyang.util.LogUtil;
import com.specialyang.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/11/30 2:25 PM.
 */
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    protected LoginResponseHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.print("客户端开始登录");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {

        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUsername();

        if (loginResponsePacket.isSuccess()) {
            //客户端保存登录状态吗？？
            LogUtil.print("[" + userName + "] 登录成功，userId为：" + loginResponsePacket.getUserId());
            //因为是两个不同的进程，所以map是不共用的
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            LogUtil.print("[" + userName + "] 登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.print("客户端连接关闭!");
    }
}
