package github.yuanbaoqiang.service;

import github.yuanbaoqiang.dto.User;

/**
 * @description: 用户类服务接口
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 11:10
 */
public interface UserService {
    User queryUserById(int id);

    // 添加一个插入数据的方法
    Integer inserUser(User user);
}
