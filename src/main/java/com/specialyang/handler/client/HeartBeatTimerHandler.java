package com.specialyang.handler.client;

import com.specialyang.packet.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fan Yang in 2018/12/2 11:51 AM.
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEART_BEAT_INTERNAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        //为了确保链中后续的channel中的Active方法也调用
        super.channelActive(ctx);
    }

    /**
     * 调度发送心跳包
     * @param ctx
     */
    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        //获取当前的ctx绑定的线程，类似JDK的延时任务机制
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                //链中直接传到编码器中
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                //再次调度
                scheduleSendHeartBeat(ctx);
            }
        }, HEART_BEAT_INTERNAL, TimeUnit.SECONDS);
    }
}
