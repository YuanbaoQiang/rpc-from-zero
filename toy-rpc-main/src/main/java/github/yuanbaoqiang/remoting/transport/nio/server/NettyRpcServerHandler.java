package github.yuanbaoqiang.remoting.transport.nio.server;

import github.yuanbaoqiang.remoting.dto.RpcRequest;
import github.yuanbaoqiang.remoting.dto.RpcResponse;
import github.yuanbaoqiang.remoting.service.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 13:11
 */
@AllArgsConstructor
public class NettyRpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private ServiceProvider serviceProvider;
    private static final Logger logger = LoggerFactory.getLogger(NettyRpcServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        RpcResponse rpcResponse = getRpcResponse(rpcRequest);
        ctx.writeAndFlush(rpcResponse);
        ctx.close();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private RpcResponse getRpcResponse(RpcRequest rpcRequest){
        String interfaceName = rpcRequest.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);
        Method method = null;
        try {
            method= service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            Object invoke = method.invoke(service, rpcRequest.getArgs());
            logger.info("服务端顺利查询到数据...");
            RpcResponse success = RpcResponse.success(invoke);
            logger.info("响应{}封装成功", success);
            return success;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            logger.error("error occur: " + e.getMessage());
            return RpcResponse.fail();
        }
    }
}