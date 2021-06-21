package github.yuanbaoqiang.remoting.transport.netty.kyro.serialize;

/**
 * @description: 序列化接口
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-21 13:26
 */
public interface Serializer {
    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     *
     * @param bytes 序列化后的字节数组
     * @param clazz 类
     * @param <T>
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
