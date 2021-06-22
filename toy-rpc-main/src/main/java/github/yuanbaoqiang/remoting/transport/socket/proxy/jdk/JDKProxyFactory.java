package github.yuanbaoqiang.remoting.transport.socket.proxy.jdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Proxy;

/**
 * @description: 生成代理对象的工厂
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 18:33
 */
@Builder
@AllArgsConstructor
@Data
public class JDKProxyFactory {

    public static <T> T getProxyInstance(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyInvocationHandler());
        return (T) o;
    }
}