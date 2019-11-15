<html>
<#include "../common/header.ftlh">
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <#include "../common/nav-top.ftlh">
    <#include "../common/nav-side.ftlh">
    <div class="layui-body" style="padding: 15px">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>数据总览</legend>
        </fieldset>
        <div class="layui-row">
            <div class="layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-header">用户总数</div>
                    <div class="layui-card-body" style="font-size: 40px;padding: 30px" >
                        ${userCount}
                    </div>
                </div>
            </div>
            <div class="layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-header">订单总数</div>
                    <div class="layui-card-body" style="font-size: 40px;padding: 30px" >
                        ${orderCount}
                    </div>
                </div>
            </div>
            <div class="layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-header">产品总数</div>
                    <div class="layui-card-body" style="font-size: 40px;padding: 30px" >
                        ${productCount}
                    </div>
                </div>
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>数据总览</legend>
        </fieldset>
    </div>
</div>
</body>
<#include "../common/script.ftlh">
</html>