<%--
  Created by IntelliJ IDEA.
  User: xp
  Date: 2017/4/14
  Time: 08:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <title>友情链接管理</title>
    <%@include file="./common/head.jspf" %>
</head>
<script type="text/javascript">

    //定义全局url 用于修改与添加操作
    var url;

    /**
     * 打开添加友情链接对话框
     */
    function openLinkAddDialog() {
        //打开对话框并且设置标题
        $("#dlg").dialog("open").dialog("setTitle", "添加友情链接");
        //将url设置为添加
        url = "${blog}/admin/link/save.do";
    }
    /**
     * 打开修改友情链接对话框
     */
    function openLinkModifyDialog() {
        //获取选中要修改的行
        var selectedRows = $("#dg").datagrid("getSelections");
        //确保被选中行只能为一行
        if(selectedRows.length != 1) {
            $.messager.alert("系统提示", "请选择一个要修改的友情链接");
            return;
        }
        //获取选中行id
        var row = selectedRows[0];
        //打开对话框并且设置标题
        $("#dlg").dialog("open").dialog("setTitle", "修改友情链接");
        //将数组回显对话框中
        $("#fm").form("load", row);//会自动识别name属性，将row中对应的数据，填充到form表单对应的name属性中
        //在url中添加id 后台就能识别是更新操作
        url = "${blog}/admin/link/save.do?id=" + row.id;
    }

    function saveLink() {
        $("#fm").form("submit",{
            url: url,
            onSubmit: function() {
                $('#linkName').attr('required',true);
                $('#linkUrl').attr('required',true);
                $('#orderNum').attr('required',true);
                return $(this).form("validate");
            }, //进行验证，通过才让提交
            success: function(result) {
                var result = eval("(" + result + ")"); //将json格式的result转换成js对象
                if(result.success) {
                    $.messager.alert("系统提示", "友情链接保存成功");
                    $("linkUrl").val(""); //保存成功后将内容置空
                    $("linkName").val("");
                    $('#orderNum').val("");
                    $("#dlg").dialog("close"); //关闭对话框
                    $("#dg").datagrid("reload"); //刷新一下
                } else {
                    $.messager.alert("系统提示", "友情链接保存失败");
                    return;
                }
            }
        });
    }

    function closeLinkDialog() {
        $("linkName").val(""); //保存成功后将内容置空
        $("linkUrl").val("");
        $("#dlg").dialog("close"); //关闭对话框
    }


    function deleteLink() {
        //获取选中要删除的行
        var selectedRows = $("#dg").datagrid("getSelections");
        //判断是否有选择的行
        if(selectedRows.length == 0) {
            $.messager.alert("系统提示", "请选择要删除的友情链接");
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
        //提示是否确认删除
        $.messager.confirm("系统提示", "<font color=red>您确定要删除选中的"+selectedRows.length+"条数据么？</font>", function(r) {
            if(r) {
                $.post("${blog}/admin/link/delete.do",
                    {ids: ids}, function(result){
                         if(result.success) {
                            $.messager.alert("系统提示", "友情链接删除成功！");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "友情链接删除失败！");
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
<table id="dg" title="友情链接管理" class="easyui-datagrid" fitColumns="true" pagination="true"
       url="${blog}/admin/link/list.do" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="20" align="center">编号</th>
        <th field="linkName" width="100" align="center">友情链接名称</th>
        <th field="linkUrl" width="100" align="center">友情链接地址</th>
        <th field="orderNum" width="100" align="center">友情链接序号</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openLinkAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:deleteLink()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        <a href="javascript:openLinkModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width:500px; height:180px; padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>友情链接名称</td>
                <td>
                    <input type="text" id="linkName" name="linkName" class="easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>友情链接地址</td>
                <td>
                    <input type="text" id="linkUrl" name="linkUrl" class="easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>友情链接排序</td>
                <td>
                    <input type="text" id="orderNum" name="orderNum" class="easyui-numberbox" required="true"
                           style="width:60px">&nbsp;(友情链接会根据序号从小到大排列)
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <div>
        <a href="javascript:saveLink()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
        <a href="javascript:closeLinkDialog()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
    </div>
</div>
</body>
</html>