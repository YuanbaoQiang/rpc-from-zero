package github.yuanbaoqiang.remoting.transport.nio.client;

import github.yuanbaoqiang.remoting.dto.RpcRequest;
import github.yuanbaoqiang.remoting.dto.RpcResponse;
import github.yuanbaoqiang.remoting.service.impl.BlogServiceImpl;
import github.yuanbaoqiang.remoting.transport.common.RpcClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 14:13
 */
@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);
    private RpcClient client;

    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("执行方法{}前对RpcRequest进行封装", method.getName());
        RpcRequest request = RpcRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .args(args).paramTypes(method.getParameterTypes()).build();
        logger.info("客户端发送RpcRequest");
        RpcResponse response = client.sendMessage(request);
        logger.info("客户端收到来自服务端的RpcResponse...");
        logger.info("{}", response);
        logger.info("客户端收到来自服务端的RpcResponse: {}", response.getData());
        return response.getData();
    }
    public <T> T getProxy(Class<T> clazz){
        T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        logger.info("创建代理对象成功");
        return t;
    }
}