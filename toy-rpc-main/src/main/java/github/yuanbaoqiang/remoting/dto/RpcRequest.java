package github.yuanbaoqiang.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户端应该将需要调用的Service的接口名称，方法名，参数类型，参数传给服务端，
 * 服务端可以根据这些信息通过反射的方式调用相应的方法
 * @description: 请求的传输实体
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 14:48
 */
@Builder
@Data
@AllArgsConstructor
public class RpcRequest implements Serializable {
    // 接口名，客户端只知道接口名称，服务端根据接口名寻找对于的实现类
    private String interfaceName;
    // 方法名
    private String methodName;
    // 形参类型
    private Class<?>[] paramTypes;
    // 形参列表
    private Object[] args;
}