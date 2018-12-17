package com.specialyang.serializer;

import com.specialyang.codec.PacketCodeC;
import com.specialyang.enumeration.SerializerAlgorithm;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * Created by Fan Yang in 2018/12/17 5:06 PM.
 */
public class SerializerInit {

    public static void loadSpiSupport(String serializerAlgoName) {
        SerializerAlgorithm serializerAlgorithm = SerializerAlgorithm.acquire(serializerAlgoName);
        ServiceLoader<Serializer> serializers = ServiceLoader.load(Serializer.class);
        Serializer serializer = StreamSupport.stream(serializers.spliterator(), false)
                .filter(v -> Objects.equals(v.getSerializerAlgorithm(), serializerAlgorithm.getCode()))
                .findFirst().orElse(new JSONSerializer());
        PacketCodeC.INSTANCE.setSerializer(serializer);
    }

}
