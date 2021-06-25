package github.yuanbaoqiang.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 用户类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 11:04
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 669337782347736493L;
    private int id;
    private String name;
    private Boolean sex;
}