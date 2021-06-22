package github.yuanbaoqiang.dto;

import lombok.*;

/**
 * @description: 客户端请求实体类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-21 10:13
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@ToString
public class RpcRequest {
    private String interfaceName;
    private String methodName;
}