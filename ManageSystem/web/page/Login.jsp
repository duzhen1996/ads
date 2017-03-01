<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- saved from url=(0038)http://v3.bootcss.com/examples/signin/ -->
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>登陆</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/signin.css" rel="stylesheet">

    <!-- 把JQuery导入 -->
    <script src="/js/jquery-2.2.3.min.js"></script>

</head>

<body>

<div class="container">

    <form class="form-signin">
        <h2 class="form-signin-heading">欢迎回来，请登录</h2>
        <input type="text" id="username" class="form-control" placeholder="用户名" required="required" autofocus="">
        <input type="password" id="password" class="form-control" placeholder="密码" required="required">
        <button class="btn btn-lg btn-primary btn-block" type="button" id="loginbutton">登陆</button>
    </form>

</div> <!-- /container -->
<script>
    $(document).ready(function () {
        $("#loginbutton").click(function () {
            $.ajax({
                url: "/LoginController/login",
                datatype: "json",
                type: "POST",
                data: {
                    username: $("#username").val(),
                    password: $("#password").val(),
                },
                success: function (data) {
                    if (data == false) {
                        alert("用户名或者密码错误");
                    }
                    else {
                        window.location.href="/AddVideoController/jump";
                    }
                }
            });
        });
    });
</script>
</body>
</html>