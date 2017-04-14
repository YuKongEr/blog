package ssm.blog.dao;

import org.springframework.stereotype.Repository;
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

}
