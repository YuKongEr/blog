package ssm.blog.service.impl;

import org.springframework.stereotype.Service;
import ssm.blog.dao.BloggerDao;
import ssm.blog.entity.Blogger;
import ssm.blog.service.BloggerService;

import javax.annotation.Resource;

/**
 * Created by xp on 2017/4/13.
 */
@Service
public class BloggerServiceImpl implements BloggerService{

    @Resource
    private BloggerDao bloggerDao;

    @Override
    public Blogger getBloggerData() {
        return bloggerDao.getBloggerData();
    }

    @Override
    public Blogger getBloggerByName(String username) {
        return bloggerDao.getBloggerByName(username);
    }

    @Override
    public Integer updateBlogger(Blogger blogger) {
        return bloggerDao.updateBlogger(blogger);
    }
}
