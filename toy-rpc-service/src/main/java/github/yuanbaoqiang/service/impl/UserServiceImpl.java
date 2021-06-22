package github.yuanbaoqiang.service.impl;

import github.yuanbaoqiang.dto.User;
import github.yuanbaoqiang.service.UserService;

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
}