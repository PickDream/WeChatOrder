<html>
<#include "header.ftlh">
<body>
    <div class="container">
        <div class="alert alert-danger" role="alert">
            <h1 class="alert-heading">ERROR!</h1>
            <p>
                <span class="badge badge-pill badge-warning">错误信息</span>
                ${msg}
            </p>
            <hr>
            <p class="mb-0" id="alert">3s后返回<a class="page-link">上级页面</a></p>
        </div>
    </div>
</body>
<script>
    var time = 3;
    var p = document.querySelector('#alert');
    setInterval(function () {
        p.innerHTML = time+'s 后返回<a class="alert-link" href="${url}">上级页面</a>'
        time--;
        if (time===0){
            location.href="${url}"
        }
    },1000);
</script>
</html>