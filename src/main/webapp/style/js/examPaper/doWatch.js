// 填空题换行问题未解决
let countOfQuestion;// 题目数量
let qaIndexType = new Map();// 题号和题目类型对应的map
let qaIndexIdMap = new Map();// 题号和问题id对应的map
let blankNumIndexMap = new Map();// 填空题题号和有多少个空位对应的map
let curPaperId = $.cookie("curWatchExamPaperId");

let form;
layui.use('form', function () {
    form = layui.form;
});

let layer;
layui.use('layer', function () {
    layer = layui.layer;
});

$(document).ready(function () {
    loadPaperInfo();
});

// 初始化页面数据
function loadPaperInfo() {
    if (fieldIsWrong(form)) {
        setTimeout("loadPaperInfo()", 100);
        return;
    }
    $.get("/Papers/getPaperQuestionSimpleInfoById.do"
        , {
            token: token
            , paperId: curPaperId
        }
        , function (data) {
            if (data.status === 200) {
                let infoObj = data.data;

                $("#paperName").text(infoObj.paperName);
                $("#fullScore").text(infoObj.fullScore);
                if (fieldIsWrong(infoObj.score)) {
                    $("#getScore").text(infoObj.score);
                } else {
                    $("#getScore").text("待批改");
                }

                if (!fieldIsWrong(infoObj.questionList)) {
                    countOfQuestion = infoObj.questionList.length;
                    for (let i = 1; i <= countOfQuestion; i++) {
                        let curQa = infoObj.questionList[i - 1];
                        qaIndexIdMap.set("qa" + i, curQa.questionId);
                        qaIndexType.set("qa" + i, curQa.questionType);
                        if (curQa.questionType === "0") {
                            let appendStr = "<div class='qaBg'>" +
                                "            <span class='leftIndex'>" + i + "</span>" +
                                "            <div class='optionDiv'>" +
                                "                <div class='separate'>" +
                                "                    <div class='layui-form-item layui-form-text'>" +
                                "                        <label class='layui-form-label question_text'>分数:</label>" +
                                "                        <label class='layui-form-label scoreAndLegend'>" + getChooseScoreExplain(curQa.fullScore, curQa.scoreType, curQa.assignScore, curQa.isMulti) + "</label>" +
                                "                    </div>" +
                                "                </div>" +
                                "                <div class='separate'>" +
                                "                    <div class='layui-form-item layui-form-text'>" +
                                "                        <label class='layui-form-label question_text'>问题描述:</label>" +
                                "                        <div class='layui-input-block'>" +
                                "                            <textarea name='questionDesc1' class='layui-textarea question_desc'" +
                                "                                      disabled>" + curQa.questionDesc + "</textarea>" +
                                "                        </div>" +
                                "                    </div>" +
                                "                </div>" +
                                "                <div class='chooseEdit1'>" +
                                "                    <div class='separate'>" +
                                "                        <div class='chooseLeftText'>" +
                                "                            <label class='layui-form-label question_text' style='padding-right: 0;'>选项：</label>" +
                                "                        </div>" +
                                "                        <div class='chooseCenterOptions'>" +
                                "                            <div class='layui-form-item' style='height: auto'>";

                            let optionStrArray = curQa.options.substring(0, curQa.options.length - 2).split("$$");
                            for (let j = 0; j < optionStrArray.length; j++) {
                                let curLetter = String.fromCharCode(j + 65);
                                appendStr = appendStr + "" +
                                    "<label class='layui-form-label chooseOption'>" + curLetter + "</label>" +
                                    "<div class='layui-input-block smallML'>" +
                                    "      <input type='text' class='layui-input' disabled value='" + optionStrArray[j] + "'>" +
                                    "</div>";
                            }

                            appendStr = appendStr + "</div>" +
                                "                        </div>" +
                                "                    </div>" +
                                "                    <div class='layui-form-item separate'>" +
                                "                        <label class='layui-form-label question_text'>正确答案：</label>" +
                                "                        <div class='layui-input-block'>" + tranAnswerToLetter(curQa.answer)
                                "                        </div>" +
                                "                    </div>" +
                                "                </div>" +
                                "            </div>" +
                                "        </div>";
                            $("#qaShow").append(appendStr);
                        } else if (curQa.questionType === "1") {
                            let appendStr = "<div class='qaBg'>" +
                                "            <span class='leftIndex'>" + i + "</span>" +
                                "            <div class='optionDiv'>" +
                                "                <div class='separate'>" +
                                "                    <div class='layui-form-item layui-form-text'>" +
                                "                        <label class='layui-form-label question_text'>分数:</label>" +
                                "                        <label class='layui-form-label scoreAndLegend'>" + getBlankScoreExplain(curQa.questionScore, curQa.fullScore) + "</label>" +
                                "                    </div>" +
                                "                </div>" +
                                "                <div class='separate'>" +
                                "                    <div class='layui-form-item layui-form-text qaBlank'>" +
                                "                        <label class='layui-form-label question_text'>问题描述:</label>" +
                                "                        <div class='layui-input-block blankDesc'>" +
                                getInputBlankDesc(curQa.questionDesc, i, curQa.blankIndex) +
                                "                       </div>" +
                                "                    </div>" +
                                "                </div>" +
                                "            </div>" +
                                "        </div>";

                            $("#qaShow").append(appendStr);
                        }
                        form.render();
                    }
                }
            } else {
                layer.msg(data.msg);
            }
        });
}

// 解析选择题得分描述
function getChooseScoreExplain(fullScore, scoreType, assignScore, isMulti) {
    if (isMulti === '1') {
        if (scoreType === '1') {
            return fullScore + "分，选对但不全不得分";
        } else {
            return fullScore + "分，选对但不全得" + assignScore + "分";
        }
    } else {
        return fullScore + "分(单选)";
    }
}

// 解析填空题得分描述
function getBlankScoreExplain(questionScore, fullScore) {
    let qSList = questionScore.substring(0, questionScore.length - 1).split(",");
    if (qSList.length < 2) {
        return fullScore + "分";
    } else {
        let explain = fullScore + "分(";
        for (let i = 0; i < qSList.length; i++) {
            explain += qSList[i] + "+";
        }
        explain = explain.substring(0, explain.length - 1);
        explain += ")";
        return explain;
    }
}

// 解析填空题问题描述
function getInputBlankDesc(questionDesc, qaIndex, blankIndex) {
    let optionIndex = blankIndex.substring(0, blankIndex.length - 1).split(",");
    blankNumIndexMap.set("qa" + qaIndex, optionIndex.length);
    let optionStr = "", preNum = 0, j = 0;
    for (let i = 1; i <= optionIndex.length; i++) {
        let blankStr = "[$" + optionIndex[i - 1] + "$]";
        let endNum = questionDesc.indexOf(blankStr);
        if (endNum >= 0) {
            optionStr += questionDesc.substring(preNum, endNum);
            optionStr += "<input type='text' name='qaBlank" + qaIndex + j + "' class='layui-input' lay-verify='required'>";
            preNum = endNum + blankStr.length;
            j++;
        }
    }
    optionStr += questionDesc.substring(preNum);
    return optionStr;
}

// 提示是否提交
function promptCommit() {
    let answerInfo = getAnswer();
    let promptStr = "";
    if (fieldIsWrong(answerInfo[0])) {
        promptStr = "确认提交？";
    } else {
        promptStr = "第" + answerInfo[0] + "未填写，确认提交？";
    }

    let FloorObject = layer.confirm(promptStr, {
        btn: ['确定', '取消'] //按钮
    }, function () {
        answerCommit(answerInfo[1]);
    }, function () {
        closeFloor(FloorObject);
    });
}

// 根据选择题答案，转义成字母
function tranAnswerToLetter(answer) {
    let numAnswer = parseInt(answer, 10);
    let twoAnswer = numAnswer.toString(2);
    let letterArray = "";
    for (let i = 0; i < twoAnswer.length; i++) {
        let compareNum = "1";
        for (let j = 0; j < i; j++) {
            compareNum += "0";
        }
        if ((numAnswer & parseInt(compareNum, 2)) > 0) {
            letterArray += String.fromCharCode(65 + i) + "、";
        }
    }
    if (!fieldIsWrong(letterArray)) {
        letterArray = letterArray.substring(0, letterArray.length - 1);
    }
    return letterArray;
}