package github.yuanbaoqiang.remoting.transport.bio.socket.server.threadpool;


import github.yuanbaoqiang.remoting.dto.Blog;
import github.yuanbaoqiang.remoting.dto.User;
import github.yuanbaoqiang.remoting.service.ServiceProvider;
import github.yuanbaoqiang.remoting.transport.proxy.jdk.ClientProxy;
import github.yuanbaoqiang.remoting.service.BlogService;
import github.yuanbaoqiang.remoting.service.UserService;
import github.yuanbaoqiang.remoting.service.impl.BlogServiceImpl;
import github.yuanbaoqiang.remoting.service.impl.UserServiceImpl;
import github.yuanbaoqiang.remoting.transport.common.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 服务端测试
 * @author: YuanbaoQiang
 * @create: 2021-06-23 22:17
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    @org.testng.annotations.Test
    public void testSimpleRpcServer(){
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RpcServer RPCServer = new SimpleRpcServer(serviceProvider);
        RPCServer.start(8879);
    }

    @org.testng.annotations.Test
    public void testThreadPoolRpcServer(){
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RpcServer RPCServer = new ThreadPoolRpcServer(serviceProvider);
        RPCServer.start(8879);
    }

    @org.testng.annotations.Test
    public void testClient(){
        // 客户中添加新的测试用例
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8879);
        BlogService blogService = clientProxy.getProxyInstance(BlogService.class);
        Blog blog = blogService.getBlogById(10000);
        logger.info("客户端收到来自服务端的响应 {}", blog);
        UserService userService = clientProxy.getProxyInstance(UserService.class);
        Integer integer = userService.inserUser(new User());
        logger.info("客户端收到来自服务端的响应 {}", integer > 0 ? "成功插入数据" : "插入数据失败");
    }
}
