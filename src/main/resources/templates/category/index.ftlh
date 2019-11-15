<html>
<#include "../common/header.ftlh">
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <#include "../common/nav-top.ftlh">
    <#include "../common/nav-side.ftlh">
    <div class="layui-body" style="padding: 15px">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>增加/删除产品类别信息</legend>
        </fieldset>
        <form class="layui-form" action="/sell/seller/category/save">
            <input name="cid" hidden value="${(category.categoryId)!""}">
            <div class="layui-form-item">
                <label class="layui-form-label">输入品类名称</label>
                <div class="layui-input-block">
                    <input type="text" name="cname" required  lay-verify="required" placeholder="品类名称" autocomplete="off" class="layui-input" value="${(category.categoryName)!""}">
                </div>
            </div>
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
</html>