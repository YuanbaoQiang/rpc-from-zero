package github.yuanbaoqiang.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 测试消息
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-26 15:21
 */
@Data
public class HelloMessage implements Serializable {
    private static final long serialVersionUID = 66953838927736493L;
    private String content;
}