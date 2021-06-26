package github.yuanbaoqiang.transmission.server;


import github.yuanbaoqiang.common.endec.JsonSerializer;
import github.yuanbaoqiang.common.endec.MyDecode;
import github.yuanbaoqiang.common.endec.MyEncode;
import github.yuanbaoqiang.transmission.center.ServiceProvider;
import github.yuanbaoqiang.transmission.client.NettyRpcClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 负责序列化的编码解码，解决netty的粘包问题
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 12:54
 */
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private static Logger logger = LoggerFactory.getLogger(NettyRpcClient.class);
    private ServiceProvider serviceProvider;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        logger.info("初始化Channel");
//        // 每一个channel只有一个pipeline
//        ChannelPipeline pipeline = socketChannel.pipeline();
//        // 消息格式：[长度][消息体]
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//        // 计算当前待发送消息的长度，写入到前四个字节中
//        pipeline.addLast(new LengthFieldPrepender(4));
//
//        // 使用java默认的序列化方式
//        pipeline.addLast(new ObjectEncoder());
//        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
//            @Override
//            public Class<?> resolve(String className) throws ClassNotFoundException {
//                return Class.forName(className);
//            }
//        }));
//
//        pipeline.addLast(new NettyRpcServerHandler(serviceProvider));

        // 添加自定义的编码、解码器
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 使用自定义的编解码器
        pipeline.addLast(new MyDecode());
        // 编码需要传入序列化器，这里是json，还支持ObjectSerializer，也可以自己实现其他的
        pipeline.addLast(new MyEncode(new JsonSerializer()));
        pipeline.addLast(new NettyRpcServerHandler(serviceProvider));
    }
}