package github.yuanbaoqiang.remoting.transport.nio;


import github.yuanbaoqiang.remoting.dto.User;
import github.yuanbaoqiang.remoting.service.BlogService;
import github.yuanbaoqiang.remoting.service.ServiceProvider;
import github.yuanbaoqiang.remoting.service.UserService;
import github.yuanbaoqiang.remoting.service.impl.BlogServiceImpl;
import github.yuanbaoqiang.remoting.service.impl.UserServiceImpl;
import github.yuanbaoqiang.remoting.transport.nio.client.NettyRpcClient;
import github.yuanbaoqiang.remoting.transport.nio.client.RpcClientProxy;
import github.yuanbaoqiang.remoting.transport.nio.server.NettyRpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


/**
 * @description:
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 14:06
 */
public class ConnectTest {
    private static final Logger logger = LoggerFactory.getLogger(ConnectTest.class);
    ServiceProvider serviceProvider = new ServiceProvider();

    @Test
    public void testNettyRpcServer(){
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);
        NettyRpcServer nettyRpcServer = new NettyRpcServer(serviceProvider);
        nettyRpcServer.start(8879);
    }

    @Test
    public void testNettyClient(){
        RpcClientProxy rpcClientProxy = new RpcClientProxy(new NettyRpcClient("127.0.0.1", 8879));
        UserService proxy = rpcClientProxy.getProxy(UserService.class);
        logger.info("生成代理对象成功");
        Integer integer = proxy.inserUser(new User(10, "add", false));
//        System.out.println(proxy);
    }
}