package github.yuanbaoqiang.common.endec;

/**
 * @description: 序列化接口
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 09:32
 */
public interface Serializer {
    // 将对象序列化成字节数组
    byte[] serialize(Object obj);
    // 将字节数组反序列化成消息
    // java自带的序列化方式不需要messageType
    // 其他的方式需要指定消息格式，根据message转成相应的对象
    Object deSerialize(byte[] bytes, int messageType);
    // 返回使用的序列器
    // 0: java自带的序列化方式
    // 1: json序列化
    int getType();
    static Serializer getSerializerByCode(int code){
        switch (code){
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }

}
