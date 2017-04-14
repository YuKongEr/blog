package ssm.blog.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ssm.blog.entity.Blogger;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by xp on 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class) //使用spring测试
@ContextConfiguration(locations = "classpath:spring-beans.xml") //设置spring配置文件路径
public class BloggerDaoTest {

    @Resource   //注入BloggerDao对象
    private BloggerDao bloggerDao;

    @Test
    public void getBloggerData() throws Exception {
        Blogger blogger = bloggerDao.getBloggerData();
        System.out.println(blogger);
    }

}