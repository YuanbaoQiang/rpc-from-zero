package github.yuanbaoqiang.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 博客类
 * @author: YuanbaoQiang
 * @create: 2021-06-23 21:15
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blog implements Serializable {
    private static final long serialVersionUID = 66533736493L;
    private Integer id;
    private Integer userId;
    private String title;
}
