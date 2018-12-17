package com.specialyang.serializer;

import com.alibaba.fastjson.JSON;
import com.specialyang.enumeration.SerializerAlgorithm;

/**
 * Created by Fan Yang in 2018/11/30 8:43 AM.
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON.getCode();
    }

    @Override
    public byte[] serializer(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
