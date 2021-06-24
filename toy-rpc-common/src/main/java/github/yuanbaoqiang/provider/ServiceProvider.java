package github.yuanbaoqiang.provider;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 服务提供者
 * @author: YuanbaoQiang
 * @create: 2021-06-23 22:16
 */
@Data
public class ServiceProvider {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProvider.class);
    private Map<String, Object> interfaceProvider;

    public ServiceProvider(){
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service){
//        String serviceName = service.getClass().getName();
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class clazz : interfaces){
            interfaceProvider.put(clazz.getName(),service);
        }
    }

    public Object getService(String interfaceName){
        logger.info("ServiceProvider查询到了{}接口", interfaceProvider.get(interfaceName));
        return interfaceProvider.get(interfaceName);
    }
}
