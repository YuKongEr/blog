<%--
  Created by IntelliJ IDEA.
  User: xp
  Date: 2017/4/21
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/ueditor1_4_3_3/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet"
      href="fw">
<script>
    SyntaxHighlighter.all();//代码高亮
    /**
     * 显示其他评论
     */
    function showOtherComment() {
        $(".otherComment").show();
    }
</script>
<!-- 博客信息显示 -->
<div class="data_list">
    <!-- 博客列表信息显示 -->
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/blog_show_icon.png" alt="博客信息">&nbsp;博客信息
    </div>
    <!-- 博客主体 -->
    <div>
        <!--博客标题 -->
        <div class="blog_title">
            <h3>
                <strong>${blog.title}</strong>
            </h3>
        </div>
        <!-- 博客信息 -->
        <div class="blog_info">
            <!-- 左侧标题信息 -->
            <div style="float:left">
                <strong>标签</strong>
                <c:choose>
                    <!--判断是否有标签-->
                    <c:when test="${keyWords==null}">
                        &nbsp;&nbsp;无
                    </c:when>
                    <c:otherwise>
                        <!-- 循环遍历标签 --》
                        <c:forEach items="${keyWorlds}" var="keyWorld">
                            <!-- 标签显示为超链接 点击查询跟类型的博客 -->
                            &nbsp;&nbsp;<a href="${pageContext.request.contextPath}/blog/search.html?q=${keyWorld}">${keyWorld}</a>&nbsp;
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
            <!-- 右侧发表时间阅读量等信息 -->
            <div style="float:right">
                发布于：『
                <fmt:formatDate value="${blog.releaseDate}" type="date" pattern="yyyy-MM-dd HH:mm"/>
                』&nbsp;&nbsp; 博客类别：
                <a href="${pageContext.request.contextPath}/index.html?typeId=${blog.blodType.id}">${blog.blodType.name}</a>
                阅读(${blog.clickHit})&nbsp;&nbsp;;评论(${blog.replyHit})
            </div>
        </div>
    </div>
    <br><br>
    <!-- 水平分割线 -->
    <div class="xian" style="clear: both;margin: 0 auto; border-top:1px solid #ddd"></div>
    <!-- 博客作者以及转载声明 -->
    <div style="line-height: 3;background-color:#F8F8FF;">
        <span style="color: #8b2323">作者：熊平&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;尊重博主原创文章，转载请注明文章出于此处。</span>
    </div>
    <!-- 水平分割线 -->
    <div class="xian" style="margin: 0 auto; border-top:1px solid #ddd"></div>
    <!-- 博客主体-->
    <div class="blog_content">
        ${blog.content}
    </div>
    <!-- 水平分割线 -->
    <div class="xian" style="margin: 0 auto; border-top:1px solid #ddd"></div>
    <!-- 分页显示条 -->
    <div style="margin-top: 25px;">${pageCode }</div>
</div>
<!-- 评论列表 -->
<div class="data_list">
    <!-- 评论列表头-->
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/comment_icon.png" alt="用户评论">&nbsp;用户评论
        <!-- 判断评论数量是否超过十条 超过就只显示十条 -->
        <c:if test="${commentList.size()>10 }">
            <a href="javascript:showd()"
               style="float:right; padding-right:40px;">显示所有用户评论</a>
        </c:if>
    </div>
    <!--显示数据 -->
    <div class="commentData">
        <ul>
            <c:choose>
                <!--判断是否为空-->
                <c:when test="${commentList.size()==0}">
                    暂无评论
                </c:when>
                <c:otherwise>
                    <c:forEach items="${commentList}" var="comment" varStatus="">
                        <c:choose>
                            <!--前十条放在一起 显示-->
                            <c:when test="${status.index<10 }">
                                        <span>
											${status.index+1}楼&nbsp;&nbsp;&nbsp;&nbsp;${comment.userIp }
										&nbsp;&nbsp;&nbsp;&nbsp;${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;
										[
                                            <fmt:formatDate value="${comment.commentDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>
                                        ]
                                    </span>
                            </c:when>
                            <!--非前十条放在一起 点击显示才显示-->
                            <c:otherwise>
                                    <span>
											${status.index+1}楼&nbsp;&nbsp;&nbsp;&nbsp;${comment.userIp }
										&nbsp;&nbsp;&nbsp;&nbsp;${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;
										[
                                            <fmt:formatDate value="${comment.commentDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>
                                        ]
                                    </span>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<!-- 添加评论-->
<div class="data_list">
    <div class="data_list_title">
        <img
                src="${pageContext.request.contextPath}/static/images/publish_comment_icon.png" />&nbsp;发表评论
    </div>
    <div class="publish_comment">
        <div>
			<textarea style="width: 100%" rows="3" id="content" name="content"
                      placeholder="来说两句吧..."></textarea>
        </div>
        <div class="verCode">
            验证码：<input type="text" value="" name="imageCode" id="imageCode"
                       size="10" onkeydown="if(event.keyCode==13)form1.submit()" />&nbsp;
            <img onclick="javascript:loadimage();" title="换一张试试" name="randImage"
                 id="randImage" src="/image.jsp" width="60" height="20" border="1"
                 align="absmiddle">
        </div>
        <div class="publishButton">
            <button class="btn btn-primary" type="button" onclick="submitData()">发表评论</button>
        </div>
        </form>
    </div>

</div>