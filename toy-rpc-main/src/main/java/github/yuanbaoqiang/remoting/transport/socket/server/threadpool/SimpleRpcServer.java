package github.yuanbaoqiang.remoting.transport.socket.server.threadpool;

import github.yuanbaoqiang.provider.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 简单的RpcServer
 * @author: YuanbaoQiang
 * @create: 2021-06-24 18:43
 */
public class SimpleRpcServer implements RpcServer{
    private static final Logger logger = LoggerFactory.getLogger(SimpleRpcServer.class);
    private final ServiceProvider serviceProvider;

    public SimpleRpcServer(ServiceProvider serviceProvider){
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            logger.info("服务器正在监听端口: {}", port);
            ServerSocket serverSocket = new ServerSocket(port);
            // BIO的方式监听Socket
            while (true){
                Socket socket = serverSocket.accept();
                // 开启一个新线程去处理
                new Thread(new WorkThread(socket,serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
