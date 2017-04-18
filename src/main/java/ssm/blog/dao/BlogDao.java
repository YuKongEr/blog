package ssm.blog.dao;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Blog;

/**
 * @Description 博客Dao接口
 * @author xp
 *
 */
public interface BlogDao {


	// 分页查询博客
	public List<Blog> listBlog(Map<String, Object> map);

	// 获取总记录数
	public Long getTotal(String title);

	// 根据博客类型的id查询该类型下的博客数量
	public Integer getBlogByTypeId(Integer typeId);
}
