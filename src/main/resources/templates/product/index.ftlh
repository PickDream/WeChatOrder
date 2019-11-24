<html>
<#include "../common/header.ftlh">
<body>
    <div class="layui-layout layui-layout-admin">
        <#include "../common/nav-top.ftlh">
        <#include "../common/nav-side.ftlh">
        <div class="layui-body" style="padding: 15px">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                <legend>商品信息新增/修改</legend>
            </fieldset>
            <form class="layui-form" action="/sell/seller/product/save" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="productName" required  lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input"  value="${(productInfo.productName)!''}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">价格</label>
                    <div class="layui-input-block">
                        <input type="text" name="productPrice" required  lay-verify="required" placeholder="请输入价格" autocomplete="off" class="layui-input"  value="${(productInfo.productPrice)!''}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">库存</label>
                    <div class="layui-input-block">
                        <input type="text" name="productStock" required  lay-verify="required" placeholder="请输入库存" autocomplete="off" class="layui-input"  value="${(productInfo.productStock)!''}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">描述</label>
                    <div class="layui-input-block">
                        <input type="text" name="productDescription" required  lay-verify="required" placeholder="请输入价格" autocomplete="off" class="layui-input"  value="${(productInfo.productDescription)!''}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">图片</label>
                    <img id = "img" height="100" width="100" style="margin-left: 30px" src="${(productInfo.productIcon)!''}"  alt="URL地址解析失败">
                    <div class="layui-input-block">
                        <input type="text" id="input_img" name="productIcon" required  lay-verify="required" placeholder="请输入图片url" autocomplete="off" class="layui-input"  value="${(productInfo.productIcon)!''}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">类目</label>
                    <div class="layui-input-block">
                        <select name="categoryType" lay-verify="">
                        <option value="">请选择一个类目</option>
                        <#list categoryList as category>
                            <option value="${category.categoryType}"
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                        selected
                                    </#if>
                            >${category.categoryName}
                            </option>
                        </#list>
                    </select>
                    </div>
                </div>
                <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
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
<script>
    var btn = document.querySelector("#input_img");
    var img = document.querySelector("#img");
    btn.oninput = function () {
        img.src = this.value;
    }
</script>
</html>
