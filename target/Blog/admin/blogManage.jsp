<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>博客管理页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="./common/head.jspf"%>
<script type="text/javascript">
    /**
	 * 格式化博客标题 使其变成超链接
     * @param val
     * @param row
     * @returns {string}
     */
	function formatTitle(val, row) {
		return "<a target='_blank' href='${blog}/blog/articles/"+row.id+".html'>"+val+"</a>";
	}

    /**
	 * 格式化博客类型获取其类型名称
     * @param val
     * @param row
     * @returns {string}
     */
	function formatBlogType(val, row) {
		return val.typeName;
	}

    /**
	 * 按照title查询博客信息
     */
	function searchBlog() {
		$("#dg").datagrid("load", {
			"title":$("#s_title").val()
		});
	}

    /**
	 * 删除博客信息
	 * 可以多选
     */
	function deleteBlog() {
	    //获取选中要删除的行
		var selectedRows = $("#dg").datagrid("getSelections");
		//判断是否有选择的行
		if(selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据");
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
			//确定删除
		    if(r) {
		        //发送ajax请求
				$.post("${blog}/admin/blog/delete.do",
						{ids: ids}, function(result){
							if(result.success) {
								$.messager.alert("系统提示", "数据删除成功！");
								$("#dg").datagrid("reload");
							} else {
								$.messager.alert("系统提示", "数据删除失败！");
							}
						}, "json");
			}
		});
	}
	/**
	 *打开修改博客界面
	 */
	function openBlogModifyTab() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一个要修改的博客");
			return;
		}
		var row = selectedRows[0];
		window.parent.openTab("修改博客","modifyBlog.jsp?id=" + row.id, "icon-writeblog");
	}
    /**
	 * 重新载入数据
     */
	function reload() {
		$("#dg").datagrid("reload");
	}
</script>

</head>

<body style="margin: 1px; font-family: microsoft yahei">
<table id="dg" title="博客管理" class="easyui-datagrid" fitColumns="true" pagination="true"
	url="${blog}/admin/blog/listBlog.do" toolbar="#tb">
	<thead>
		<tr>
			<th field="cb" checkbox="true" align="center"></th>
			<th field="id" width="20" align="center">编号</th>
			<th field="title" width="200" formatter="formatTitle">标题</th>
			<th field="releaseDate" width="100" align="center">发布日期</th> 
			<th field="blogType" width="100" align="center" formatter="formatBlogType">博客类型</th>
		</tr>
	</thead>
</table>
<div id="tb">
	<div>
		&nbsp;标题&nbsp;<input type="text" id="s_title" size="20" onkeydown="if(event.keyCode==13) searchBlog()">
		<a href="javascript:searchBlog()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:deleteBlog()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openBlogModifyTab()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>		
		<a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>		
	</div>
</div>
</body>
</html>
