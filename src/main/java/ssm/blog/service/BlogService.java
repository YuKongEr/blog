package ssm.blog.service;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Blog;
import ssm.blog.entity.PageBean;

/**
 * @author xp
 * @Description 博客Service接口
 */
public interface BlogService {


    // 分页查询博客
    public List<Blog> listBlog(Map<String,Object> map);

    // 分页查询博客
    public PageBean<Blog> listBlog(String title,PageBean<Blog> pageBean);

    // 根据博客类型的id查询该类型下的博客数量
    public Integer getBlogByTypeId(Integer typeId);

    //添加博客
    public Integer saveBlog(Blog blog);

    //更新博客
    public Integer updateBlog(Blog blog);

    //通过id删除博客
    public Integer deleteBlog(Integer id);

    //通过id获取博客
    public Blog getById(Integer id);

    long getTotal(Map<String, Object> map);

    /**
     * 获取上一篇博客
     * @param id
     * @return
     */
    Blog getPrevBlog(Integer id);
    /**
     * 获取下一篇博客
     * @param id
     * @return
     */
    Blog getNextBlog(Integer id);
}
