<%--
  Created by IntelliJ IDEA.
  User: xp
  Date: 2017/4/20
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改人信息</title>
    <%@include file="./common/head.jspf"%>
    <script type="text/javascript" charset="utf-8"
            src="${blog}/static/ueditor1_4_3_3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${blog}/static/ueditor1_4_3_3/ueditor.all.min.js">
    </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8"
            src="${blog}/static/ueditor1_4_3_3/lang/zh-cn/zh-cn.js"></script>
    <script>
        function submitData() {
            $('#fm').form('submit',{
                url:'${blog}/admin/blogger/save.do',
                onSubmit:function() {
                    $('#nickname').attr('required',true);
                    $('#sign').attr('required',true);
                    var profile = UE.getEditor("profile").getContent();
                    $("#pf").val(profile); //将UEditor编辑器中的内容放到隐藏域中提交到后台
                    return $(this).form("validate");
                },//进行验证，通过才让提交
                success:function (result) {
                    // var result = eval("(" + result + ")"); //将json格式的result转换成js对象
                    var result = JSON.parse(result);
                    if(result.success) {
                        $.messager.alert("系统提示", "博主信息更新成功");
                    } else {
                        $.messager.alert("系统提示", "博主信息更新失败");
                        return;
                    }
                }
            });
        }
    </script>
</head>
<body style="margin: 10px; font-family: microsoft yahei">
    <div id="p" class="easyui-panel" title="修改人信息" style="padding: 10px">
        <form method="post" id="fm" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                    <td width="80px">用户名：</td>
                    <td>
                        <input type="hidden" id="id" name="id" value="${blogger.id }"/>
                        <input type="text" id="userName" name="userName" style="width:200px" readonly="readonly" value="${blogger.userName }"/>
                    </td>
                </tr>
                <tr>
                    <td>昵称：</td>
                    <td>
                        <input type="text" id="nickName" name="nickName" style="width:200px"
                              />
                    </td>
                </tr>
                <tr>
                    <td>个性签名：</td>
                    <td>
                        <input type="text" id="sign" name="sign" style="width:400px"
                                />
                    </td>
                </tr>
                <tr>
                    <td>个人头像：</td>
                    <td>
                        <input type="file" id="imageFile" name="imageFile"/>
                    </td>
                </tr>
                <tr>
                    <td>个人简介：</td>
                    <td>
                        <script id="profile" type="text/plain" style="width:95%; height:300px;"></script>
                        <input type="hidden" id="pf" name="profile"> <%-- UEditor不能作为表单的一部分提交，所以用这种隐藏域的方式 --%>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><a href="javascript:submitData()" class="easyui-linkbutton"
                           data-options="iconCls:'icon-submit'">提交</a></td>
                </tr>
            </table>
        </form>
    </div>
<script>
    var ue = UE.getEditor('profile');
    ue.addListener('ready',function () {
        UE.ajax.request('${blog}/admin/blogger/getBloggerInfo.do',{
            method: "post",
            async: false,
            data: {},
            onsuccess: function(result) {
                //result = eval("(" + result.responseText + ")");
                result = JSON.parse(result.responseText);
                $("#nickName").val(result.nickName);
                $("#sign").val(result.sign);
                UE.getEditor('profile').setContent(result.profile);
            }
        })
    })
</script>

</body>
</html>
