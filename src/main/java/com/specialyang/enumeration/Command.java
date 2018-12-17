package com.specialyang.enumeration;

/**
 * Created by Fan Yang in 2018/11/30 8:35 AM.
 */
public enum Command {

    LOGIN_REQUEST((byte) 1, "登录请求"),
    LOGIN_RESPONSE((byte) 2, "登录响应"),
    MESSAGE_REQUEST((byte) 3, "消息请求"),
    MESSAGE_RESPONSE((byte) 4, "消息响应"),
    CREATE_GROUP_REQUEST((byte) 5, "创建群聊请求"),
    CREATE_GROUP_RESPONSE((byte) 6, "创建群聊响应"),
    LOGOUT_REQUEST((byte) 7, "退出请求"),
    LOGOUT_RESPONSE((byte) 8, "退出响应"),
    JOIN_GROUP_REQUEST((byte) 9, "加群请求"),
    JOIN_GROUP_RESPONSE((byte) 10, "加群响应"),
    QUIT_GROUP_REQUEST((byte) 11, "退群请求"),
    QUIT_GROUP_RESPONSE((byte) 12, "退群响应"),
    LIST_GROUP_MEMBERS_REQUEST((byte) 13, "获取群聊成员请求"),
    LIST_GROUP_MEMBERS_RESPONSE((byte) 14, "获取群聊成员响应"),
    GROUP_MESSAGE_REQUEST((byte) 15, "群聊请求"),
    GROUP_MESSAGE_RESPONSE((byte) 16, "群聊响应"),
    HEART_BEAT_REQUEST((byte) 17, "心跳请求"),
    HEART_BEAT_RESPONSE((byte) 18, "心跳响应");

    private byte code;

    private String desc;

    Command(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Command valueOf(byte code) {
        for (Command command : values()) {
            if (command.getCode() == code) {
                return command;
            }
        }
        return null;
    }
}
