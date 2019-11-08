<html>
    <#include "../common/header.ftlh">
<body>
<div class="layui-layout layui-layout-admin">
    <#include "../common/nav-top.ftlh">
    <#include "../common/nav-side.ftlh">
    <div class="layui-body" style="padding: 15px">
        <fieldset class="layui-elem-field site-demo-button" style="margin-top: 30px;">
            <legend>订单详情</legend>
            <div style="padding: 20px; background-color: #F2F2F2;">
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md6">
                        <div class="layui-card">
                            <div class="layui-card-header">订单总金额</div>
                            <div class="layui-card-body" style="font-size: xx-large;padding: 30px">
                                ${orderDTO.orderAmount}
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <div class="layui-card">
                            <div class="layui-card-header">基本信息</div>
                            <div class="layui-card-body">
                                订单ID:${orderDTO.orderId} <br>
                                买家名字:${orderDTO.buyerName} <br>
                                买家手机号:${orderDTO.buyerPhone} <br>
                                买家地址:${orderDTO.buyerAddress} <br>
                                订单状态:
                                <#if orderDTO.orderStatus==1 >
                                    <div style="display: inline-block; width: 10px;height: 10px;border-radius: 50%" class="layui-bg-green"></div>
                                <#else>
                                    <div style="display: inline-block; width: 10px;height: 10px;border-radius: 50%" class="layui-bg-orange"></div>
                                </#if>
                                ${orderDTO.orderStatusEnum.message}

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
        <fieldset class="layui-elem-field site-demo-button" style="margin-top: 30px;">
            <legend>订单商品详情</legend>
            <table class="layui-table">
                <thead>
                <tr>
                    <th>商品id</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as orderDetail>
                    <tr>
                        <td>${orderDetail.productId}</td>
                        <td>${orderDetail.productName}</td>
                        <td>${orderDetail.productPrice}</td>
                        <td>${orderDetail.productQuantity}</td>
                        <td>${orderDetail.productPrice.multipliedBy(orderDetail.productQuantity)}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </fieldset>
    </div>
</div>
</body>
<#include "../common/script.ftlh">
</html>