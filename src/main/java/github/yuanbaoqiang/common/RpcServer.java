package github.yuanbaoqiang.common;

/**
 * @description: 服务端接口类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 14:42
 */
public interface RpcServer {
    void startListening(int port);
    void stop();
}
