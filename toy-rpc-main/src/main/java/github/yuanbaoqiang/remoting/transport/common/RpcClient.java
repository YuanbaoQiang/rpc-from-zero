package github.yuanbaoqiang.remoting.transport.common;

import github.yuanbaoqiang.remoting.dto.RpcRequest;
import github.yuanbaoqiang.remoting.dto.RpcResponse;

/**
 * @description: 客户端接口
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-25 12:31
 */
public interface RpcClient {
    RpcResponse sendMessage(RpcRequest rpcRequest);
}
