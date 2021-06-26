package github.yuanbaoqiang.common.endec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import github.yuanbaoqiang.common.dto.RpcRequest;
import github.yuanbaoqiang.common.dto.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: JSON序列化
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 10:13
 */
public class JsonSerializer implements Serializer {
    private static final Logger logger = LoggerFactory.getLogger(MyDecode.class);

    @Override
    public byte[] serialize(Object obj) {
        logger.info("JsonSerializer开始序列化...");
        byte[] bytes = JSONObject.toJSONBytes(obj);
        logger.info("JsonSerializer序列化成功，得到字节数组，数组大小: {}", bytes.length);
        return bytes;
    }

    @Override
    public Object deSerialize(byte[] bytes, int messageType) {
        logger.info("JsonSerializer开始反序列化...");
        Object obj = null;
        // 传输的消息分为request与response
        switch (messageType) {
            case 0:
                RpcRequest request = JSON.parseObject(bytes, RpcRequest.class);
                Object[] objects = new Object[request.getArgs().length];
                // 把json字串转化成对应的对象， fastjson可以读出基本数据类型，不用转化
                for (int i = 0; i < objects.length; i++) {
                    Class<?> paramsType = request.getParamTypes()[i];
                    if (!paramsType.isAssignableFrom(request.getArgs()[i].getClass())) {
                        objects[i] = JSONObject.toJavaObject((JSONObject) request.getArgs()[i], request.getParamTypes()[i]);
                    } else {
                        objects[i] = request.getArgs()[i];
                    }
                }
                request.setArgs(objects);
                obj = request;
                logger.info("JsonSerializer反序列化成功...");
                break;
            case 1:

                RpcResponse response = JSON.parseObject(bytes, RpcResponse.class);
                Class<?> dataType = response.getDataType();
                if (!dataType.isAssignableFrom(response.getData().getClass())) {
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(), dataType));
                }
                obj = response;
                logger.info("JsonSerializer反序列化成功...");
                break;
            default:
                System.out.println("暂时不支持此种消息");
                throw new RuntimeException();
        }
        return obj;
    }

    // 1 代表着json序列化方式
    @Override
    public int getType() {
        return 1;
    }
}