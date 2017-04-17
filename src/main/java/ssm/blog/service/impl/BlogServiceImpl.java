package ssm.blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.oracle.javafx.jmx.SGMXBean;
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
		map.put("title",title);
		map.put("start",pageBean.getStart());
		map.put("end",pageBean.getEnd());
		pageBean.setResult(blogDao.listBlog(map));
		pageBean.setTotal(blogDao.getTotal(title));
		return pageBean;
	}


	public Integer getBlogByTypeId(Integer typeId) {
		return blogDao.getBlogByTypeId(typeId);
	}

}
