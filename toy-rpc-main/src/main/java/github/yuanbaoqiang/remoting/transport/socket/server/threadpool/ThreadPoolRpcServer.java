package github.yuanbaoqiang.remoting.transport.socket.server.threadpool;

import github.yuanbaoqiang.provider.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 基于线程池的Rpc服务器
 * @author: YuanbaoQiang
 * @create: 2021-06-23 22:00
 */
public class ThreadPoolRpcServer implements RpcServer{
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolRpcServer.class);
    private final ThreadPoolExecutor threadPoolExecutor;
//    private Map<String, Object> serviceProvide;
    private ServiceProvider serviceProvider;

    public ThreadPoolRpcServer(ServiceProvider serviceProvider){
        threadPoolExecutor = new ThreadPoolExecutor(5,
                10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));
        this.serviceProvider = serviceProvider;
    }

    public ThreadPoolRpcServer(ServiceProvider serviceProvider,
                               int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue){
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            logger.info("服务器正在监听端口: {}", port);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket;
            while((socket = serverSocket.accept()) != null){
                threadPoolExecutor.execute(new WorkThread(socket, serviceProvider));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
