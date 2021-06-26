package github.yuanbaoqiang.transmission.center;

import java.util.List;

/**
 * @description: 轮询负载均衡
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 13:46
 */
public class RoundLoadBalance implements LoadBalance{
    private int choose = -1;
    @Override
    public String balance(List<String> addressList) {
        choose++;
        choose = choose % addressList.size();
        return addressList.get(choose);
    }
}