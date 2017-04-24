package ssm.blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ssm.blog.dao.BlogDao;
import ssm.blog.entity.Blog;
import ssm.blog.entity.PageBean;
import ssm.blog.service.BlogService;

/**
 * @Description 博客Service实现类
 * @author xp
 *
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogDao blogDao;



	public PageBean<Blog> listBlog(String title, PageBean<Blog> pageBean) {
		Map<String,Object> map = new HashMap<String,Object>();
		//设置查询条件
		map.put("title",title);
		//总记录放入pageBean
		pageBean.setTotal(blogDao.getTotal(map));
		map.put("start",pageBean.getStart());
		map.put("end",pageBean.getEnd());
		//把分页结果放入pageBean
		pageBean.setResult(blogDao.listBlog(map));
		return pageBean;
	}


	public List<Blog> listBlog(Map<String,Object> map) {
		return blogDao.listBlog(map);
	}

	public Integer getBlogByTypeId(Integer typeId) {
		return blogDao.getBlogByTypeId(typeId);
	}

	public Integer saveBlog(Blog blog) {
		return blogDao.saveBlog(blog);
	}

	public Integer updateBlog(Blog blog) {
		return blogDao.updateBlog(blog);
	}

	public Integer deleteBlog(Integer id) {
		return blogDao.deleteBlog(id);
	}

	public Blog getById(Integer id) {
		return blogDao.getById(id);
	}

	public long getTotal(Map<String, Object> map) {
		return blogDao.getTotal(map);
	}

	public Blog getPrevBlog(Integer id) {
		return blogDao.getPrevBlog(id);
	}


	public Blog getNextBlog(Integer id) {
		return blogDao.getNextBlog(id);
	}

}
