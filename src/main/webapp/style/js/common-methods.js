const curWwwPath = window.document.location.href;
const pathName = window.document.location.pathname;
const localhostPath = curWwwPath.substring(0, curWwwPath.indexOf(pathName));//当前主机名

let token = $.cookie('online_token') ? $.cookie('online_token') : "";//token
let user_name;
let user_type;

//控制主页面的遮罩层,只适用于子页面
function controlMainMask(num) {
    if (num === 1) {
        $("#mask", window.parent.document).show();
        $("#preloader_4", window.parent.document).show();
    } else {
        $("#mask", window.parent.document).hide();
        $("#preloader_4", window.parent.document).hide();
    }
}

/**
 * 校验值是否为空
 * values : 要校验的值的数组
 * mids: 用于显示的元素的id的数组
 * msgs:提示信息数组
 */
function validNull(values, mids, msgs) {
    for (let i = 0; i < values.length; i++) {
        if (!values[i]) {
            $("#" + mids[i]).html("<span style='color:red;''>" + msgs[i] + "</span>");
            return false;
        } else {
            $("#" + mids[i]).html("");
        }
    }
    return true;
}

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
                let layer = layui.layer;
                layer.msg('服务器维护中。。', {icon: 5});
            }
            let obj = eval('(' + data.responseText + ')');
            if (obj.status === 1000) {
                layer.msg('服务器维护中。。', {icon: 5});
            } else if (obj.status === 401) {
                location.href = localhostPath;
            }
        }
});

/**
 * 如果字符串过长，显示...
 * @param sourceMsg 源字符串
 * @param size 最大长度
 */
function getLessMsg(sourceMsg, size) {
    if (sourceMsg.length > size) {
        return sourceMsg.substring(0, size - 3) + "...";
    } else {
        return sourceMsg;
    }
}

/**
 * unix时间戳转化日期
 * @param type yMdHms,这里只控制显示什么，格式不能改变
 * @param timeStamp 时间戳
 * @returns {*}
 */
function dtf(type, timeStamp) {
    if (!timeStamp) {
        return "";
    }
    let date = new Date(parseInt(timeStamp) * 1000);

    let year = date.getFullYear();
    let month = add0(date.getMonth() + 1);
    let day = add0(date.getDate());
    let hour = add0(date.getHours());
    let minute = add0(date.getMinutes());
    let second = add0(date.getSeconds());

    let re;
    if (type.indexOf("y") >= 0) {
        re = year;
    }
    if (type.indexOf("M") >= 0) {
        re = re + "-" + month;
    }
    if (type.indexOf("d") >= 0) {
        re = re + "-" + day;
    }
    if (type.indexOf("H") >= 0) {
        re = re + " " + hour;
    }
    if (type.indexOf("m") >= 0) {
        re = re + ":" + minute;
    }
    if (type.indexOf("s") >= 0) {
        re = re + ":" + second;
    }

    return re;
}

/**
 * 补齐位数
 * @param source 源目标
 * @param size 位数
 * @returns {string|*}
 */
function add0(source, size) {
    size = !size ? 2 : size;
    source = "" + source;
    let l = source.length;
    for (let i = l; i < size; i++) {
        source = "0" + source;
    }
    return source;
}

/**
 * 获取表单的所有值，要求取值字段有name属性
 * @param form 选择器（如："#form"）
 * @returns {{}}
 */
function getEntity(form) {
    let result = {};
    $(form).find("[name]").each(function () {
        let field = $(this).attr("name");
        let val;

        if ($(this).attr('type') === 'checkbox') {
            val = $(this).prop('checked');
        } else if ($(this).attr('type') === 'radio') {
            val = $(this).prop('checked');
        } else {
            val = $(this).val();
        }
        // 获取单个属性的值,并扩展到result对象里面
        getField(field.split('.'), val, result);
    });
    return result;
}

function getField(fieldNames, value, result) {
    if (fieldNames.length > 1) {
        for (let i = 0; i < fieldNames.length - 1; i++) {
            if (result[fieldNames[i]] === undefined) {
                result[fieldNames[i]] = {}
            }
            result = result[fieldNames[i]];
        }
        result[fieldNames[fieldNames.length - 1]] = value;
    } else {
        result[fieldNames[0]] = value;
    }
}

/**
 * 表单赋值方法，要求字段有name属性（未测试）
 * @param form
 * @param entity
 */
function setEntity(form, entity) {
    $(form).find("[name]").each(function () {
        let field = $(this).attr("name");
        fieldNames = field.split('.');
        let value = JSON.parse(JSON.stringify(entity));
        for (let index = 0; index < fieldNames.length; index++) {
            value = value[fieldNames[index]];
            if (!value) {
                break;
            }
        }
        if ($(this).attr("type") === "checkbox" ||
            $(this).attr("type") === "radio") {
            $(this).attr('checked', Boolean(value));
        } else {
            if (value) {
                $(this).val(value);
            } else {
                $(this).val("");
            }
        }
    })
}

/**
 * 性别数字转文字
 */
function num2Sex(sexNum) {
    if(!sexNum)
        return "";
    switch (sexNum) {
        case "0":
            return "男";
        case "1":
            return "女";
    }
}

//关闭弹出层
function closeFloor(floorName) {
    layer.close(floorName);
}