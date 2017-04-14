package ssm.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ssm.blog.entity.BlogType;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by xp on 2017/4/14.
 * @author xp
 * @Description 博客类别dao测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class BlogTypeDaoTest {

    @Resource
    private BlogTypeDao blogTypeDao;


    @Test
    public void addBlogType() throws Exception {
        BlogType blogType = new BlogType("Mysql",10);
        int result = blogTypeDao.addBlogType(blogType);
        System.out.println(result);
    }

    @Test
    public void deleteBlogType() throws Exception {
        int result = blogTypeDao.deleteBlogType(19);
        System.out.println(result);
    }

    @Test
    public void updateBlogType() throws Exception {
        // 先查询出要更新的记录然后修改
        BlogType blogType = blogTypeDao.getById(19);
        //然后提交更新
        blogType.setTypeName("更新mysql");
        //更新
        blogTypeDao.updateBlogType(blogType);
        //查询更新后的值 并且打印
        System.out.println(blogTypeDao.getById(19));

    }

    @Test
    public void getById() throws Exception {
        BlogType blogType = blogTypeDao.getById(19);
        System.out.println(blogType);
    }

    @Test
    public void  listByPage(){
        Integer page = 1;
        Integer pageSize = 2;
        Integer start =(page-1)*pageSize;
        Integer end = page*pageSize;
        List<BlogType> blogTypeList = blogTypeDao.listByPage(start,end);
        for (BlogType b: blogTypeList) {
            System.out.println(b);
        }
    }

    @Test
    public void getTotal(){
        long total = blogTypeDao.getTotal();
        System.out.println(total);
    }

}