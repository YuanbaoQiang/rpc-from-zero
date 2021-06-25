package github.yuanbaoqiang.remoting.transport.bio.socket.client;

import github.yuanbaoqiang.remoting.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * @description: 客户端
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 11:18
 */
public class SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    public Object sendMessage(String host, int port){
        try(Socket socket = new Socket(host, port)){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Random random = new Random();
            int id = random.nextInt();
            logger.info("客户端向{}:{}发送了一个用户id: {}", host, port, id);
            objectOutputStream.writeObject(id);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("error happened: " + e);
        }
        return null;
    }

    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        String host = "127.0.0.1";
        int port = 8879;
        User user = (User) client.sendMessage(host, port);
        logger.info("客户端收到来自{}:{}查询得到的用户对象: {}, {}, {}", host, port, user.getId(), user.getName(), user.getSex());
    }
}