package com.specialyang.serializer;

/**
 * Created by Fan Yang in 2018/11/30 8:40 AM.
 *
 * 序列化器
 */
public interface Serializer {

    byte JSON_SERIALIZER = 1;

    /**
     * 提供默认序列化器
     *
     * 好牛逼的写法
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 得到序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 序列化对象
     * @param object
     * @return
     */
    byte[] serializer(Object object);

    /**
     * 反序列化对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserializer(Class<T> clazz, byte[] bytes);
}
