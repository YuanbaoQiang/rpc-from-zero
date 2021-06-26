package github.yuanbaoqiang.common.endec;


import github.yuanbaoqiang.common.dto.RpcRequest;
import github.yuanbaoqiang.common.dto.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @description: 我的编码类，按照自定义消息格式写入，传入的数据为request或者response，
 * 需要持有一个序列化器，负责将传入的对象序列化成字节数组
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 09:47
 */
@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;
    private static final Logger logger = LoggerFactory.getLogger(MyEncode.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        logger.info("开始编码...");
        // 写入消息的类型
        if(msg instanceof RpcRequest){
            out.writeShort(MessageType.REQUEST.getCode());
        }else if(msg instanceof RpcResponse){
            out.writeShort(MessageType.RESPONSE.getCode());
        }
        logger.info("编码完成...");
        // 写入的序列化方式
        out.writeShort(serializer.getType());
        // 得到序列化数组
        byte[] serialize = serializer.serialize(msg);
        // 写入长度
        out.writeInt(serialize.length);
        // 写入序列化字节数组
        out.writeBytes(serialize);
    }
}