package ssm.blog.controller;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xp on 2017/4/13.
 * 博主控制器
 */
@Controller  //注册为控制器bean
@RequestMapping(value = "/blog")    //请求路径
public class BloggerController {

    @ResponseBody
    @RequestMapping(value="/hello")
    public String hello(){
        return "hello";
    }

}
