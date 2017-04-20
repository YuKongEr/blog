package ssm.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xp on 2017/4/14.
 * 跳转后台管理主页面测试类
 */
@Controller
@RequestMapping(value = "/admin")
public class MainAdminController {

    @RequestMapping(value = "/menu")
    public String toMenuPage(){
        return "admin/main";
    }

    @RequestMapping(value = "/menu1")
    public String toMenuPage1(){
        return "admin/main1";
    }
}
