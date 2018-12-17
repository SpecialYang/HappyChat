package com.specialyang.codec;

import com.specialyang.enumeration.Command;
import com.specialyang.enumeration.SerializerAlgorithm;
import com.specialyang.packet.*;
import com.specialyang.serializer.JSONSerializer;
import com.specialyang.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fan Yang in 2018/11/30 8:51 AM.
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    //饿汉式  单例模式
    public static final PacketCodeC INSTANCE = new PacketCodeC();
    private final Map<Enum<Command>, Class<? extends Packet>> packetTypeMap;
    private final Map<Enum<SerializerAlgorithm>, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);

        serializerMap = new HashMap<>();
        serializerMap.put(SerializerAlgorithm.JSON, new JSONSerializer());
    }

    /**
     * 编码
     * Java对象转化为二进制流
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serializer(packet);
        //填充各字段
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 解码
     * 二进制流转换为Java对象
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf) {

        byteBuf.skipBytes(4);

        byteBuf.skipBytes(1);

        byte serializerAlgorithm = byteBuf.readByte();

        byte commandCode = byteBuf.readByte();

        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestPacket = getRequestType(commandCode);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (requestPacket != null && serializer != null) {
            return serializer.deserializer(requestPacket, bytes);
        }
        return null;
    }

    /**
     * 根据字序列化器编号找映射的序列化器
     * @param serializerAlgorithm
     * @return
     */
    private Serializer getSerializer(byte serializerAlgorithm) {
        SerializerAlgorithm target = SerializerAlgorithm.valueOf(serializerAlgorithm);
        return serializerMap.get(target);
    }

    /**
     * 根据指令编号找映射的请求对象的class对象
     * @param commandCode
     * @return
     */
    private Class<? extends Packet> getRequestType(byte commandCode) {
        Command command = Command.valueOf(commandCode);
        return packetTypeMap.get(command);
    }
}
