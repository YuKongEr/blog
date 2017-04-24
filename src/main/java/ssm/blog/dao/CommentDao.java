package ssm.blog.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ssm.blog.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * @Author xp
 * @Description 评论dao类
 * @Date 2017/4/20 07:40
 */
@Repository
public interface CommentDao {

    /**
     * 分页查询评论信息
     * @param map
     * @return
     */
    public List<Comment> listByPage(Map<String,Object> map);

    /**
     * 获取总记录数目
     * @param map
     * @return
     */
    public Long getTotal(Map<String ,Object> map);

    /**
     * 根据id查询评论信息
     * @param id
     * @return
     */
    public Comment getById(Integer id);

    /**
     * 添加评论信息
     * @param comment
     * @return
     */
    public Integer saveComment(Comment comment);

    /**
     * 根据id删除评论
     * @param id
     * @return
     */
    public Integer deleteComment(Integer id);

    /**
     * 更改评论审核状态
     * @param comment
     * @return
     */
    public Integer updateComment(Comment comment);

    /**
     * 根据博客id查询评论信息
     * @param blogId
     * @return
     */
    public List<Comment> queryByBlogId(Integer blogId);

    /**
     * 删除评论信息通过博客id
     * @param blogId
     * @return
     */
    public Long deleteCommentByBlogId(Integer blogId);


}
