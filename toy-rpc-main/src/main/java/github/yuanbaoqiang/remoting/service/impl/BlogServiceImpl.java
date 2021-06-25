package github.yuanbaoqiang.remoting.service.impl;

import github.yuanbaoqiang.remoting.dto.Blog;
import github.yuanbaoqiang.remoting.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @description: 新的服务接口的实现类
 * @author: YuanbaoQiang
 * @create: 2021-06-23 21:18
 */
public class BlogServiceImpl implements BlogService {
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").userId(20).build();
        logger.info("BlogService查询到了id为{}的博客信息", id);
        return blog;
    }
}
