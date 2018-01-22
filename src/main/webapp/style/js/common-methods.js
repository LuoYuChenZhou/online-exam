const curWwwPath = window.document.location.href;
const pathName = window.document.location.pathname;
const localhostPath = curWwwPath.substring(0, curWwwPath.indexOf(pathName));//当前主机名

let token = $.cookie('online_token') ? $.cookie('online_token') : "";//token
let user_name;
let user_type;

//获取用户姓名
function getUserName() {
    $.get("/Login/getUserInfoByType.do", {searchType: "name", token: token}, function (data) {
        if (data.status === 200) {
            user_name = data.data.returnData;
        }
    });
}

//获取用户类别
function getUserType() {
    $.get("/Login/getUserInfoByType.do", {searchType: "type", token: token}, function (data) {
        if (data.status === 200) {
            user_type = data.data.returnData;
        }
    });
}

//设置ajax通用处理（如果自定义了complete，本方法会被替换掉）
$.ajaxSetup({
    //设置ajax请求结束后的执行动作
    complete:
        function (data, status) {
            if (status === 1000) {
                swal('服务器维护中。。。', 'error');
            }
            let obj = eval('(' + data.responseText + ')');
            if (obj.status === 1000) {
                swal('服务器维护中。。。', 'error');
            } else if (obj.status === 401) {
                location.href = localhostPath;
            }
        }
});