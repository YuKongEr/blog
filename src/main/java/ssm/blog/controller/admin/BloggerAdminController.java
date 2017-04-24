package ssm.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ssm.blog.entity.Blogger;
import ssm.blog.service.BloggerService;
import ssm.blog.util.DateUtil;
import ssm.blog.util.MD5Util;
import ssm.blog.util.PathUtil;
import ssm.blog.util.ResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Author xp
 * @Description 博主信息管理控制层
 * @Date 2017/4/20 10:55
 */
@Controller
@RequestMapping(value = "/admin/blogger")
public class BloggerAdminController {

    @Resource
    private BloggerService bloggerService;

    //获取博主信息
    @RequestMapping(value = "getBloggerInfo")
    public String getBloggerData(HttpServletResponse response) throws Exception {
        Blogger blogger = bloggerService.getBloggerData();
        String jsonStr = JSONObject.toJSONString(blogger);
        JSONObject object = JSONObject.parseObject(jsonStr);
        ResponseUtil.write(response,object);
        return null;
    }

    //更新博主信息
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String saveBlogger(@RequestParam(value = "imageFile",required = false) MultipartFile imageFile, Blogger blogger,
                              HttpServletResponse response) throws Exception {
        //判断是否有上图片 有就更新
        if(!imageFile.isEmpty()){
            String filePath = PathUtil.getRootPath(); //获取服务器根路径
            String imageName = DateUtil.getCurrentDateStr() + "." + imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath + "/src/main/webapp/static/userImages/" + imageName));
            blogger.setImageName(imageName);
        }
        int resultTotal = bloggerService.updateBlogger(blogger);
        JSONObject result = new JSONObject();
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    //更新博主密码
    @RequestMapping(value = "modtifyPassword",method = RequestMethod.POST)
    public String modityBloggerPassword(Blogger blogger,HttpServletResponse response) throws Exception {
        //加密
        String newPassword = MD5Util.md5(blogger.getPassword(),"xp");
        blogger.setPassword(newPassword);
        int resultTotal = bloggerService.updateBlogger(blogger);
        JSONObject result = new JSONObject();
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }
}
