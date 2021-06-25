package github.yuanbaoqiang.remoting.transport.proxy.jdk;

import github.yuanbaoqiang.remoting.dto.RpcRequest;
import github.yuanbaoqiang.remoting.dto.RpcResponse;
import github.yuanbaoqiang.remoting.transport.bio.socket.client.RpcSocketClient;
import github.yuanbaoqiang.remoting.service.impl.BlogServiceImpl;
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
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
    private String host;
    private int port;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法调用之前先对RpcRequest进行封装
        // 比如调用userService.queryUserById(int id)，此时的方法method就是queryUserById
        // getDeclaringClass()返回 方法的类的Class对象，getName()找到具体的Service名字
        logger.info("开始执行代理类的增强方法，封装rpcRequest");
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramTypes(method.getParameterTypes())
                .args(args).build();

        logger.info("客户端发送rpcRequest");
        RpcResponse rpcResponse = RpcSocketClient.sendMessage(host, port, rpcRequest);

        return rpcResponse.getData();
    }

    public <T> T getProxyInstance(Class<T> clazz){
        logger.info("创建代理对象");
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }
}