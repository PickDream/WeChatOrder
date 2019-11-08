<html>
<#include "../common/header.ftlh">
<body class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
        <#include "../common/nav-top.ftlh">
        <#include "../common/nav-side.ftlh">
        <div class="layui-body" style="padding: 15px">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                <legend>订单信息显示</legend>
            </fieldset>

                <div class="layui-form">
                    <table class="layui-table">
                        <thead class="thead-light">
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getOrderStatusEnum().message}</td>
                                <td>${orderDTO.getPayStatusEnum().message}</td>
                                <td>${orderDTO.createTime}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}" class="layui-btn">详情</a></td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().message!="已取消">
                                        <a class="layui-btn layui-btn-normal" href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" role="button">取消</a>
                                    <#else>
                                        <a class="layui-btn layui-btn-disabled" href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" role="button">取消</a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <!--分页-->
                    <nav aria-label="Page example">
                        <ul class="pagination justify-content-center">
                            <#if currentPage lte 1>
                                <li class="page-item disabled">
                                    <a class="page-link" href="/sell/seller/order/list?page=${currentPage - 1}" tabindex="-1" aria-disabled="true">上一页</a>
                                </li>
                            <#else>
                                <li class="page-item">
                                    <a class="page-link" href="/sell/seller/order/list?page=${currentPage - 1}" tabindex="-1" aria-disabled="true">上一页</a>
                                </li>
                            </#if>

                            <#list 1..orderDTOPage.getTotalPages() as index>
                                <#if currentPage==index>
                                    <li class="page-item disabled"><a class="page-link" href="/sell/seller/order/list?page=${index}">${index}</a></li>
                                <#else>
                                    <li class="page-item"><a class="page-link" href="/sell/seller/order/list?page=${index}">${index}</a></li>
                                </#if>
                            </#list>

                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="page-item disabled">
                                    <a class="page-link" href="/sell/seller/order/list?page=${currentPage + 1}">下一页</a>
                                </li>
                            <#else>
                                <li class="page-item">
                                    <a class="page-link" href="/sell/seller/order/list?page=${currentPage + 1}">下一页</a>
                                </li>
                            </#if>
                        </ul>
                    </nav>
                </div>
        </div>
    </div>
</body>
<#include "../common/script.ftlh">
</html>