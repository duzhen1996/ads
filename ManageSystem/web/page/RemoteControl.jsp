<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<!-- saved from url=(0041)http://v3.bootcss.com/examples/dashboard/ -->
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/">
    <title>广告投送管理系统</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/dashboard.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/Component/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

</head>

<body style="font: Microsoft YaHei, sans-serif">

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">广告投放管理系统</a>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="/AddVideoController/jump">增加片库</a></li>
                <li class="active"><a href="#">远程控制</a></li>
                <li><a href="/LoginController/jump">注销登陆</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="sub-header">状态控制</h1>
            <div class="btn-group col-lg-12 col-sm-12 col-md-12">
                <a class="btn btn-primary col-lg-4 col-sm-4 col-md-4" href="/rest/statue/0">播放</a>
                <a class="btn btn-primary col-lg-4 col-sm-4 col-md-4" href="/rest/statue/1">暂停</a>
                <a class="btn btn-primary col-lg-4 col-sm-4 col-md-4" href="/rest/statue/2">清空播放列表</a>
            </div>
            <h1 class="sub-header">现有片库</h1>
            <div class="table-responsive">
                <table class="table table-striped" id="dataTables">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>片源名称</th>
                        <th>片源路径</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="allVideoList">
                    <c:forEach items="${videoInfos}" var="info">
                        <c:out value="<tr id = \"${info.id}\">
                        <td>${info.id}</td>
                        <td>${info.name}</td>
                        <td>${info.dirname}</td>
                        <td><a class=\"btn btn-success\" href=\"javascript:add(${info.id})\" id=\"${info.id}button\">添加</a></td>
                        </tr>" escapeXml="false"></c:out>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <h1 class="sub-header">播放列表</h1>
            <div class="table-responsive">
                <table class="table table-striped" id="dataTables2">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>片源名称</th>
                        <th>片源路径</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="remoteControlList">
                    </tbody>
                </table>
                <div class="btn-group col-lg-12 col-sm-12 col-md-12">
                    <a class="btn btn-primary col-lg-6 col-sm-6 col-md-6" href="javascript:sendMessage(0)">排队播放</a>
                    <a class="btn btn-primary col-lg-6 col-sm-6 col-md-6" href="javascript:sendMessage(1)">立即播放</a>
                </div>
            </div>

        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/js/jquery-2.2.3.min.js"></script>
<script src="/js/bootstrap.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/Component/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="/Component/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

<script>
    idArray = new Array();
    function add(a) {
        idArray.push(a);
        var info = $("#" + a);
        $("#remoteControlList").prepend(info);
        $("#" + a + "button").removeClass("btn btn-success").addClass("btn btn-danger");
        $("#" + a + "button").html("移除");
        $("#" + a + "button").attr("href", "javascript:remove(" + a + ")");
    }

    function remove(b) {
        //遍历并且删除某个元素
        for (var i = 0; i < idArray.length; i++) {
            if (idArray[i] == b) {
                idArray.splice(i,1);
            }
        }
        var info = $("#" + b);
        $("#allVideoList").prepend(info);
        $("#" + b + "button").removeClass("btn btn-danger").addClass("btn btn-success");
        $("#" + b + "button").html("添加");
        $("#" + b + "button").attr("href", "javascript:add(" + b + ")");
    }

    function sendMessage(a){
        var restString=a;
        for (var i = 0; i < idArray.length; i++) {
            restString = restString + "g" + idArray[i];
        }
        window.location.href="/rest/send/"+restString;
    }

    $(document).ready(function () {
        $(".btn-primary").click(function()
        {
            $(".btn-primary").disabled = true;
        });

        $("table").DataTable({
            responsive: true,
        });

    });
</script>
</body>
</html>
