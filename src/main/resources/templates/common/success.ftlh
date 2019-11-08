<html>
<#include "header.ftlh">
<body>
<div class="container">
    <div class="alert alert-success" role="alert">
        <h1 class="alert-heading">SUCCESS!</h1>
        <p>
            <span class="badge badge-pill badge-info">提示信息</span>
            ${msg}
        </p>
        <hr>
        <p class="mb-0" id="alert">3s后返回<a class="page-link" href="${url}">上级页面</a></p>
    </div>
    <script>
        setTimeout(function () {
            location.href="${url}"
        },3000);
    </script>
</div>
</body>
</html>