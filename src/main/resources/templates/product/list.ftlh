<html>
<#include "../common/header.ftlh">
<body>
<div class="layui-layout layui-layout-admin">
    <#include "../common/nav-top.ftlh">
    <#include "../common/nav-side.ftlh">
    <div class="layui-body" style="padding: 15px">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>商品信息显示</legend>
            <table class="layui-table">
                <thead>
                <tr>
                    <th>商品id</th>
                    <th>名称</th>
                    <th>图片</th>
                    <th>单价</th>
                    <th>库存</th>
                    <th>描述</th>
                    <th>类目</th>
                    <th>创建时间</th>
                    <th>修改时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list productInfoPage.content as productInfo>
                    <tr>
                        <td>${productInfo.productId}</td>
                        <td>${productInfo.productName}</td>
                        <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                        <td>${productInfo.productPrice}</td>
                        <td>${productInfo.productStock}</td>
                        <td>${productInfo.productDescription}</td>
                        <td>${productInfo.categoryType}</td>
                        <td>${productInfo.createTime}</td>
                        <td>${productInfo.updateTime}</td>
                        <td><a href="/sell/seller/product/index?productId=${productInfo.productId}" class="layui-btn layui-btn-primary">修改</a></td>
                        <td>
                            <#if productInfo.productStatus == 0>
                                <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}" class="layui-btn layui-btn-danger">下架</a>
                            <#else>
                                <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}" class="layui-btn layui-btn-normal">上架</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </fieldset>
        <!--分页-->
        <nav aria-label="Page example">
            <ul class="pagination justify-content-center">
                <#if currentPage lte 1>
                    <li class="page-item disabled">
                        <a class="page-link" href="/sell/seller/product/list?page=${currentPage - 1}" tabindex="-1" aria-disabled="true">上一页</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="/sell/seller/product/list?page=${currentPage - 1}" tabindex="-1" aria-disabled="true">上一页</a>
                    </li>
                </#if>

                <#list 1..productInfoPage.getTotalPages() as index>
                    <#if currentPage==index>
                        <li class="page-item disabled"><a class="page-link" href="/sell/seller/product/list?page=${index}">${index}</a></li>
                    <#else>
                        <li class="page-item"><a class="page-link" href="/sell/seller/product/list?page=${index}">${index}</a></li>
                    </#if>
                </#list>

                <#if currentPage gte productInfoPage.getTotalPages()>
                    <li class="page-item disabled">
                        <a class="page-link" href="/sell/seller/product/list?page=${currentPage + 1}">下一页</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="/sell/seller/product/list?page=${currentPage + 1}">下一页</a>
                    </li>
                </#if>
            </ul>
        </nav>
    </div>
</div>
</body>
<#include "../common/script.ftlh">
</html>