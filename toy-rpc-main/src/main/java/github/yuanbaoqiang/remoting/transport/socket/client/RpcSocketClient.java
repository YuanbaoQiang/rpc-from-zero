package github.yuanbaoqiang.remoting.transport.socket.client;

import github.yuanbaoqiang.dto.RpcRequest;
import github.yuanbaoqiang.dto.RpcResponse;
import github.yuanbaoqiang.dto.User;
import github.yuanbaoqiang.remoting.transport.socket.proxy.jdk.ClientProxy;
import github.yuanbaoqiang.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * 对于客户端而言，只知道自己需要调用的Service接口，
 * RpcRequest的封装和RpcResponse的接收需要通过动态代理来实现
 * @description: 服务类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 15:15
 */
public class RpcSocketClient {

    // 在调用sendMessage方法前，rpcRequest就已经通过动态代理的方式封装好了
    public static RpcResponse sendMessage(String host, int port, RpcRequest rpcRequest) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (RpcResponse) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8879);
        // 获取对应服务的代理类
        UserService proxyInstance = clientProxy.getProxyInstance(UserService.class);
        // 代理类调用方法
        User user = proxyInstance.queryUserById(10);
    }
}