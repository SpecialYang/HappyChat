package com.specialyang.handler.server;

import com.specialyang.handler.client.CreateGroupResponseHandler;
import com.specialyang.packet.CreateGroupRequestPacket;
import com.specialyang.packet.CreateGroupResponsePacket;
import com.specialyang.util.IDUtil;
import com.specialyang.util.LogUtil;
import com.specialyang.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fan Yang in 2018/12/1 3:36 PM.
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    protected CreateGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        //Channel set 集合， 当Channel关闭时，自动退出组，提供channel的批量操作方法
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUsername());
            } else {
                LogUtil.errPrint("[" + userId + "] 用户不在线，拉取他/她失败！");
            }
        }

        String groupId = IDUtil.generatorId();
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        SessionUtil.bindChannelGroup(groupId, channelGroup);

        //通知组内每一个成员
        channelGroup.writeAndFlush(createGroupResponsePacket);

        LogUtil.print("群创建成功，id为 [" + createGroupResponsePacket.getGroupId()
                + "], 群里面有: " + createGroupResponsePacket.getUserNameList());
    }
}
