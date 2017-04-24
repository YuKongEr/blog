<%--
  Created by IntelliJ IDEA.
  User: xp
  Date: 2017/4/20
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评论审核</title>
    <%@include file="./common/head.jspf"%>
    <script>

        function formatBlogTile(val,row) {
            if(val.id == null){
                return "<font color=red>该博客已删除</font>";
            }else{
                return "<a href='${blog}/blog/articles/"+val.id+".html' target='_blank'>"+val.title+"</a>";
            }
        }
        /**
         * 审核评论
         * @param state 审核状态
         */
        function commentReview(state) {
            //获取选中要审核的行
            var selectedRows = $("#dg").datagrid("getSelections");
            //判断是否有选择的行
            if(selectedRows.length == 0) {
                $.messager.alert("系统提示", "请选择要审核的评论");
                return;
            }
            //定义选中 选中id数组
            var idsStr = [];
            //循环遍历将选中行的id push进入数组
            for(var i = 0; i < selectedRows.length; i++) {
                idsStr.push(selectedRows[i].id);
            }
            //将数组安装,连接成字符串
            var ids = idsStr.join(","); //1,2,3,4
            //提示是否确认审核
            $.messager.confirm("系统提示", "<font color=red>您确定要审核选中的"+selectedRows.length+"条数据么？</font>", function(r) {
                if(r) {
                    $.post("${blog}/admin/comment/review.do",
                        {ids: ids,state:state}, function(result){
                           if(result.success) {
                                $.messager.alert("系统提示", "评论审核成功！");
                                $("#dg").datagrid("reload");
                            } else {
                                $.messager.alert("系统提示", "评论审核失败！");
                            }
                        }, "json");
                }
            });

        }

        function reload() {
            $("#dg").datagrid("reload");
        }
    </script>
</head>
<body style="margin: 1px; font-family: microsoft yahei">
<table id="dg" title="评论信息管理" class="easyui-datagrid" fitColumns="true" pagination="true"
       url="${blog}/admin/comment/list.do?state=0" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" align="center" width="20px">编号</th>
        <th field="userIp" align="center" width=100px">评论者ip</th>
        <th field="blog" align="center" width="150px" formatter="formatBlogTile">博客标题</th>
        <th field="content" align="center" width="200px">评论内容</th>
        <th field="commentDate" align="center" width="50px">评论日期</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:commentReview(1)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">审核通过</a>
        <a href="javascript:commentReview(2)" class="easyui-linkbutton" iconCls="icon-no" plain="true">审核不通过</a>
        <a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
    </div>
</div>
</body>
</html>
