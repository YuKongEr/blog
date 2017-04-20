package ssm.blog.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xp on 2017/4/20.
 */
public class PathUtil {

    public static String getRootPath(HttpServletRequest request){
        String path=request.getServletContext().getRealPath("/");
        for (int i = 0; i < 3; i++) {
            int end = path.lastIndexOf("/");
            path = path.substring(0, end);
        }
        return path;
    }
}
