package com.specialyang.enumeration;

/**
 * Created by Fan Yang in 2018/11/30 8:44 AM.
 */
public enum SerializerAlgorithm {

    JSON((byte) 1, "FastJson");

    private byte code;

    private String kind;

    SerializerAlgorithm(byte code, String kind) {
        this.code = code;
        this.kind = kind;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public static SerializerAlgorithm valueOf(byte code) {
        for (SerializerAlgorithm algorithm : values()) {
            if (algorithm.getCode() == code) {
                return algorithm;
            }
        }
        return null;
    }
}
