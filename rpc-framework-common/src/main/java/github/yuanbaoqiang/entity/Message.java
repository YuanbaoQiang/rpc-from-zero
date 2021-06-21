package github.yuanbaoqiang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: yuanbaoqiang-toy-rpc
 * @description: socket传输实体类
 * @author: YuanbaoQiang
 * @create: 2021-06-20 22:51
 */
@Data
@AllArgsConstructor
public class Message {
    private static final long serialVersionUID = 66234327736493L;
    private String content;
}
