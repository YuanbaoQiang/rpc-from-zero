package github.yuanbaoqiang.remoting.transport.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @description: 随机负载均衡
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 13:44
 */
public class RandomLoadBalance implements LoadBalance{
    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int choose = random.nextInt(addressList.size());
        return addressList.get(choose);
    }
}