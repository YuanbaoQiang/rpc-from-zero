package github.yuanbaoqiang.remoting.transport.endec;

import lombok.AllArgsConstructor;

/**
 * @description: 获取消息类型
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 10:25
 */
@AllArgsConstructor
public enum MessageType {
    REQUEST(0),RESPONSE(1);
    private int code;
    public int getCode(){
        return code;
    }
}