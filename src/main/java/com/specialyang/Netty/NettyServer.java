package com.specialyang.Netty;

import com.specialyang.codec.Spliter;
import com.specialyang.handler.PacketCodecHandler;
import com.specialyang.handler.server.*;
import com.specialyang.serializer.SerializerInit;
import com.specialyang.util.LogUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
/**
 * Created by Fan Yang in 2018/11/30 10:21 AM.
 */
public class NettyServer {

    private final static int PORT = 8000;
    private static String serializerName = "kryo";

    /**
     * 开启netty服务端
     * 需要指定三个属性：线程模型，IO模型，连接读写处理逻辑
     * @param args
     */
    public static void main(String[] args) {
        SerializerInit.loadSpiSupport(serializerName);
        //服务端的一些初始化工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //监听连接请求，建立新的连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        //负责读写数据的线程，主要用于读取数据以及业务逻辑处理
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                /*
                    给监听连接请求的管道设置属性
                    SO_BACKLOG：用于临时存放已完成三次握手的请求的队列的最大长度
                    高并发下，可以适当提高此参数
                 */
                .option(ChannelOption.SO_BACKLOG, 1024)
                /*
                    childOption用于对每一条连接设置一些的TCP底层相关的属性
                    SO_KEEPALIVE：心跳检测机制
                    TCP_NODELAY：有数据就发送
                 */
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    //指定数据读写处理逻辑
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap, PORT);
    }

    /**
     * 尝试绑定端口，若失败则自动尝试增1，看是否成功。
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LogUtil.print("端口[" + port + "]绑定成功！");
                } else {
                    LogUtil.print("端口[" + port + "]绑定失败！");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
