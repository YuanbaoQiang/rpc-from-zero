package github.yuanbaoqiang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 66234327736493L;
    private String content;
}
