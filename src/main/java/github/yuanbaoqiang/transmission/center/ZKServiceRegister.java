package github.yuanbaoqiang.transmission.center;

import github.yuanbaoqiang.common.ServiceRegister;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @description: zookeeper服务注册接口的实现类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 11:33
 */
public class ZKServiceRegister implements ServiceRegister {
    // curator提供的zookeeper客户端
    private CuratorFramework client;
    // zookeeper根路径节点
    private static final String ROOT_PATH = "MyRpc";
    private static final Logger logger = LoggerFactory.getLogger(ZKServiceRegister.class);
    // 初始化负载均衡器， 这里用的是随机， 一般通过构造函数传入
    private LoadBalance loadBalance = new RandomLoadBalance();

    public ZKServiceRegister(){
        // 指数时间重试
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        // zookeeper服务器的地址是固定的
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.client.start();
        logger.info("zookeeper客户端启动成功，连接zookeeper服务器成功...");
    }

    // zookeeper客户端的初始化，并与zookeeper服务端建立连接
    @Override
    public void register(String serviceName, InetSocketAddress serverAddress){
        try {
            // serviceName创建成永久节点，服务提供者下线时，不删服务名，只删地址
            if(client.checkExists().forPath("/" + serviceName) == null){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }
            // 路径地址，一个/代表一个节点
            String path = "/" + serviceName +"/"+ getServiceAddress(serverAddress);
            // 临时节点，服务器下线就删除节点
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            System.out.println("此服务已存在");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
//            List<String> strings = client.getChildren().forPath("/" + serviceName);
//            // 默认使用第一个
//            String str = strings.get(0);
//            return parseAddress(str);

            logger.info("采用随机负载均衡算法");
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            // 负载均衡选择器，选择一个
            String string = loadBalance.balance(strings);
            logger.info("找到: {}", string);
            return parseAddress(string);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("列表为空...");
        }
        return null;
    }

    // xxx.xxx.xxx.xxx:port
    private String getServiceAddress(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() + ":" + serverAddress.getPort();
    }

    // 字符串解析为地址
    private InetSocketAddress parseAddress(String address){
        String[] result = address.split(":");
        return new InetSocketAddress(result[0], Integer.parseInt(result[1]));
    }


}