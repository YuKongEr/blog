package ssm.blog.service;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Blog;
import ssm.blog.entity.PageBean;

/**
 * @Description 博客Service接口
 * @author xp
 *
 */
public interface BlogService {


	// 分页查询博客
	public PageBean<Blog> listBlog(String title, PageBean<Blog> pageBean);


	// 根据博客类型的id查询该类型下的博客数量
	public Integer getBlogByTypeId(Integer typeId);
}
