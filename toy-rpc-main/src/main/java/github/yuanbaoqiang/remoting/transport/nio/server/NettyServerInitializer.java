package github.yuanbaoqiang.remoting.transport.nio.server;

import github.yuanbaoqiang.remoting.service.ServiceProvider;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

/**
 * @description: 负责序列化的编码解码，解决netty的粘包问题
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 12:54
 */
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 每一个channel只有一个pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 消息格式：[长度][消息体]
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        // 计算当前待发送消息的长度，写入到前四个字节中
        pipeline.addLast(new LengthFieldPrepender(4));

        // 使用java默认的序列化方式
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String className) throws ClassNotFoundException {
                return Class.forName(className);
            }
        }));

        pipeline.addLast(new NettyRpcServerHandler(serviceProvider));
    }
}