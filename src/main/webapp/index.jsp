<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎登录</title>
    <link rel="stylesheet" type="text/css" href="style/css/inputTextStyle.css"/>
    <link rel="stylesheet" type="text/css" href="style/css/checkbox.css"/>
    <link rel="stylesheet" type="text/css" href="style/css/login.css"/>
    <script type="text/javascript" src="style/js/jquery-3.2.1.js"></script>
</head>
<body>
<div id="center-form">
    <span class="input input--minoru">
		<input class="input__field input__field--isao" type="text" id="input-name"/>
		<label class="input__label input__label--isao" for="input-name">
		<span class="input__label-content input__label-content--isao">用户名</span>
		</label>
	</span>
    <span class="input input--minoru">
		<input class="input__field input__field--isao" type="password" id="input-pass"/>
		<label class="input__label input__label--isao" for="input-pass">
		<span class="input__label-content input__label-content--isao">密码</span>
		</label>
	</span>
    <div id="first" class="buttonBox">
        <button>登录</button>
        <div class="border"></div>
        <div class="border"></div>
    </div>
    <div id="fourth" class="buttonBox">
        <button>注册</button>
        <div class="border"></div>
        <div class="border"></div>
        <div class="border"></div>
        <div class="border"></div>
    </div>
</div>
<div id="message-div"></div>
<div id="change-button">
    <h4 id="currentName"></h4>
    <div id="seventh" class="buttonBox">
        <button id="goalName"></button>
    </div>
</div>
</body>
<script type="text/javascript">
    // 登录类型（1-考官登录，2-考生登录）
    var loginType = "2";
    // 页面加载完后执行
    window.onload = function () {
        $("#currentName").html("当前登录模式：<span style='display:block;color: #9B30FF'>考生登录</span>");
        $("#goalName").text("切换至考官登录");
        // 绑定回车键
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $("#first").click();
            }
        });
        $("#first").click(function () {
            var userName = $("#input-name").val();
            var password = $("#input-pass").val();
            if (!userName) {
                $("#message-div").html("<span style='display:block;color: red'>用户名不能为空</span>");
                return;
            }
            if (!password) {
                $("#message-div").html("<br><br><br><br><span style='display:block;color: red'>密码不能为空</span>");
                return;
            } else {
                $("#message-div").html("");
            }
            $.ajax({
                url: '/Login/exeLogin',
                type: 'POST', //GET
                async: false,    //或false,是否异步
                data: {
                    loginName: userName,
                    loginPass: password,
                    type: loginType
                },
                timeout: 5000,    //超时时间
                dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                success: function (data) {
                    if (data.status === 200) {
                        alert("登录成功");
                    } else if (data.status === 400) {
                        $("#message-div").html("<span style='display:block;color: Orange'>" + data.msg + "</span>");
                    } else  if (data.status === 401)  {
                        location.href="http://127.0.0.1:2333/index.jsp";
                    }
                },
                error: function () {
                    alert("服务器维护中");
                }
            })
        });
        $("#seventh").click(function () {
            if(loginType === "2"){
                $("#currentName").html("当前登录模式：<span style='color: #00BFFF'>考官登录</span>");
                $("#goalName").text("切换至考生登录");
                loginType = "1";
            }else  if(loginType === "1"){
                $("#currentName").html("当前登录模式：<span style='color: #9B30FF'>考生登录</span>");
                $("#goalName").text("切换至考官登录");
                loginType = "2";
            }
        });
    };
</script>
</html>
