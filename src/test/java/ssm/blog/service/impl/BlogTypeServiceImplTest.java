package ssm.blog.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ssm.blog.entity.BlogType;
import ssm.blog.entity.PageBean;
import ssm.blog.service.BlogTypeService;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by xp on 2017/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class BlogTypeServiceImplTest {

    @Resource
    private BlogTypeService blogTypeService;

    @Test
    public void listByPage() throws Exception {
        PageBean<BlogType> pageBean = new PageBean<BlogType>(1,2);
        pageBean = blogTypeService.listByPage(pageBean);
        System.out.println(pageBean);
    }

    @Test
    public void getTotal() throws Exception {

    }

    @Test
    public void addBlogType() throws Exception {

    }

    @Test
    public void updateBlogType() throws Exception {

    }

    @Test
    public void deleteBlogType() throws Exception {

    }

}