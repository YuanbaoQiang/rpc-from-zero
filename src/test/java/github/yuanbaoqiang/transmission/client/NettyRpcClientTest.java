package github.yuanbaoqiang.transmission.client;

import github.yuanbaoqiang.common.dto.User;
import github.yuanbaoqiang.common.service.UserService;
import github.yuanbaoqiang.transmission.proxy.jdk.ClientProxy;
import org.junit.Test;

/**
 * @description:
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 16:33
 */
public class NettyRpcClientTest {

    @Test
    public void sendRpcRequestTest(){
        ClientProxy rpcClientProxy = new ClientProxy(new NettyRpcClient());
        UserService proxyInstance = rpcClientProxy.getProxyInstance(UserService.class);
        Integer integer = proxyInstance.insertUser(new User());
    }
}