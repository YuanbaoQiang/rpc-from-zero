package github.yuanbaoqiang.remoting.transport.socket.server;

import github.yuanbaoqiang.dto.RpcRequest;
import github.yuanbaoqiang.dto.RpcResponse;
import github.yuanbaoqiang.dto.User;
import github.yuanbaoqiang.service.UserService;
import github.yuanbaoqiang.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 服务类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 15:14
 */
public class RpcSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(RpcSocketServer.class);
    // 注入服务接口的实现类
    private UserService userService = new UserServiceImpl();

    public void start(int port){
        // 1. 服务端开始监听端口
        try(ServerSocket server = new ServerSocket(port);){
            logger.info("服务端开始监听端口: [{}]", port);
            Socket socket;
            // 2. 监听数据
            while((socket = server.accept()) != null){
                logger.info("服务器得到来自客户端的RpcResponse");
                try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
                    // 服务端获得请求实体
                    RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
                    // 接口名称
//                    String interfaceName = rpcRequest.getInterfaceName();
                    // 方法名
                    String methodName = rpcRequest.getMethodName();
                    // 形参类型列表
                    Class<?>[] paramTypes = rpcRequest.getParamTypes();
                    // 形参列表
                    Object[] args = rpcRequest.getArgs();


                    // 通过反射得到具体的方法
                    Method method = userService.getClass().getMethod(methodName, paramTypes);
                    logger.info("通过动态代理找到具体的方法: {}", method.getName());
                    // 执行invoke方法
                    Object invoke = method.invoke(userService, args);

                    logger.info("服务器将数据封装成RpcResponse返回给客户端");
                    objectOutputStream.writeObject(RpcResponse.success(invoke));
                    objectOutputStream.flush();
                }
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("error happened: " + e);
        }
    }

    public static void main(String[] args) {
        RpcSocketServer rpcSocketServer = new RpcSocketServer();
        rpcSocketServer.start(8879);
    }
}