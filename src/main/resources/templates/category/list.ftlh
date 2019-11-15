<html>
<#include "../common/header.ftlh">
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <#include "../common/nav-top.ftlh">
    <#include "../common/nav-side.ftlh">
    <div class="layui-body" style="padding: 15px">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>产品类别信息</legend>
        </fieldset>
        <ul class="layui-timeline">
            <#list categories as category>
            <li class="layui-timeline-item">
                <i class="layui-icon layui-timeline-axis"></i>
                <div class="layui-timeline-content layui-text">
                    <h3 class="layui-timeline-title">${category.categoryName}</h3>
                    <p>
                        品类ID:${category.categoryId}
                        <br>
                        <a href="/sell/seller/category/update?cid=${category.categoryId}" class="layui-btn"><i class="layui-icon"></i></a>
                        <a href="/sell/seller/category/del?cid=${category.categoryId}" class="layui-btn"><i class="layui-icon"></i></a>
                    </p>
                </div>
            </li>
            </#list>
        </ul>
    </div>
</div>
</body>
<#include "../common/script.ftlh">
</html>
