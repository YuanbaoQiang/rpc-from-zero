package github.yuanbaoqiang.remoting.transport.nio.client;


import github.yuanbaoqiang.remoting.transport.endec.JsonSerializer;
import github.yuanbaoqiang.remoting.transport.endec.MyDecode;
import github.yuanbaoqiang.remoting.transport.endec.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 13:56
 */
@NoArgsConstructor
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
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
//        pipeline.addLast(new NettyClientHandler());


        // 添加自定义的编码、解码器
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 使用自定义的编解码器
        pipeline.addLast(new MyDecode());
        // 编码需要传入序列化器，这里是json，还支持ObjectSerializer，也可以自己实现其他的
        pipeline.addLast(new MyEncode(new JsonSerializer()));
        pipeline.addLast(new NettyClientHandler());
    }
}