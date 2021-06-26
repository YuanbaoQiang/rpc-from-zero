package github.yuanbaoqiang.common.endec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @description: Java原生序列化
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 10:11
 */
public class ObjectSerializer implements Serializer{
    private static final Logger logger = LoggerFactory.getLogger(MyDecode.class);
    // 利用java IO 对象 -> 字节数组
    @Override
    public byte[] serialize(Object obj) {
        logger.info("JsonSerializer开始序列化...");
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("序列化完成...");
        return bytes;
    }

    // 字节数组 -> 对象
    @Override
    public Object deSerialize(byte[] bytes, int messageType) {
        logger.info("JsonSerializer开始反序列化...");
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("反序列化完成...");
        return obj;
    }

    // 0 代表java原生序列化器
    @Override
    public int getType() {
        return 0;
    }
}