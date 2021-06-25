package github.yuanbaoqiang.remoting.transport.bio.socket.server;

import github.yuanbaoqiang.remoting.dto.User;
import github.yuanbaoqiang.remoting.service.UserService;
import github.yuanbaoqiang.remoting.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 服务端
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 11:18
 */
public class SocketServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    // 注入服务类对象
    private UserService userService = new UserServiceImpl();

    public void start(int port){
        // 1. 服务端开始监听端口
        try(ServerSocket server = new ServerSocket(port);){
            logger.info("服务端开始监听端口: [{}]", port);
            Socket socket;
            // 2. 监听数据
            while((socket = server.accept()) != null){
                try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
                    int id = (Integer) objectInputStream.readObject();
                    logger.info("服务端收到客户端传来的用户id: [{}]", id);
                    User user = userService.queryUserById(id);
                    logger.info("服务端查询得到id为[{}]的用户信息: [{}], [{}]", id, user.getName(), user.getSex());
                    // 3. 服务端将查询得到的User对象传给客户端
                    objectOutputStream.writeObject(user);
                    objectOutputStream.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("error happened: " + e);
        }
    }

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.start(8879);
    }
}