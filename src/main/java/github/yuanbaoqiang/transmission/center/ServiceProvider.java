package github.yuanbaoqiang.transmission.center;

import github.yuanbaoqiang.common.ServiceRegister;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 服务提供者
 * @author: YuanbaoQiang
 * @create: 2021-06-23 22:16
 */
@Data
@NoArgsConstructor
public class ServiceProvider {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProvider.class);
    private Map<String, Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;

    public ServiceProvider(String host, int port){
        // 传入服务端自己的ip和端口号
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new ZKServiceRegister();
    }

    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class clazz : interfaces){
            // 本机的映射表
            interfaceProvider.put(clazz.getName(),service);
            logger.info("在注册中心注册服务");
            serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port));
        }
    }

    public Object getService(String interfaceName){
        logger.info("ServiceProvider查询到了{}接口", interfaceProvider.get(interfaceName));
        return interfaceProvider.get(interfaceName);
    }
}
