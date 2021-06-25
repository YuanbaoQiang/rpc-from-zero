package github.yuanbaoqiang.remoting.transport.bio.socket.client;

import github.yuanbaoqiang.remoting.dto.RpcRequest;
import github.yuanbaoqiang.remoting.dto.RpcResponse;
import github.yuanbaoqiang.remoting.transport.common.RpcClient;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @description: 简单的Rpc客户端类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 12:39
 */
@AllArgsConstructor
public class SimleRpcClient implements RpcClient {
    private String host;
    private int port;

    @Override
    public RpcResponse sendMessage(RpcRequest rpcRequest) {
        // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
        // 这里的request是封装好的，不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
        try {
            // 发起一次Socket连接请求
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(rpcRequest);
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            RpcResponse response = (RpcResponse) objectInputStream.readObject();

            //System.out.println(response.getData());
            return response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }
    }
}