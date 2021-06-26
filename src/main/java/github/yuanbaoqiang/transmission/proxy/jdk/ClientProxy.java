package github.yuanbaoqiang.transmission.proxy.jdk;

import github.yuanbaoqiang.common.RpcClient;
import github.yuanbaoqiang.common.dto.RpcRequest;
import github.yuanbaoqiang.common.dto.RpcResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 在发送消息之前，对RpcRequest直接封装，并且返回响应
 * @description: 客户端的代理类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 15:39
 */
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientProxy.class);
    private RpcClient client;

    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("执行方法{}前对RpcRequest进行封装", method.getName());
        RpcRequest request = RpcRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .args(args).paramTypes(method.getParameterTypes()).build();
        logger.info("客户端发送RpcRequest");
        RpcResponse response = client.sendRpcRequest(request);
        logger.info("客户端收到来自服务端的RpcResponse...");
        logger.info("{}", response);
        logger.info("客户端收到来自服务端的RpcResponse: {}", response.getData());
        return response.getData();
    }

    public <T> T getProxyInstance(Class<T> clazz){
        logger.info("创建代理对象");
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }
}