<html>
    <#include "../common/header.ftlh">
<body>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="list-group" id="list-tab" role="tablist">
                    <a class="list-group-item list-group-item-action active" id="list-home-list" data-toggle="basic-info" href="#list-basic" role="tab" aria-controls="home">订单基本信息</a>
                    <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="orderInfo" href="#list-order-info" role="tab" aria-controls="profile">购买商品信息</a>
                </div>
            </div>
            <div class="col-8">
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="list-basic" role="tabpanel" aria-labelledby="basic-info">AAA</div>
                    <div class="tab-pane fade" id="list-order-info" role="tabpanel" aria-labelledby="order-info">BBB</div>
                </div>
            </div>
        </div>
    </div>
</body>
<#include "../common/script.ftlh">
</html>