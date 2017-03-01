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

    <!--FileInput CSS-->
    <link href="/Component/fileinput/css/fileinput.min.css" rel="stylesheet">

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
                <li class="active"><a href="#">增加片库</a></li>
                <li><a href="/RemoteController/jump">远程控制</a></li>
                <li><a href="/LoginController/jump">注销登陆</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">添加<span style="font-size: small">&nbsp;路径名不要出现中文</span></h1>

            <input type="file" name="files[]" id="txt_file" multiple class="file-loading"/>

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
                    <tbody>
                    <c:forEach items="${videoInfos}" var="info">
                        <c:out value="<tr>
                        <td>${info.id}</td>
                        <td>${info.name}</td>
                        <td>${info.dirname}</td>
                        <td><a href=\"/AddVideoController/delete/${info.id}\">删除</a></td>
                        </tr>" escapeXml="false"></c:out>
                    </c:forEach>
                    <%--<tr>--%>
                        <%--<td>1,002</td>--%>
                        <%--<td>amet</td>--%>
                        <%--<td>consectetur</td>--%>
                    <%--</tr>--%>
                    </tbody>
                </table>
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

<!-- canvas to blob for preview -->
<script src="/js/canvas-to-blob.min.js"></script>

<!-- FileInput JavaScript -->
<script src="/Component/fileinput/js/fileinput.min.js"></script>

<!-- Translate the FileInput's language , 也就是汉化包 -->
<script src="/Component/fileinput/js/fileinput_locale_zh.js"></script>

<script>
    //初始化fileinput
    var FileInput = function () {
        var oFile = new Object();
        //初始化fileinput控件（第一次初始化）
        oFile.Init = function (ctrlName, uploadUrl) {
            var control = $('#' + ctrlName);
            //初始化上传控件的样式
            control.fileinput({
                language: 'zh', //设置语言
                uploadUrl: uploadUrl, //上传的地址
                allowedFileExtensions: ['mp4', 'avi', 'wav'],//接收的文件后缀
                showUpload: true, //是否显示上传按钮
                showCaption: false,//是否显示标题
                browseClass: "btn btn-primary", //按钮样式
                //dropZoneEnabled: false,//是否显示拖拽区域
                //minImageWidth: 50, //图片的最小宽度
                //minImageHeight: 50,//图片的最小高度
                //maxImageWidth: 1000,//图片的最大宽度
                //maxImageHeight: 1000,//图片的最大高度
                //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
                //minFileCount: 0,
                uploadAsync: true,
                allowedPreviewTypes: ['image', 'text'],
                maxFileCount: 6, //表示允许同时上传的最大文件个数
                enctype: 'multipart/form-data',
                validateInitialCount: true,
                previewFileIcon: "<i class='glyphicon glyphicon-film'></i>",
                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            });
            //导入文件上传完成之后的事件
            $("#txt_file").on("filebatchuploadsuccess", function (event, data, previewId, index) {
                setTimeout(function () {
                    window.location.href = "/AddVideoController/jump";
                }, 1000);
            });
            $("#txt_file").on("filebatchuploadcomplete", function (event, data, previewId, index) {
                setTimeout(function () {
                    window.location.href = "/AddVideoController/jump";
                }, 1000);

            });
        }
        return oFile;
    };

    $(document).ready(function () {
        $(function () {
            //0.初始化fileinput
            var oFileInput = new FileInput();
            oFileInput.Init("txt_file", "update");
        });

        $("#dataTables").DataTable({
            responsive: true,
        });
    });
</script>
</body>
</html>
