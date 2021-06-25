package github.yuanbaoqiang.remoting.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 响应的传输实体
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 15:02
 */
@Builder
@Data
public class RpcResponse implements Serializable {
    // 状态信息
    // 状态码
    private int code;
    private String message;
    // 数据
    private Object data;

    public static RpcResponse success(Object data){
        return RpcResponse.builder()
                .code(200)
                .data(data).build();
    }

    public static RpcResponse fail(){
        return RpcResponse.builder()
                .code(500)
                .message("服务器发生错误").build();
    }
}