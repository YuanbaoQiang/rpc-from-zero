package github.yuanbaoqiang.remoting.transport.endec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description: 我的解码类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 09:57
 */
@AllArgsConstructor
public class MyDecode extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(MyDecode.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.info("开始反序列化...");
        // 1. 读取消息类型
        short messageType = in.readShort();
        logger.info("收到{}", messageType == 0 ? "request" : "response");
        // 目前只支持request和response
        if(messageType != MessageType.REQUEST.getCode()
                && messageType != MessageType.RESPONSE.getCode()){
            logger.info("不支持的消息格式...");
            return;
        }
        // 2. 读取序列化的类型
        short serializerType = in.readShort();
        // 根据类型得到相应的序列器
        Serializer serializer = Serializer.getSerializerByCode(serializerType);
        logger.info("获得序列化器{}", serializerType == 0 ? "java" : "json");
        if(serializer == null){
            throw new RuntimeException("不存在相应的序列器");
        }
        // 3. 读取数据序列化后的字节长度
        int length = in.readInt();
        // 4. 读取序列化数组
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        // 5. 用相应的序列化器解码字节数组
        logger.info("解码字节数组");
        Object deserialize = serializer.deSerialize(bytes, messageType);
        out.add(deserialize);
    }
}