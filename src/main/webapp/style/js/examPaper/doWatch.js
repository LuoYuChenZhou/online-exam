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
    $.get("/Papers/getPaperQuestionInfoByIdAfterExam.do"
        , {
            token: token
            , paperId: curPaperId
        }
        , function (data) {
            if (data.status === 200) {
                let infoObj = data.data;

                $("#paperName").text(infoObj.paperName);
                $("#fullScore").text(infoObj.fullScore);
                if (!fieldIsWrong(infoObj.score)) {
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
                                "                        <label class='layui-form-label question_text'>得分:</label>" +
                                "                        <label class='layui-form-label scoreAndLegend' style='text-align: left;margin-left: 0;'>" + getQaScore(infoObj, curQa.questionId) + "</label>" +
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
                                "                        <div class='question_text divLabel'>正确答案：" + tranAnswer1ToLetter(curQa.answer) + "</div>" +
                                "                        <div class='question_text divLabel'>你的答案：" + tranAnswer2ToLetter(infoObj, curQa.questionId) + "</div>" +
                                "                        <div class='question_text divLabel'>问题解析：" + reEmptyStrIfNull(curQa.questionAnanlyze) + "</div>" +
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
                                "                        <label class='layui-form-label question_text'>得分:</label>" +
                                "                        <label class='layui-form-label scoreAndLegend' style='text-align: left;margin-left: 0;'>" + getQaScore(infoObj, curQa.questionId) + "</label>" +
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
                                "                    <div class='layui-form-item separate'>" +
                                "                        <div class='question_text divLabel'>正确答案：" + tran1NormalAnswer(curQa.answer) + "</div>" +
                                "                        <div class='question_text divLabel'>你的答案：" + tran2NormalAnswer(infoObj, curQa.questionId) + "</div>" +
                                "                        <div class='question_text divLabel'>问题解析：" + reEmptyStrIfNull(curQa.questionAnanlyze) + "</div>" +
                                "                    </div>" +
                                "            </div>" +
                                "        </div>";

                            $("#qaShow").append(appendStr);
                        }
                        $("#qaShow").append("<br/><br/><br/>");
                        form.render();
                    }
                }
            } else {
                layer.msg(data.msg);
                setTimeout("backToList()", 1500);
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
            optionStr += "<input type='text' name='qaBlank" + qaIndex + j + "' style='width:40px;' disabled class='layui-input' lay-verify='required'>";
            preNum = endNum + blankStr.length;
            j++;
        }
    }
    optionStr += questionDesc.substring(preNum);
    return optionStr;
}

// 根据选择题答案，转义成字母
function tranAnswer1ToLetter(answer) {
    if (fieldIsWrong(answer)) {
        return "";
    }
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

// 根据选择题答案，转义成字母
function tranAnswer2ToLetter(obj, qaId) {
    if (fieldIsWrong(obj.EeAnswer)) {
        return "";
    }
    let numAnswer = parseInt(obj.EeAnswer[qaId], 10);
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

// 获取题目分数
function getQaScore(obj, qaId) {
    if (fieldIsWrong(obj.scoreDetail) || fieldIsWrong(obj.scoreDetail[qaId])) {
        return "待批改";
    }
    return obj.scoreDetail[qaId];
}

// 将$$转化为、
function tran1NormalAnswer(answer) {
    if (fieldIsWrong(answer)) {
        return "";
    }
    return answer.substring(0, answer.length - 2).replace(/\$\$/g, "、");
}

// 将$$转化为、
function tran2NormalAnswer(obj, qaId) {
    if (fieldIsWrong(obj.EeAnswer) || fieldIsWrong(obj.EeAnswer[qaId])) {
        return "";
    }
    return obj.EeAnswer[qaId].substring(0, obj.EeAnswer[qaId].length - 2).replace(/\$\$/g, "、");
}

// 返回列表页
function backToList() {
    window.parent.changeView('examPaper/exam-paper-list.html');
}