<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--lang的作用： 给浏览器翻译指定原始的语言，给屏幕阅读软件指定阅读的语言-->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>持明法洲后台管理系统</title>
    <%--引入样式文件--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">
    <%--引入js功能文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <%--引入kindEditor的配置文件--%>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <%--引入echarts的配置文件--%>
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>



</head>
<body>
<div class="nav navbar-inverse">
    <div class="container-fluid">
        <%--头部--%>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#dd">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
                <a href="" class="navbar-brand">持明法洲后台管理系统</a>
        </div>
<%--内容兼备,可响应式折叠--%>
        <div class="collapse navbar-collapse navbar-right" id="dd" >
            <ul class="nav navbar-nav">
                <li><a href="#">欢迎:${admin.nickname}</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/safeExit">安全退出</a></li>
            </ul>
        </div>
    </div>
</div>
<hr>
    <%--手风琴--%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <div class="panel-group" id="pg">
                    <div class="panel panel-danger" id="pic1">
                        <div class="panel panel-heading" style="text-align: center">
                            <a href="#pd1" class="panel-title" data-toggle="collapse" data-parent="#pg">轮播图管理</a>
                        </div>
                        <div class="panel-collapse collapse in" id="pd1">
                            <div class="panel-body" style="text-align: center">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/banner/banner.jsp')" class="btn btn-danger" >查看所有轮播图</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-success" id="guru1">
                        <div class="panel panel-heading" style="text-align: center">
                            <a href="#pd2" class="panel-title" data-toggle="collapse" data-parent="#pg">专辑管理</a>
                        </div>
                        <div class="panel-collapse collapse " id="pd2">
                            <div class="panel-body" style="text-align: center">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/album/album.jsp')" class="btn btn-success" >查看所有专辑</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-warning" id="artic1">
                        <div class="panel panel-heading" style="text-align: center">
                            <a href="#pd3" class="panel-title" data-toggle="collapse" data-parent="#pg">文章管理</a>
                        </div>
                        <div class="panel-collapse collapse " id="pd3">
                            <div class="panel-body" style="text-align: center">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/article/article.jsp')" class="btn btn-warning" >查看所有文章</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info" id="user1">
                        <div class="panel panel-heading" style="text-align: center">
                            <a href="#pd4" class="panel-title" data-toggle="collapse" data-parent="#pg">用户管理</a>
                        </div>
                        <div class="panel-collapse collapse " id="pd4">
                            <div class="panel-body" style="text-align: center">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/user/user.jsp')" class="btn btn-info" >查看所有用户</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/user/line.jsp')" class="btn btn-info" >用户注册趋势</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:$('#contentLayout').load('${pageContext.request.contextPath}/user/map.jsp')" class="btn btn-info" >用户地区分布</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%--巨幕--%>
            <div class="col-md-9" id="contentLayout">
                <div class="jumbotron">
                    <h2>
                        欢迎来到持明法洲后台管理系统
                    </h2>
                </div>
                <div>
                    <img src="shouye.jpg" alt="" width="100%" height="300px">
                </div>
            </div>
        </div>
        <div class="panel panel-footer" style="text-align: center">
            <p>持明法洲后台管理系统@百知教育2019.7.9</p>
        </div>
    </div>

</body>
</html>