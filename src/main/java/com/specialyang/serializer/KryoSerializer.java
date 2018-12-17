package com.specialyang.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Fan Yang in 2018/12/17 5:31 PM.
 */
public class KryoSerializer implements Serializer{

    @Override
    public byte getSerializerAlgorithm() {
        return 2;
    }

    @Override
    public byte[] serializer(Object object) {
        byte[] bytes;
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); Output output = new Output(outputStream)) {
            Kryo kryo = new Kryo();
            kryo.writeObject(output, object);
            bytes = output.toBytes();
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException("kryo serializer error" + e.getMessage());
        }
        return bytes;
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        T object;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            Kryo kryo = new Kryo();
            Input input = new Input(inputStream);
            object = kryo.readObject(input, clazz);
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("kryo deSerializer error" + e.getMessage());

        }
        return object;
    }

    @Override
    public String toString() {
        return "jaja";
    }
}
