package com.specialyang.handler.server;

import com.specialyang.util.LogUtil;
import com.specialyang.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Fan Yang in 2018/11/30 5:04 PM.
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    protected AuthHandler() {}

    /**
     * 因为未指定感兴趣的请求对象
     * 所以所有的请求对象都会被该处理器处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            //如果未登录成功，直接关闭连接
            ctx.channel().close();
        } else {
            //验证成功后，之后本次连接所有的数据无需再走这个处理器
            ctx.pipeline().remove(this);
            //否则传递给下一个处理器继续使用
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            LogUtil.print("当前连接登录验证完毕，无需再次验证，AuthHandler 被移除");
        } else {
            LogUtil.print("无法登录验证，强制关闭连接！");
        }
    }
}
