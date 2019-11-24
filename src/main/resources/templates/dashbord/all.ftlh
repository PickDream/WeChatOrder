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
            <div class="layui-col-md3">
                <div class="layui-card">
                    <div class="layui-card-header">用户总数</div>
                    <div class="layui-card-body" style="font-size: 40px;padding: 30px" >
                        ${userCount}
                    </div>
                </div>
            </div>
            <div class="layui-col-md3">
                <div class="layui-card">
                    <div class="layui-card-header">订单总数</div>
                    <div class="layui-card-body" style="font-size: 40px;padding: 30px" >
                        ${orderCount}
                    </div>
                </div>
            </div>
            <div class="layui-col-md3">
                <div class="layui-card">
                    <div class="layui-card-header">今天订单</div>
                    <div class="layui-card-body" style="font-size: 40px;padding: 30px" >
                        ${todayOrderCount}
                    </div>
                </div>
            </div>
            <div class="layui-col-md3">
                <div class="layui-card">
                    <div class="layui-card-header">产品总数</div>
                    <div class="layui-card-body" style="font-size: 40px;padding: 30px" >
                        ${productCount}
                    </div>
                </div>
            </div>


        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>数据统计</legend>
        </fieldset>
        <div class="layui-row">
            <div class="layui-col-md6">
                <div id="order-rate" style="height: 300px;width: 100%"></div>
            </div>
            <div class="layui-col-md6">
                <div id="sex-rate" style="height: 300px;width: 100%">
                </div>
            </div>
        </div>
    </div>

</div>
</body>
<#include "../common/script.ftlh">
<script src="/sell/echart-min.js"></script>
<script>
    var myChart1 = echarts.init(document.getElementById('order-rate'));
    var myChart3 = echarts.init(document.getElementById('sex-rate'))
    var list=[];

    <#list mapKeys as mapKey>
    list.push('${mapKey}');
    </#list>


    option1 = {
        title:{
            text:'销售显示',
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
            // data:['红富士','新疆葡萄','涨价之后的红烧肉']
            data:list
        },
        series: [
            {
                name:'',
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
                    <#list mapKeys as mapKey>

                    {value:${nameAndNumMap[mapKey]}, name:'${mapKey}'},
                    </#list>
                    <#--{value:${newOrder}, name:'红富士'},-->
                    <#--{value:${finishOrder}, name:'新疆葡萄'},-->
                    <#--{value:${cancelOrder}, name:'涨价之后的红烧肉'},-->
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
    option3 = {
        title:{
            text:'此月最受欢迎的商品',
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
            // data:['红富士','新疆葡萄','涨价之后的红烧肉']
            data:['${bestProductName}' ]
        },
        series: [
            {
                name:'',
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
                    {value:${newOrder}, name:'${bestProductName}'},
                    <#--{value:${finishOrder}, name:'新疆葡萄'},-->
                    <#--{value:${cancelOrder}, name:'涨价之后的红烧肉'},-->
                ]
            }
        ]
    };
    myChart3.setOption(option3);
    myChart1.setOption(option1);
</script>
</html>