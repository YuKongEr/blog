package ssm.blog.dao;

import org.springframework.stereotype.Repository;
import ssm.blog.entity.Blog;
import ssm.blog.entity.Blogger;

/**
 * Created by xp on 2017/4/13.
 * 博主dao接口
 */
@Repository
public interface BloggerDao {

    /**
     * 查询博主信息
     * @return
     */
    Blogger getBloggerData();

    /**
     * 通过用户名查询博主信息
     * @param username
     * @return
     */
    Blogger getBloggerByName(String username);

    /**
     * 更新博主信息
     * @param blogger
     * @return
     */
    Integer updateBlogger(Blogger blogger);

}
