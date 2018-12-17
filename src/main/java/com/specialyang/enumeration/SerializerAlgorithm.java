package com.specialyang.enumeration;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Fan Yang in 2018/11/30 8:44 AM.
 */
public enum SerializerAlgorithm {

    JSON((byte) 1, "fastJson"),

    KRYO((byte) 2, "kryo"),

    HESSIAN((byte) 3, "hessian"),

    PROTOSTUFF((byte) 4, "protostuff");

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

    /**
     * 根据指定的序列化名称找到对应枚举对象
     * 默认为JSON序列化器
     * @param serializerName
     * @return
     */
    public static SerializerAlgorithm acquire(final String serializerName) {
        Optional<SerializerAlgorithm> serializerAlgorithm =
                Arrays.stream(SerializerAlgorithm.values())
                        .filter(v -> Objects.equals(v.getKind(), serializerName))
                        .findFirst();
        return serializerAlgorithm.orElse(JSON);
    }
}
