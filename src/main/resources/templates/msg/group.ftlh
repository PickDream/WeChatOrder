<html>
<#include "../common/header.ftlh">
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <#include "../common/nav-top.ftlh">
    <#include "../common/nav-side.ftlh">
    <div class="layui-body" style="padding: 15px">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>折扣信息群发</legend>
        </fieldset>
        <form class="layui-form" action="/sell/push/groupsend" method="post">
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">概述</label>
                <div class="layui-input-block">
                    <textarea name="desc" placeholder="请输入折扣内容" class="layui-textarea"></textarea>
                </div>
            </div>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend>选择人员</legend>
            </fieldset>
            <#list users as user >
            <input type="checkbox" name="openid" title="${user.nickName}"  value="${user.openId}" style="margin: 10px" checked>
            </#list>
            <hr class="layui-bg-green">

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<#include "../common/script.ftlh">
<script>

</script>
</html>