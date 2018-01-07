<%--
  Created by IntelliJ IDEA.
  User: LuoYuChengZhou
  Date: 2018/1/5
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%--${pageContext.request.contextPath}--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="../style/css/nav.css"/>
    <link rel="stylesheet" type="text/css" href="../style/css/mainMenu.css"/>
    <script type="text/javascript" src="../style/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../style/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="../style/js/nav.js"></script>
    <script type="text/javascript" src="//at.alicdn.com/t/font_533369_6jsqbsvydbxfxbt9.js"></script>
</head>
<body>
<%--顶部--%>
<div id="main_menu_top">

</div>
<%--菜单--%>
<div class="nav">
    <div class="nav-top">
        <div id="mini" style="border-bottom:1px solid rgba(255,255,255,.1)">
            <svg class="icon left_menu_top" aria-hidden="true">
                <use xlink:href="#icon-menu"></use>
            </svg>
        </div>
    </div>
    <ul>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-xitong"></use>
                </svg>
                <span>系统管理</span><i class="nav-more"></i>
            </a>
            <ul>
                <li><a href="javascript:void(0);" onclick="ttt()"><span>网站设置</span></a></li>
                <li><a href="javascript:void(0);"><span>友情链接</span></a></li>
                <li><a href="javascript:void(0);"><span>分类管理</span></a></li>
                <li><a href="javascript:void(0);"><span>系统日志</span></a></li>
            </ul>
        </li>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-banji"></use>
                </svg>
                <span>班级管理</span><i class="nav-more"></i>
            </a>
            <ul>
                <li><a href="javascript:void(0);"><span>站内新闻</span></a></li>
                <li><a href="javascript:void(0);"><span>站内公告</span></a></li>
                <li><a href="javascript:void(0);"><span>登录日志</span></a></li>
            </ul>
        </li>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-xuesheng"></use>
                </svg>
                <span>学生管理</span><i class="nav-more"></i>
            </a>
            <ul>
                <li><a href="javascript:void(0);"><span>订单列表</span></a></li>
                <li><a href="javascript:void(0);"><span>打个酱油</span></a></li>
                <li><a href="javascript:void(0);"><span>也打酱油</span></a></li>
            </ul>
        </li>
    </ul>
</div>
<%--主显示div--%>
<div id="main_show">
    <%--实验发现如果不加“../view”会出现问题--%>
    <iframe id="main_show_iframe" src="../view/firstContext.jsp" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0"
            scrolling="no">
    </iframe>
</div>
</body>
<script type="text/javascript">
    // a标签onclick对应的方法要放在页面加载之前
    function ttt() {
        // 切换页面
        $("#main_show_iframe").attr("src","../view/others/1.jsp");
    }
    window.onload = function () {
        $("title").text("欢迎使用");
    }
</script>
</html>
