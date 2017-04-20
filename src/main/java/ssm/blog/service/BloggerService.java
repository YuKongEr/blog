package ssm.blog.service;

import org.springframework.stereotype.Service;
import ssm.blog.entity.Blogger;

/**
 * Created by xp on 2017/4/13.
 */

public interface BloggerService {
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
