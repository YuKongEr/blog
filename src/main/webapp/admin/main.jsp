<%--
  Created by IntelliJ IDEA.
  User: xp
  Date: 2017/4/14
  Time: 08:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人博客后台管理</title>
    <%@include file="./common/head.jspf" %>
    <style type="text/css">
        body {
            font-family: microsoft yahei;
        }
    </style>
    <script>
        /**
         * 打开新选项卡
         * @param text 标题
         * @param url   路径
         * @param icon  图标
         */
        function openTab(text,url,icon) {
            //判断当前选项卡是否存在
            if($('#tabs').tabs('exists',text)){
                //如果存在 显示
                $("#tabs").tabs("select",text);
            }else{
                //如果不存在 则新建一个
                $("#tabs").tabs('add',{
                    title:text,
                    closable:true,      //是否允许选项卡摺叠。
                    iconCls:icon,    //显示图标
                    content:"<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${blog}/admin/"+url+"'></iframe>"
                    //url 远程加载所打开的url
                })
            }
        }
        function openPasswordModifyDialog() {
            $("#dlg").dialog("open").dialog("setTitle","修改密码");
        }
        /**
         * 提交修改密码
         */
        function submitData() {
            $("#fm").form('submit',{
                url:'${blog}/admin/blogger/modtifyPassword.do',
                onsubmit:function () {
                    var newPassword = $("#password").val();
                    var newPassword2 = $("#password2").val();
                    if(!$(this).form("validate")) {
                        return false; //验证不通过直接false，即没填
                    }
                    if(newPassword != newPassword2) {
                        $.messager.alert("系统提示", "两次密码必须填写一致");
                        return false
                    }
                    return true;
                },//进行验证，通过才让提交
                success: function(result) {
                    //var result = eval("(" + result + ")"); //将json格式的result转换成js对象
                    var result = JSON.parse(result);
                    if(result.success) {
                        $.messager.alert("系统提示", "密码修改成功，下一次登陆生效");
                        closePasswordModifyDialog();
                    } else {
                        $.messager.alert("系统提示", "密码修改失败");
                        return;
                    }
                }
            });
        }
        /**
         * 关闭对话框
         */
        function closePasswordModifyDialog() {
            $("#password").val(""); //保存成功后将内容置空
            $("#password2").val("");
            $("#dlg").dialog("close"); //关闭对话框
        }
    </script>
</head>
<body class="easyui-layout">

<div region="north" style="height: 78px; background-color: #E0ECFF">
    <table style="padding: 5px" width="100%">
        <tr>
            <td width="50%">
                <h2>博客后台系统</h2>
            </td>
            <td valign="bottom" align="right" width="50%">
                <font size="3" color="#1e90ff">&nbsp;&nbsp;<strong>欢迎：</strong>${currentUser.userName }</font>
            </td>
        </tr>
    </table>
</div>
<div region="west" style="width: 200px" title="导航菜单" split="true">
    <div class="easyui-accordion" data-options="fit:true,border:false">
        <div title="常用操作" data-options="selected:true,iconCls:'icon-item'" style="padding: 10px">
            <a href="javascript:openTab('写博客','writeBlog.jsp','icon-writeblog')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">写博客</a>
            <a href="javascript:openTab('评论审核','commentReview.jsp','icon-review')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
        </div>
        <div title="博客管理" data-options="iconCls:'icon-bkgl'" style="padding:10px;">
            <a href="javascript:openTab('写博客','writeBlog.jsp','icon-writeblog')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px;">写博客</a>
            <a href="javascript:openTab('博客信息管理','blogManage.jsp','icon-bkgl')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-bkgl'" style="width: 150px;">博客信息管理</a>
        </div>
        <div title="博客类别管理" data-options="iconCls:'icon-bklb'" style="padding:10px">
            <a href="javascript:openTab('博客类别信息管理','blogTypeManage.jsp','icon-bklb')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-bklb'" style="width: 150px;">博客类别信息管理</a>
        </div>
        <div title="评论管理" data-options="iconCls:'icon-plgl'" style="padding:10px">
            <a href="javascript:openTab('评论审核','commentReview.jsp','icon-review')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
            <a href="javascript:openTab('评论信息管理','commentManage.jsp','icon-plgl')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-plgl'" style="width: 150px;">评论信息管理</a>
        </div>
        <div title="个人信息管理" data-options="iconCls:'icon-grxx'" style="padding:10px">
            <a href="javascript:openTab('修改个人信息','modifyInfo.jsp','icon-grxxxg')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-grxxxg'" style="width: 150px;">修改个人信息</a>
        </div>
        <div title="系统管理" data-options="iconCls:'icon-system'" style="padding:10px">
            <a href="javascript:openTab('友情链接管理','linkManage.jsp','icon-link')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-link'" style="width: 150px">友情链接管理</a>
            <a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
            <a href="javascript:refreshSystemCache()" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-refresh'" style="width: 150px;">刷新系统缓存</a>
            <a href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'"
               style="width: 150px;">安全退出</a>
        </div>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px; height:200px; padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名</td>
                <td>
                    <input type="text" id="username" name="username" value="${blogger.userName }" readonly="readonly">
                </td>
            </tr>
            <tr>
                <td>新密码</td>
                <td>
                    <input type="password" id="password" name="password" class="easyui-validatebox"
                           required="true" style="width:200px">
                </td>
            </tr>
            <tr>
                <td>确认新密码</td>
                <td>
                    <input type="password" id="password2" name="password2" class="easyui-validatebox"
                           required="true" style="width:200px">
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <div>
        <a href="javascript:submitData()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
        <a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div data-options="region:'center'" style="background:#eee;">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" data-options="iconCls:'icon-home'">
            <div align="center" style="padding-top: 100px"><font color="red" size="10">欢迎使用</font></div>
        </div>
    </div>
</div>
<div region="south" style="height: 25px;padding: 5px" align="center">
    Copyright © 2012-2017 熊平的SSM博客系统 版权所有
</div>
</body>
</html>

