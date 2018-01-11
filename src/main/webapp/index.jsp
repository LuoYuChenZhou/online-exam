<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎登录</title>
    <link rel="stylesheet" type="text/css" href="style/css/inputTextStyle.css"/>
    <link rel="stylesheet" type="text/css" href="style/css/checkbox.css"/>
    <link rel="stylesheet" type="text/css" href="style/css/input-style.css"/>
    <link rel="stylesheet" type="text/css" href="style/css/loadStyle.css"/>
    <link rel="stylesheet" type="text/css" href="style/css/login.css"/>
    <link rel="stylesheet" type="text/css" href="style/css/sweetalert2.min.css"/>
    <script type="text/javascript" src="style/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="style/js/sweetalert2.min.js"></script>
    <script type="text/javascript" src="style/js/es6-promise.min.js"></script>
    <script type="text/javascript" src="style/js/jquery.cookie.js"></script>
</head>
<body>

<%--登录页面--%>
<div id="center-form">
    <span class="input input--isao">
		<input class="input__field input__field--isao" type="text" id="input-name"/>
		<label class="input__label input__label--isao" for="input-name">
		<span class="input__label-content input__label-content--isao">用户名</span>
		</label>
	</span>
    <span class="input input--isao">
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

<%--注册页面--%>
<div id="register-form">
    <div id="reg-tab">
        <div id="left-tab" class="tab-class tab-click">
            <span>考生注册</span>
        </div>
        <div id="right-tab" class="tab-class">
            <span>考官注册</span>
        </div>
    </div>
    <div class="for-reset">
        <input class="effect-17" type="text" placeholder="" id="reg-name">
        <label><span style="color: orange">*</span>用户名</label>
        <span class="focus-border"></span>
        <span class="reg_msg"></span>
    </div>
    <div class="for-reset">
        <input class="effect-17" type="password" placeholder="" id="reg-password">
        <label><span style="color: orange">*</span>登录密码</label>
        <span class="focus-border"></span>
        <span class="reg_msg"></span>
    </div>
    <div class="for-reset">
        <input class="effect-17" type="password" placeholder="" id="reg-rePassword">
        <label><span style="color: orange">*</span>再次输入密码</label>
        <span class="focus-border"></span>
        <span class="reg_msg"></span>
    </div>
    <div class="for-reset only-ee">
        <input class="effect-17" type="text" placeholder="" id="reg-eeNo">
        <label>考生号</label>
        <span class="focus-border"></span>
        <span class="reg_msg"></span>
    </div>
    <div class="for-reset">
        <input class="effect-17" type="text" placeholder="" id="reg-realName">
        <label><span style="color: orange">*</span>真实姓名</label>
        <span class="focus-border"></span>
        <span class="reg_msg"></span>
    </div>
    <fieldset id="sex-div">
        <legend><span style="color: orange">*</span>性别：</legend>
        <label class="el-radio" style="display: inline-block">
            <span>男</span>
            <input type="radio" name="reg-sex" value="0">
            <span class="el-radio-style"></span>
        </label>
        <label class="el-radio" style="display: inline-block">
            <span>女</span>
            <input type="radio" name="reg-sex" value="1">
            <span class="el-radio-style"></span>
        </label>
        <span id="reg_sex_msg"></span>
    </fieldset>
    <div class="for-reset">
        <input class="effect-17" type="text" placeholder="" id="reg-phone">
        <label><span style="color: orange">*</span>联系电话</label>
        <span class="focus-border"></span>
        <span class="reg_msg"></span>
    </div>
    <div class="for-reset">
        <input class="effect-17" type="text" placeholder="" id="reg-email">
        <label>电子邮箱</label>
        <span class="focus-border"></span>
        <span class="reg_msg"></span>
    </div>
    <div id="commit" class="reg-buttonBox">
        <button>提交</button>
        <div class="border"></div>
        <div class="border"></div>
        <div class="border"></div>
        <div class="border"></div>
    </div>
    <div id="cancel" class="reg-buttonBox">
        <button>取消</button>
        <div class="border"></div>
        <div class="border"></div>
        <div class="border"></div>
        <div class="border"></div>
    </div>
</div>

<%--load加载页面--%>
<div id="preloader6">
    <span></span>
    <span></span>
    <span></span>
    <span></span>
</div>

<%--遮罩层--%>
<div id="mask"></div>
</body>
<script type="text/javascript">
    var loginType = "2";// 登录类型（1-考官登录，2-考生登录）
    var regType = "2";//注册类型（1-考官注册，2-考生注册）
    var load = $("#preloader6");//load加载图形
    var mask = $("#mask");//遮罩层

    // 页面加载完后执行
    window.onload = function () {
        //默认隐藏注册
        cancelRegister();
        load.hide();

        //设置默认值
        $("#currentName").html("当前登录模式：<span style='display:block;color: #9B30FF'>考生登录</span>");
        $("#goalName").text("切换至考官登录");
        $(".for-reset input").val("");
        mask.css("height", $(document).height());
        mask.css("width", $(document).width());

        //判断输入框是否有内容
        $(".for-reset input").focusout(function () {
            if ($(this).val() !== "") {
                $(this).addClass("has-content");
            } else {
                $(this).removeClass("has-content");
            }
        });

        // 点击登录按钮
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
            mask.show();
            load.show();
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
                        $.cookie('online_token', data.data.token);
                        toMainMenu();
                    } else if (data.status === 400) {
                        $("#message-div").html("<span style='display:block;color: Orange'>" + data.msg + "</span>");
                    } else if (data.status === 401) {
                        location.href = "http://127.0.0.1:2333/index.jsp";
                    }
                },
                error: function () {
                    swal('错误', '服务器维护中!', 'error');
                },
                complete: function () {
                    mask.hide();
                    load.hide();
                }
            })
        });
        $("#seventh").click(function () {
            if (loginType === "2") {
                $("#currentName").html("当前登录模式：<span style='color: #00BFFF'>考官登录</span>");
                $("#goalName").text("切换至考生登录");
                loginType = "1";
            } else if (loginType === "1") {
                $("#currentName").html("当前登录模式：<span style='color: #9B30FF'>考生登录</span>");
                $("#goalName").text("切换至考官登录");
                loginType = "2";
            }
        });

        //点击注册按钮显示隐藏层和弹出层
        $("#fourth").click(function () {
            mask.fadeIn();  //显示隐藏层
            $("#register-form").fadeIn();  //显示隐藏层
        });

        //点击考生注册
        $("#left-tab").click(function () {
            if (regType === "2") {
                return;
            }
            regType = "2";
            $(".only-ee").show();
            $("#left-tab").addClass("tab-click");
            $("#right-tab").removeClass("tab-click");
            userNameIsExist();
        });

        //点击考官注册
        $("#right-tab").click(function () {
            if (regType === "1") {
                return;
            }
            regType = "1";
            $(".only-ee").hide();
            $("#right-tab").addClass("tab-click");
            $("#left-tab").removeClass("tab-click");
            userNameIsExist();
        });

        //用户名字段失去焦点触发,判断用户名是否已占用
        $("#reg-name").blur(function () {
            userNameIsExist();
        });

        //密码字段失去焦点触发,判断是否为空
        $("#reg-password").blur(function () {
            var password = $("#reg-password");
            if (!password.val()) {
                password.siblings(".reg_msg").text("请输入登录密码");
            } else {
                password.siblings(".reg_msg").text("");
            }
        });

        //重复密码字段失去焦点触发,判断两次输入的是否相同
        $("#reg-rePassword").blur(function () {
            var password = $("#reg-password");
            var rePassword = $("#reg-rePassword");
            if (!password.val()) {
                password.siblings(".reg_msg").text("请输入登录密码");
            } else if (!rePassword.val()) {
                rePassword.siblings(".reg_msg").text("请再次输入密码");
            } else if (password.val() !== rePassword.val()) {
                rePassword.siblings(".reg_msg").text("两次输入的密码不一致");
            } else {
                password.siblings(".reg_msg").text("");
                rePassword.siblings(".reg_msg").text("");
            }
        });

        function userNameIsExist() {
            var name = $("#reg-name");
            if (!name.val()) {
                name.siblings(".reg_msg").text("请输入用户名");
                return;
            }
            $.ajax({
                url: '/Login/userNameIsExist',
                type: 'GET',
                async: true,    //是否异步
                data: {
                    userName: name.val(),
                    regType: regType
                },
                timeout: 5000,    //超时时间
                dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                success: function (data) {
                    if (data.status === 200) {
                        if (data.data.isExist === "true") {
                            name.siblings(".reg_msg").text("用户名被占用");
                        } else {
                            name.siblings(".reg_msg").text("");
                        }
                    }
                },
                error: function () {
                    swal('错误', '服务器维护中!', 'error');
                }
            })
        }

        //点击提交按钮显示load层，执行操作
        $("#commit").click(function () {
            var name = $("#reg-name");
            var password = $("#reg-password");
            var rePassword = $("#reg-rePassword");
            var eeNo = $("#reg-eeNo");
            var realName = $("#reg-realName");
            var sex = $("input[name='reg-sex']:checked");
            var phone = $("#reg-phone");
            var email = $("#reg-email");
            $(".reg_msg").text("");
            if (!name.val()) {
                name.siblings(".reg_msg").text("请输入用户名");
                return;
            }
            if (!password.val()) {
                password.siblings(".reg_msg").text("请输入登录密码");
                return;
            } else if (!rePassword.val()) {
                rePassword.siblings(".reg_msg").text("请再次输入密码");
                return;
            } else if (password.val() !== rePassword.val()) {
                rePassword.siblings(".reg_msg").text("两次输入的密码不一致");
                return;
            }
            if (!realName.val()) {
                realName.siblings(".reg_msg").text("请输入真实姓名");
                return;
            }
            if (!sex.val()) {
                $("#reg_sex_msg").html("<span style=\"color: red\">请选择性别</span>");
                return;
            }
            if (!phone.val()) {
                phone.siblings(".reg_msg").text("请输入联系电话");
                return;
            }

            $("#register-form").hide();
            load.show();

            var targetUrl;
            if (regType === "1") {
                targetUrl = '/Login/erRegister';
            } else {
                targetUrl = '/Login/eeRegister';
            }

            $.ajax({
                url: targetUrl,
                type: 'POST',
                async: false,    //是否异步
                data: {
                    loginName: name.val(),
                    password: password.val(),
                    eeNo: eeNo.val(),
                    realName: realName.val(),
                    sex: sex.val(),
                    phone: phone.val(),
                    email: email.val()
                },
                timeout: 5000,    //超时时间
                dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                success: function (data) {
                    if (data.status === 201) {
                        $.cookie('online_token', data.data.token);
                        $.cookie('isReg', "1");
                        toMainMenu();
                    } else if (data.status === 400) {
                        swal('注册失败', '请重试或联系管理员!', 'error');
                        $("#register-form").show();
                    } else if (data.status === 301) {
                        $("#register-form").show();
                        userNameIsExist();
                    } else {
                        swal('错误', '服务器维护中!', 'error');
                    }
                },
                error: function () {
                    swal('错误', '服务器维护中!', 'error');
                },
                complete: function () {
                    load.hide();
                }
            })
        });

        //点击取消按钮去除隐藏层和弹出层
        $("#cancel").click(function () {
            cancelRegister();
        });

        //去除隐藏层
        function cancelRegister() {
            $("#mask").hide();
            $("#register-form").hide();
        }

        //跳转到主页面
        function toMainMenu() {
            location.href = "view/mainMenu.jsp";
        }
    };
</script>
</html>
