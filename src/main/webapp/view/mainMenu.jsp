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
    <script type="text/javascript" src="//at.alicdn.com/t/font_533369_3w359x66sdnm6lxr.js"></script>
</head>
<body>
<%--顶部--%>
<div id="main_menu_top">
    <div id="main_menu_top_left">
        <img src="../style/image/logo.png"/>
    </div>
    <div id="main_menu_top_right">
        <a href="javascript:void(0);">
            <svg class="icon left_menu_top" aria-hidden="true">
                <use xlink:href="#icon-gerenzhongxin"></use>
            </svg>
            <span>123</span>
        </a>
        <a href="javascript:void(0);">
            <svg class="icon left_menu_top" aria-hidden="true">
                <use xlink:href="#icon-zhuxiao"></use>
            </svg>
        </a>
    </div>
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
                <ul>
                    <li><a href="javascript:void(0);"><span>日志管理</span></a></li>
                </ul>
            </a>
        </li>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-banji"></use>
                </svg>
                <span>班级管理</span><i class="nav-more"></i>
            </a>
        </li>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-xuesheng"></use>
                </svg>
                <span>学生管理</span><i class="nav-more"></i>
            </a>
            <ul>
                <li><a href="javascript:void(0);"><span>我的学生</span></a></li>
                <li><a href="javascript:void(0);"><span>申请列表</span></a></li>
                <li><a href="javascript:void(0);"><span>历史动态</span></a></li>
            </ul>
        </li>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-shijuan"></use>
                </svg>
                <span>试卷管理</span><i class="nav-more"></i>
            </a>
            <ul>
                <li><a href="javascript:void(0);"><span>我的题库</span></a></li>
                <li><a href="javascript:void(0);"><span>试卷列表</span></a></li>
            </ul>
        </li>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-examiner"></use>
                </svg>
                <span>考官信息</span><i class="nav-more"></i>
            </a>
            <ul>
                <li><a href="javascript:void(0);"><span>考官列表</span></a></li>
                <li><a href="javascript:void(0);"><span>申请列表</span></a></li>
            </ul>
        </li>
        <li class="nav-item">
            <a href="javascript:void(0);">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-chengji"></use>
                </svg>
                <span>成绩信息</span><i class="nav-more"></i>
            </a>
        </li>
    </ul>
</div>
<%--主显示div--%>
<div id="main_show">
    <%--实验发现如果不加“../view”会出现问题--%>
    <iframe id="main_show_iframe" src="../view/firstContext.jsp" width="100%" height="100%" frameborder="0"
            marginwidth="0" marginheight="0"
            scrolling="no">
    </iframe>
</div>
</body>

<script type="text/javascript">
    // a标签onclick对应的方法要放在页面加载之前
    function ttt() {
        // 切换页面
        $("#main_show_iframe").attr("src", "../view/others/1.jsp");
    }

    window.onload = function () {
        $("title").text("欢迎使用");
    }
</script>
</html>
