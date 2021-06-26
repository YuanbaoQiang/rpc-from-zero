package github.yuanbaoqiang.common;


import github.yuanbaoqiang.common.dto.RpcRequest;
import github.yuanbaoqiang.common.dto.RpcResponse;
import github.yuanbaoqiang.transmission.center.ServiceProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @description: 工作线程
 * @author: YuanbaoQiang
 * @create: 2021-06-23 21:39
 */
@AllArgsConstructor
public class WorkThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WorkThread.class);
    private final Socket socket;
    private final ServiceProvider serviceProvider;

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 读取RpcRequest
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            // 反射得到服务方法获得返回值
            RpcResponse rpcResponse = getRpcResponse(rpcRequest);
            // 写入客户端
            objectOutputStream.writeObject(rpcResponse);
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("error occur: {}", e.getMessage());
        }
    }

    private RpcResponse getRpcResponse(RpcRequest rpcRequest){
        // 得到服务名称
        String interfaceName = rpcRequest.getInterfaceName();
        // 得到服务类相应的服务实现类
        Object service = serviceProvider.getService(interfaceName);
        // 反射调用方法
        Method method = null;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            Object invoke = method.invoke(service, rpcRequest.getArgs());
            return RpcResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            logger.error("error occur: " + e.getMessage());
            return RpcResponse.fail();
        }
    }
}
