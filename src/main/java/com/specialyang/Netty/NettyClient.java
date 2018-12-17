package com.specialyang.Netty;

import com.specialyang.codec.PacketDecoder;
import com.specialyang.codec.PacketEncoder;
import com.specialyang.codec.Spliter;
import com.specialyang.consolecommand.ConsoleCommandManager;
import com.specialyang.consolecommand.LoginConsoleCommand;
import com.specialyang.handler.PacketCodecHandler;
import com.specialyang.handler.client.*;
import com.specialyang.handler.server.IMIdleStateHandler;
import com.specialyang.packet.LoginRequestPacket;
import com.specialyang.packet.LogoutResponsePacket;
import com.specialyang.packet.MessageRequestPacket;
import com.specialyang.serializer.Serializer;
import com.specialyang.serializer.SerializerInit;
import com.specialyang.util.LogUtil;
import com.specialyang.util.LoginUtil;
import com.specialyang.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fan Yang in 2018/11/29 2:55 PM.
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static int PORT = 8000;
    private static final int MAX_RETRY = 5;
    private static String serializerName = "kryo";

    public static void main(String[] args) throws InterruptedException {

        SerializerInit.loadSpiSupport(serializerName);
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup client = new NioEventLoopGroup();
        bootstrap
                //指定线程模型
                .group(client)
                //指定IO类型为NIO
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                //设置连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //指定处理逻辑
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        /*
                          ch.pipeline(）返回的是和这条连接的处理逻辑链，责任链模式
                          各个处理单元按序对请求进行处理
                         */
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INTANCE);
                        ch.pipeline().addLast(LoginResponseHandler.INSTANCE);
                        ch.pipeline().addLast(LogoutResponseHandler.INSTANCE);
                        ch.pipeline().addLast(CreateGroupResponseHandler.INSTANCE);
                        ch.pipeline().addLast(JoinGroupResponseHandler.INSTANCE);
                        ch.pipeline().addLast(QuitGroupResponseHandler.INSTANCE);
                        ch.pipeline().addLast(ListGroupMembersResponseHandler.INSTANCE);
                        ch.pipeline().addLast(GroupMessageResponseHandler.INSTANCE);
                        ch.pipeline().addLast(MessageResponseHandler.INSTANCE);
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);

    }

    /**
     * 异步回调机制
     * 指数退避的客户端重连逻辑
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LogUtil.print("连接成功！");
                    Channel channel = ((ChannelFuture) future).channel();
                    //开启控制台线程
                    startConsoleThread(channel);
                } else if (retry == 0) {
                    LogUtil.errPrint("重试次数已用完，放弃连接");
                } else {
                    int order = (MAX_RETRY - retry) + 1;
                    //计算出下次发起连接的间隔，避免频繁向服务器发起连接
                    int delay = 1 << order;
                    LogUtil.errPrint(new Date() + "：连接失败，第" + order + "次重连...");
                    //调度连接
                    bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1),
                            delay, TimeUnit.SECONDS);
                }
            }
        });
    }

    /**
     * 开启输入数据线程
     * @param channel
     */
    private static void startConsoleThread(Channel channel) {
        Scanner input = new Scanner(System.in);
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(input, channel);
                } else {
                    consoleCommandManager.exec(input, channel);
                }
            }
        }).start();
    }
}
