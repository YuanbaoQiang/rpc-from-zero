package github.yuanbaoqiang.remoting.dto.io.learning;

import lombok.*;

/**
 * @description: 服务端响应实体类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-21 10:14
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@ToString
public class RpcResponse {
    private String message;
}