package github.yuanbaoqiang.common.service;

import github.yuanbaoqiang.common.dto.Blog;

/**
 * @description: 新的博客服务接口
 * @author: YuanbaoQiang
 * @create: 2021-06-23 21:16
 */
public interface BlogService {
    Blog getBlogById(Integer id);
}
