package github.yuanbaoqiang.remoting.transport.register;

import java.net.InetSocketAddress;

/**
 * @description: 服务注册接口
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 11:29
 */
public interface ServiceRegister {
    // 注册服务
    void register(String serviceName, InetSocketAddress serverAddress);
    // 查询地址
    InetSocketAddress serviceDiscovery(String serviceName);
}
