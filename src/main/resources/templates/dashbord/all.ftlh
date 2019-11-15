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
            <legend>成交率信息/用户性别分布</legend>
        </fieldset>
        <div class="layui-row">
            <div class="layui-col-md6">
                <div id="order-rate" style="height: 300px;width: 100%"></div>
            </div>
            <div class="layui-col-md6">
                <div id="sex-rate" style="height: 300px;width: 100%"></div>
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>商品补货预测（紧急程度）</legend>
        </fieldset>
        <blockquote class="layui-elem-quote">
            计算公式: （销量/库存）*100% 排序
        </blockquote>
        <br>
        <div class="layui-progress layui-progress-big">
            <div class="layui-progress-bar" lay-percent="20%"></div>
        </div>
        <br>
        <div class="layui-progress layui-progress-big">
            <div class="layui-progress-bar layui-bg-orange" lay-percent="50%"></div>
        </div>
        <br>
        <div class="layui-progress layui-progress-big" lay-showPercent="true">
            <div class="layui-progress-bar layui-bg-blue" lay-percent="80%"></div>
        </div>
    </div>

</div>
</body>
<#include "../common/script.ftlh">
<script src="/sell/echart-min.js"></script>
<script>
    var myChart1 = echarts.init(document.getElementById('order-rate'));
    var myChart2 = echarts.init(document.getElementById('sex-rate'))
    option1 = {
        title:{
            text:'成交率信息表',
            top:'bottom',
            left:'center',
            textStyle:{
                fontSize: 14,
                fontWeight: '',
                color: '#333'
            },
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['新订单','订单完结','取消订单']
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:${newOrder}, name:'新订单'},
                    {value:${finishOrder}, name:'订单完结'},
                    {value:${cancelOrder}, name:'取消订单'},
                ]
            }
        ]
    }
    option2 = {
        title:{
            text:'性别分布',
            top:'bottom',
            left:'center',
            textStyle:{
                fontSize: 14,
                fontWeight: '',
                color: '#333'
            },
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['男','女','未知']
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:${man}, name:'男'},
                    {value:${woman}, name:'女'},
                    {value:${none}, name:'未知'},
                ]
            }
        ]
    }
    myChart1.setOption(option1);
    myChart2.setOption(option2);
</script>
</html>