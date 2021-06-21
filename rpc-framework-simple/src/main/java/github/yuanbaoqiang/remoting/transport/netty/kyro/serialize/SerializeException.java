package github.yuanbaoqiang.remoting.transport.netty.kyro.serialize;

/**
 * @description: 自定义序列化异常
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-21 13:33
 */
public class SerializeException extends RuntimeException{
    public SerializeException(String message){
        super(message);
    }
}