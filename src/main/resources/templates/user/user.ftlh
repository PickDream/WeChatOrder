<html>
<#include "../common/header.ftlh">
<style>
    .headImg{
        width:50px;height:50px;margin-right:10px;border-radius:50%
    }
</style>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <#include "../common/nav-top.ftlh">
    <#include "../common/nav-side.ftlh">
    <div class="layui-body" style="padding: 15px">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>用户信息显示</legend>
        </fieldset>
        <form class="layui-form" action="/sell/user/searchUser" method="get">
            <div class="layui-form-item">
                <label class="layui-form-label">搜用户</label>
                <div class="layui-input-inline">
                    <input type="text" name="username" required  lay-verify="required" placeholder="请输入用户昵称" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-input-inline">
                    <button class="layui-btn" lay-submit lay-filter="formDemo">搜索</button>
                </div>
            </div>
        </form>
        <div class="layui-form">
            <table class="layui-table">
                <thead class="thead-light">
                <tr>
                    <th>openId</th>
                    <th>头像</th>
                    <th>昵称</th>
                    <th>省市</th>
                    <th>城市</th>
                    <th>性别</th>
                    <th>使用语言</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list userInfos as user>
                    <tr>
                        <td>${user.openId}</td>
                        <td><img src="${user.imgUrl}" class="headImg layui-anim layui-anim-upbit"></td>
                        <td>${user.nickName}</td>
                        <td>${user.province}</td>
                        <td>${user.city}</td>
                        <#if user.sex==1>
                            <td>男</td>
                        <#else>
                            <td>女</td>
                        </#if>
                        <td>${user.language}</td>
                        <td><a href="/sell/user/delUser?openid=${user.openId}" class="layui-btn layui-btn-danger ">删除</a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<#include "../common/script.ftlh">
</html>