package ssm.blog.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssm.blog.entity.Blog;
import ssm.blog.entity.Comment;
import ssm.blog.service.BlogService;
import ssm.blog.service.CommentService;
import ssm.blog.util.ResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author xp
 * @Description 前台评论控制器
 * @Date 2017/4/24 14:19
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Resource
    private CommentService commentService;
    @Resource
    private BlogService blogService;

    //评论更新或者添加
    @RequestMapping(value = "save")
    public  String save(
            Comment comment,
            @RequestParam("imageCode")String imageCode, //前台传来的验证码
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        String sRand = (String) session.getAttribute("sRand");//获取session中正确的验证码，验证码产生后会存到session中的
        JSONObject result = new JSONObject();
        int resultTotal = 0; //执行记录数
        if(!imageCode.equals(sRand)){
            result.put("success",false);
            result.put("errorInfo","验证码有误");
        }else{
            //获取评论者ip
            String userIp = request.getRemoteAddr();
            comment.setUserIp(userIp);
            if(comment.getId() == null){
                resultTotal = commentService.saveComment(comment); //添加评论
                Blog blog = blogService.getById(comment.getBlog().getId()); //更新一下博客的评论次数
                blog.setReplyHit(blog.getReplyHit() + 1);
                blogService.updateBlog(blog);
            }else{
                //更新操作
            }
        }
        if(resultTotal > 0) {
            result.put("success", true);
        }
        ResponseUtil.write(response, result);
        return null;
    }

}
