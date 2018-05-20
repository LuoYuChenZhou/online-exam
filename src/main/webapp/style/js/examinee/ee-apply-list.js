let leftCanPress = true;//向左按钮是否可以点击
let rightCanPress = true;//向右按钮是否可以点击

let page = 1;
let limit = 9;

let layer;
layui.use('layer', function () {
    layer = layui.layer;
});

getInvitedList();
$(document).ready(function () {
//左边按钮点击
    $("#left_btn").click(function () {
        if (leftCanPress === true) {
            page = page - 1;
            getInvitedList();
        }
    });

//右边按钮点击
    $("#right_btn").click(function () {
        if (rightCanPress === true) {
            page = page + 1;
            getInvitedList();
        }
    });
});

//设置按钮的背景颜色
function btnClassChange() {
    if (leftCanPress === true) {
        $("#left_btn").removeClass("canNotPress");
    } else {
        $("#left_btn").addClass("canNotPress");
    }
    if (rightCanPress === true) {
        $("#right_btn").removeClass("canNotPress");
    } else {
        $("#right_btn").addClass("canNotPress");
    }
}

//加载申请列表
function getInvitedList() {
    $.get("/ErEe/getApplyList.do",
        {
            page: page
            , limit: limit
            , token: token
        },
        function (data) {
            if (data.status === 200) {
                rightCanPress = data.data.hasNextPage;
                leftCanPress = data.data.hasPreviousPage;
                btnClassChange();
                let showStr = "";
                let sourceList = data.data.list;
                //当前索引值对3的余数，
                // 余数为0加上show_bak_line的开标签，为2加上show_bak_line的闭标签，如果结束循环时余数不是2，则补上一个闭标签
                let yu;
                for (let i = 0; i < sourceList.length; i++) {
                    yu = i % 3;
                    if (yu === 0) {
                        showStr = showStr + "<div class=\"show_bak_line\">";
                    }

                    showStr = showStr + "<div class=\"invited_bak\">" +
                        "<div class=\"invited_user\">申请人：" + sourceList[i].erName + "</div>" +
                        "<div class=\"invited_extraMsg\">" + sourceList[i].extraMsg + "</div>" +
                        "<div class=\"button_group\">" +
                        "<a class=\"accept\" href='javascript:void(0)' onclick='acceptInvite(\"" + sourceList[i].erEeId + "\")'>接受邀请</a>" +
                        "<a class=\"refuse\" href='javascript:void(0)' onclick='refuseInvite(\"" + sourceList[i].erEeId + "\")'>拒绝</a>" +
                        "</div>" +
                        "</div>";

                    if (yu === 2) {
                        showStr = showStr + "</div>";
                    }
                }
                if (yu !== 2) {
                    showStr = showStr + "</div>";
                }
                $("#show_bak").html(showStr);
            } else if (data.status === 204) {
                leftCanPress = false;
                rightCanPress = false;
                btnClassChange();
                $("#show_bak").html("<span class=\"noInvite\">暂无申请</span>");
            }
        });
}

//接受邀请
function acceptInvite(erEeId) {
    $.post("/ErEe/acceptEeEr.do",
        {
            erEeId: erEeId
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                layer.msg(data.msg, {icon: 1, offset: ['100px']});
            } else {
                layer.msg(data.msg, {offset: ['100px']});
            }
            getInvitedList();
        });
}

//拒绝邀请
function refuseInvite(erEeId) {
    let floor = layer.confirm('确认拒绝？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post("/ErEe/removeEeEr.do",
            {
                erEeId: erEeId
                , operate: "eeRefuse"
                , token: token
            },
            function (data) {
                if (data.status === 201) {
                    layer.msg(data.msg, {icon: 1, offset: ['100px']});
                } else {
                    layer.msg(data.msg, {offset: ['100px']});
                }
                getInvitedList();
            });
    }, function () {
        layer.close(floor);
    });
}