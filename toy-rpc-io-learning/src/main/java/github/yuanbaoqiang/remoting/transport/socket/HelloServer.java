package github.yuanbaoqiang.remoting.transport.socket;

import github.yuanbaoqiang.dto.io.learning.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 服务端socket测试类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-20 22:50
 */
public class HelloServer {

    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);

    public void start(int port) {
        // try(){...}，()中的资源会释放
        // 1. 创建一个绑定端口的ServerSocket对象
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("server is listening...");
            Socket socket;
            // 2. 通过accept()方法监听端口上数据
            while ((socket = server.accept()) != null) {
                logger.info("client connected");
                try (
                        ObjectInputStream objectInputStream
                                = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream
                                = new ObjectOutputStream(socket.getOutputStream())
                ) {
                    // 3. 通过输入流获取客户端传来的请求信息
                    Message message = (Message) objectInputStream.readObject();
                    logger.info("server receive content: " + message.getContent());
                    // 4. 通过输出流给客户端发送响应消息
                    message.setContent("this is the message from server");
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();

                } catch (IOException | ClassNotFoundException e) {
                    logger.error("error happened: " + e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloServer helloServer = new HelloServer();
        helloServer.start(6789);
    }
}
