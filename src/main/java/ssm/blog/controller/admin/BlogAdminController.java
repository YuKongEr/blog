package ssm.blog.controller.admin;


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
import ssm.blog.lucene.BlogIndex;
import ssm.blog.service.BlogService;
import ssm.blog.service.CommentService;
import ssm.blog.util.ResponseUtil;

/**
 * @author xp
 * @Description 管理员博客Controller层
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;
    @Resource
    private BlogIndex blogIndex;
    //后台分页查询博客信息
    @RequestMapping("/listBlog")
    public String listBlog(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "rows", required = false) String rows,
            Blog s_blog,
            HttpServletResponse response) throws Exception {

        PageBean<Blog> pageBean = new PageBean<Blog>(Integer.parseInt(page), Integer.parseInt(rows));

        pageBean = blogService.listBlog(s_blog.getTitle(), pageBean);

        //创建json对象
        JSONObject result = new JSONObject();
        //设置json序列化日期格式
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        //禁止对象循环引用
        //使用默认日期格式化
        String jsonStr = JSONObject.toJSONString(pageBean.getResult(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteDateUseDateFormat);
        //得到json数组
        JSONArray array = JSON.parseArray(jsonStr);
        //把结果放入json
        result.put("rows", array);
        result.put("total", pageBean.getTotal());
        //返回
        ResponseUtil.write(response, result);
        return null;
    }

    //更新或者新增博客
    @RequestMapping(value = "/save")
    public String saveBlog(Blog blog,HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        if(blog.getId()!=null){
            //更新操作
            resultTotal = blogService.updateBlog(blog);
            //更新索引
            blogIndex.updateIndex(blog);
        }else{
            //新增操作
            resultTotal = blogService.saveBlog(blog);
            //添加索引
            blogIndex.addIndex(blog);
        }
        JSONObject result = new JSONObject();
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    //删除博客
    @RequestMapping(value = "delete")
    public String deleteBlog(@RequestParam("ids")String ids,HttpServletResponse response) throws Exception {
        String[] idsStr = ids.split(",");
        for(int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            //先删除博客所关联的评论
            commentService.deleteCommentByBlogId(id);
            blogService.deleteBlog(id);
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    //通过id获取博客
    @RequestMapping(value = "get")
    public String getById(@RequestParam("id") String id,HttpServletResponse response) throws Exception {

        Blog blog = blogService.getById(Integer.parseInt(id));
        String jsonStr = JSONObject.toJSONString(blog);
        JSONObject result = JSONObject.parseObject(jsonStr);
        ResponseUtil.write(response, result);
        return null;
    }

}
