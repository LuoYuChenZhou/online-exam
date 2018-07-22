let amq = org.activemq.Amq;
let myDestination = 'topic://online.exam.topic';
/* var myDestination = 'channel://chat1'; */

let msgHide = true;
let newMsgShow, messageArea;
window.onload = function () {
    newMsgShow = $("#newMsgShow");
    messageArea = $("#messageArea");
}

function showOrHideMsgDiv() {
    if (msgHide) {
        newMsgShow.hide();
        $("#triangleDiv").show();
        messageArea.show();
        msgHide = false;
    } else {
        $("#triangleDiv").hide();
        messageArea.hide();
        msgHide = true;
    }
}

amq.init({
    uri: '../../amq', //AjaxServlet所配置对应的URL
    logging: true, //激活日志记录
    timeout: 45, //保持连接时长，单位为秒
    clientId: (new Date()).getTime().toString() //防止多个浏览器窗口标签共享同一个JSESSIONID
});

let myHandler = {
        rcvMessage: function (message) {
            try {
                let obj = eval('(' + message.data + ')');
                let erIdList = $.cookie("erIdList").split(",");
                let userId = $.cookie("online_cur_userId");
                switch (obj.operate) {
                    case "info":
                        if (erIdList.indexOf(obj.userId) > -1) {
                            addMsgToDiv(obj.message);
                        }
                        break;
                    case "add":
                        if (userId === obj.eeId) {
                            addMsgToDiv(obj.message);
                            if (erIdList.indexOf(obj.erId) > -1) {
                                erIdList.push(obj.erId);
                                $.cookie('erIdList', erIdList, {path: "/"});
                            }
                        }
                        break;
                    case "remove":
                        if (userId === obj.eeId) {
                            addMsgToDiv(obj.message);
                            erIdList = deleteAllSameEleFromArray(obj.erId, erIdList);
                            $.cookie('erIdList', erIdList, {path: "/"});
                        }
                }
            } catch (e) {
                // 不解析非json格式的信息
            }
        }
    }
;

amq.addListener('any', myDestination, myHandler.rcvMessage);

function addMsgToDiv(msg) {
    messageArea.html("·>  " + msg + "<br/>" + messageArea.html());
    if (msgHide) {
        newMsgShow.show();
    }
}
