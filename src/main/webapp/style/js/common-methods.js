const curWwwPath = window.document.location.href;
const pathName = window.document.location.pathname;
const localhostPath = curWwwPath.substring(0, curWwwPath.indexOf(pathName));//当前主机名

let token = $.cookie('online_token');//token
let user_name;
let user_type;

//获取用户类别
function getUserName() {
    $.get("/Login/getUserInfoByType.do", {searchType: "name", token: token}, function (data) {
        if (data.status === 200) {
            user_name = data.data.returnData;
        } else if (data.status === 401) {
            location.href = localhostPath + "/index.html";
        }
    });
}

//获取用户类别
function getUserType() {
    $.get("/Login/getUserInfoByType.do", {searchType: "type", token: token}, function (data) {
        if (data.status === 200) {
            user_type = data.data.returnData;
        } else if (data.status === 401) {
            location.href = localhostPath + "/index.html";
        }
    });
}