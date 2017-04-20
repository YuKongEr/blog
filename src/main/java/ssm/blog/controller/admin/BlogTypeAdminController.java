package ssm.blog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssm.blog.entity.BlogType;
import ssm.blog.entity.PageBean;
import ssm.blog.service.BlogService;
import ssm.blog.service.BlogTypeService;
import ssm.blog.util.ResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

/**
 * Created by xp on 2017/4/14.
 * @author xp
 * @Description 博客分类控制器
 */
@Controller
@RequestMapping(value = "/admin/blogType")
public class BlogTypeAdminController {

    @Resource
    private BlogTypeService blogTypeService;
    @Resource
    private BlogService blogService;

    // 分页查询博客类别
    @RequestMapping("/listBlogType")
    public String listBlogType(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "rows", required = false) String rows,
            HttpServletResponse response) throws Exception {
        //定义分页bean
        PageBean<BlogType> pageBean = new PageBean<BlogType>(Integer.parseInt(page)
                ,Integer.parseInt(rows));
        //拿到分页结果已经记录总数的pageBean
        pageBean = blogTypeService.listByPage(pageBean);
        //使用阿里巴巴的fastJson创建JSONObject
        JSONObject result = new JSONObject();
        //通过fastJson序列化list为jsonArray
        String jsonArray = JSON.toJSONString(pageBean.getResult());
        JSONArray array = JSONArray.parseArray(jsonArray);
        //将序列化结果放入json对象中
        result.put("rows", array);
        result.put("total", pageBean.getTotal());

        //使用自定义工具类向response中写入数据
        ResponseUtil.write(response, result);
        return null;
    }

    // 添加和更新博客类别
    @RequestMapping("/save")
    public String save(BlogType blogType, HttpServletResponse response)
            throws Exception {

        int resultTotal = 0; // 接收返回结果记录数
        if (blogType.getId() == null) { // 说明是第一次插入
            resultTotal = blogTypeService.addBlogType(blogType);
        } else { // 有id表示修改
            resultTotal = blogTypeService.updateBlogType(blogType);
        }

        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    // 博客类别信息删除
    @RequestMapping(value = "/delete",produces = { "application/json;charset=UTF-8" }  )
    public String deleteBlog(
            @RequestParam(value = "ids", required = false) String ids,
            HttpServletResponse response) throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            if(blogService.getBlogByTypeId(id) > 0) { //说明该类别中有博客
                result.put("exist", "true");
                break;
            } else {
                blogTypeService.deleteBlogType(id);
            }
        }
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }



}
