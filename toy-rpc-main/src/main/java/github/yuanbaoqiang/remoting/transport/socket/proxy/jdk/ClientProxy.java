package github.yuanbaoqiang.remoting.transport.socket.proxy.jdk;

import github.yuanbaoqiang.dto.RpcRequest;
import github.yuanbaoqiang.dto.RpcResponse;
import github.yuanbaoqiang.remoting.transport.socket.client.RpcSocketClient;
import lombok.AllArgsConstructor;

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
    private String host;
    private int port;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法调用之前先对RpcRequest进行封装
        // 比如调用userService.queryUserById(int id)，此时的方法method就是queryUserById
        // getDeclaringClass()返回 方法的类的Class对象，getName()找到具体的Service名字
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramTypes(method.getParameterTypes())
                .args(args).build();

        RpcResponse rpcResponse = RpcSocketClient.sendMessage(host, port, rpcRequest);

        return rpcResponse.getData();
    }

    public <T> T getProxyInstance(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }
}