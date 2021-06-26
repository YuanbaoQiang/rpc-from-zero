package github.yuanbaoqiang.transmission.client;


import github.yuanbaoqiang.common.dto.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 13:51
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private static Logger logger = LoggerFactory.getLogger(NettyRpcClient.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        logger.info("执行channelRead0方法");
        // 接收到response, 给channel设计别名，让sendRequest里读取response
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
        ctx.channel().attr(key).set(msg);
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}