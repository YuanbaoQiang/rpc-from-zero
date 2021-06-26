package github.yuanbaoqiang.transmission.client;


import github.yuanbaoqiang.common.RpcClient;
import github.yuanbaoqiang.common.ServiceRegister;
import github.yuanbaoqiang.common.dto.RpcRequest;
import github.yuanbaoqiang.common.dto.RpcResponse;
import github.yuanbaoqiang.transmission.center.ZKServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @description: 基于Netty的客户端
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 13:43
 */
public class NettyRpcClient implements RpcClient {
    private static Logger logger = LoggerFactory.getLogger(NettyRpcClient.class);
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    private String host;
    private int port;
    private ServiceRegister serviceRegister;

    public NettyRpcClient(String host, int port){
        this.host = host;
        this.port = port;

    }

    public NettyRpcClient(){
        // 初始化注册中心，建立连接
        this.serviceRegister = new ZKServiceRegister();
    }

    // netty客户端初始化，重复使用
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
        logger.info("客户端初始化成功...");
    }

    @Override
    public RpcResponse sendRpcRequest(RpcRequest rpcRequest) {
        InetSocketAddress address = serviceRegister.serviceDiscovery(rpcRequest.getInterfaceName());
        String host = address.getHostName();
        int port = address.getPort();

        try {
            ChannelFuture channelFuture  = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            // 发送数据
            channel.writeAndFlush(rpcRequest);
            channel.closeFuture().sync();
            // 阻塞的获得结果，通过给channel设计别名，获取特定名字下的channel中的内容（这个在hanlder中设置）
            // AttributeKey是，线程隔离的，不会由线程安全问题。
            // 实际上不应通过阻塞，可通过回调函数
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
            RpcResponse rpcResponse = channel.attr(key).get();
            logger.info("客户端从channel中获取到RpcResponse");
            return rpcResponse;
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("客户端发送消息异常: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void stop() {

    }
}