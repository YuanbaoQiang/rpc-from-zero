package github.yuanbaoqiang.remoting.dto;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class RpcResponse implements Serializable {
    // 状态信息
    // 状态码
    private int code;
    private String message;
    // 数据
    private Object data;
    // 更新,这里我们需要加入这个，不然用其它序列化方式（除了java Serialize）得不到data的type
    private Class<?> dataType;

    public static RpcResponse success(Object data){
        return RpcResponse.builder()
                .code(200)
                .data(data).dataType(data.getClass()).build();
    }

    public static RpcResponse fail(){
        return RpcResponse.builder()
                .code(500)
                .message("服务器发生错误").build();
    }
}