<%--
  Created by IntelliJ IDEA.
  User: LuoYuChengZhou
  Date: 2018/1/7
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../style/css/sweetalert2.min.css"/>
    <script type="text/javascript" src="../style/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../style/js/sweetalert2.min.js"></script>
    <script type="text/javascript" src="../style/js/es6-promise.min.js"></script>
    <script type="text/javascript" src="../style/js/jquery.cookie.js"></script>
</head>
<body>
    <h2>欢迎使用到测入流在线评测系统</h2>
</body>
<script type="text/javascript">
    window.onload = function () {
        if ($.cookie('isReg') === "1") {
            swal({
                title: '注册成功!',
                text: '欢迎使用',
                type: 'success',
                timer: 2000
            });
        }
    }
</script>
</html>
