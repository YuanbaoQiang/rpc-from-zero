package github.yuanbaoqiang.transmission.center;

import java.util.List;

/**
 * @description: 负载均衡接口
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 13:43
 */
public interface LoadBalance {
    String balance(List<String> addressList);
}
