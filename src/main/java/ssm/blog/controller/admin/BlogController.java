package ssm.blog.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssm.blog.entity.Blog;
import ssm.blog.entity.PageBean;
import ssm.blog.service.BlogService;
import ssm.blog.util.ResponseUtil;

/**
 * @Description 管理员博客Controller层
 * @author xp
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogController {

	@Resource
	private BlogService blogService;

	

	//后台分页查询博客信息
	@RequestMapping("/listBlog")
	public String listBlog(
			@RequestParam(value="page", required=false)String page,
			@RequestParam(value="rows", required=false)String rows,
			Blog s_blog,
			HttpServletResponse response) throws Exception {
		
		PageBean<Blog> pageBean = new PageBean<Blog>(Integer.parseInt(page), Integer.parseInt(rows));

		pageBean = blogService.listBlog(s_blog.getTitle(),pageBean);

		
		JSONObject result = new JSONObject();
		JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd";
		String jsonStr = JSONObject.toJSONString(pageBean.getResult(),
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		JSONArray array = JSON.parseArray(jsonStr);
		result.put("rows", array);
		result.put("total", pageBean.getTotal());
		ResponseUtil.write(response, result);
		return null;
	}
	

}
