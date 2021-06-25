package github.yuanbaoqiang.remoting.service.impl;

import github.yuanbaoqiang.remoting.dto.User;
import github.yuanbaoqiang.remoting.service.UserService;

import java.util.Random;
import java.util.UUID;

/**
 * @description: 用户服务实现类
 * @author: YuanbaoQiang-ctrip
 * @create: 2021-06-22 11:10
 */
public class UserServiceImpl implements UserService {
    @Override
    public User queryUserById(int id) {
        Random random = new Random();
        User user = User.builder()
                .id(id)
                .name(UUID.randomUUID().toString())
                .sex(random.nextBoolean())
                .build();
        return user;
    }

    @Override
    public Integer inserUser(User user) {
        return 1;
    }
}