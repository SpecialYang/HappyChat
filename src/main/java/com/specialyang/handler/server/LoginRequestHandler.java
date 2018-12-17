package com.specialyang.handler.server;

import com.specialyang.packet.LoginRequestPacket;
import com.specialyang.packet.LoginResponsePacket;
import com.specialyang.session.Session;
import com.specialyang.util.LogUtil;
import com.specialyang.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Fan Yang in 2018/11/30 2:12 PM.
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        LogUtil.print("收到客户端登录请求......");
        //构造登录请求的响应包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUsername(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            String userId = SessionUtil.generateUserId();
            loginResponsePacket.setUserId(userId);
            LogUtil.print("[" + loginRequestPacket.getUsername() + "] 登录成功");
            loginResponsePacket.setSuccess(true);
            //保留登录信息，创建session，并绑定到管道上
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号或者密码错误");
            LogUtil.print("登录失败!");

        }
        //写回被客户端，会被encoder处理器自动编码
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
