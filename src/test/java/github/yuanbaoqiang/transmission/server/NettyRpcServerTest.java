package github.yuanbaoqiang.transmission.server;

import github.yuanbaoqiang.transmission.center.ServiceProvider;
import github.yuanbaoqiang.common.service.BlogService;
import github.yuanbaoqiang.common.service.UserService;
import github.yuanbaoqiang.common.service.impl.BlogServiceImpl;
import github.yuanbaoqiang.common.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @description:
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 16:30
 */
public class NettyRpcServerTest {

    @Test
    public void testStartListening(){
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8899);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);
        NettyRpcServer nettyRpcServer = new NettyRpcServer(serviceProvider);
        nettyRpcServer.startListening(8899);
    }
}