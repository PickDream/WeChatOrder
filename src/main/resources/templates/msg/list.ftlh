<html>
<#include "../common/header.ftlh">
<body class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
        <#include "../common/nav-top.ftlh">
        <#include "../common/nav-side.ftlh">
        <div class="layui-body" style="padding: 15px">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend>微信消息发送</legend>
            </fieldset>
            <div class="layui-collapse">
                <#list users as user>
                <div class="layui-colla-item">
                    <h2 class="layui-colla-title">${user.nickName}</h2>
                    <div class="layui-colla-content">
                        <table class="layui-table">
                            <thead class="thead-light">
                            <tr>
                                <th>头像</th>
                                <th>昵称</th>
                                <th>省市</th>
                                <th>城市</th>
                                <th>性别</th>
                                <th>使用语言</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
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
                            </tr>
                            </tbody>
                        </table>
                        <form class="layui-form" action="/sell/push/msg" method="post">
                            <input type="text" hidden name="openid" value="${user.openId}">
                            <div class="layui-form-item">
                                <label class="layui-form-label">消息:</label>
                                <div class="layui-input-block">
                                    <textarea type="text" name="msg" required  lay-verify="required" placeholder="请输入消息内容" autocomplete="off" class="layui-textarea"></textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">URL:</label>
                                <div class="layui-input-block">
                                    <textarea type="text" name="url" required  autocomplete="on" class="layui-textarea" placeholder="输入跳转URL(http/https协议前缀需要写)"></textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit lay-filter="formDemo">发送消息</button>
                                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                </#list>
            </div>
        </div>
    </div>
</body>
<#include "../common/script.ftlh">
<script>

</script>
</html>