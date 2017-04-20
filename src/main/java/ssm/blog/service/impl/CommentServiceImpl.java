package ssm.blog.service.impl;

import org.springframework.stereotype.Service;
import ssm.blog.dao.CommentDao;
import ssm.blog.entity.Comment;
import ssm.blog.entity.PageBean;
import ssm.blog.service.CommentService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xp on 2017/4/20.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;

    @Override
    public PageBean<Comment> listByPage(PageBean<Comment> pageBean) {
        pageBean.getMap().put("start",pageBean.getStart());
        pageBean.getMap().put("end",pageBean.getEnd());
        pageBean.setResult(commentDao.listByPage(pageBean.getMap()));
        pageBean.setTotal(commentDao.getTotal(pageBean.getMap()));
        return pageBean;
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    @Override
    public Comment getById(Integer id) {
        return commentDao.getById(id);
    }

    @Override
    public Integer saveComment(Comment comment) {
        return commentDao.saveComment(comment);
    }

    @Override
    public Integer deleteComment(Integer id) {
        return commentDao.deleteComment(id);
    }

    @Override
    public Integer updateComment(Comment comment) {
        return commentDao.updateComment(comment);
    }

    @Override
    public Long deleteCommentByBlogId(Integer blogId) {
        return commentDao.deleteCommentByBlogId(blogId);
    }
}
