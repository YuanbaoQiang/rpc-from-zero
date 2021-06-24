package github.yuanbaoqiang.remoting.transport.socket.server.threadpool;

/**
 * @description: RpcServer的接口
 * @author: YuanbaoQiang
 * @create: 2021-06-23 21:25
 */
public interface RpcServer {
    void start(int port);

    void stop();
}
