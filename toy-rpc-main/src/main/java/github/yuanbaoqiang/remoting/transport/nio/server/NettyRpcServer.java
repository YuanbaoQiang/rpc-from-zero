package github.yuanbaoqiang.remoting.transport.nio.server;

import github.yuanbaoqiang.remoting.service.ServiceProvider;
import github.yuanbaoqiang.remoting.transport.common.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 基于Netty的服务端
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 12:47
 */
@AllArgsConstructor
public class NettyRpcServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyRpcServer.class);
    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        logger.info("Netty服务端启动，监听端口: {}", port);
        try {
            // 启动netty服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 初始化
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvider));
            logger.info("Netty服务端初始化结束...");
            // 同步阻塞
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            logger.info("同步阻塞...");
            // 死循环监听
            channelFuture.channel().closeFuture().sync();
            logger.info("死循环监听...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}