package github.yuanbaoqiang.common;

import github.yuanbaoqiang.common.dto.RpcRequest;
import github.yuanbaoqiang.common.dto.RpcResponse;

/**
 * @description: 客户端接口类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 14:41
 */
public interface RpcClient {
    RpcResponse sendRpcRequest(RpcRequest rpcRequest);
    void stop();
}
