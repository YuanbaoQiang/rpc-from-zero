package github.yuanbaoqiang.remoting.socket;

import github.yuanbaoqiang.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @description: 客户端socket测试类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-20 22:48
 */
public class HelloClient {

    private static final Logger logger = LoggerFactory.getLogger(HelloClient.class);

    public Object send(Message message, String host, int port) {
        // 1. 创建一个socket，指定ip和端口
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 2. 客户端往socket的输出流中发送请求的消息
            objectOutputStream.writeObject(message);
            // 3. 客户端往socket的输入流中接收来自服务端的响应消息
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            logger.error("error happened:" + e);
        }
        return null;
    }

    public static void main(String[] args) {
        HelloClient helloClient = new HelloClient();
        Message message = (Message) helloClient.send(new Message("this is the message from client"), "127.0.0.1", 6789);
        System.out.println("client receive message: " + message.getContent());
    }

}
