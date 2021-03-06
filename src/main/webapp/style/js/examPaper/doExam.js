// 填空题换行问题未解决
let countOfQuestion;// 题目数量
let qaIndexType = new Map();// 题号和题目类型对应的map
let qaIndexIdMap = new Map();// 题号和问题id对应的map
let blankNumIndexMap = new Map();// 填空题题号和有多少个空位对应的map

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
            , paperId: $.cookie("curDoExamPaperId")
        }
        , function (data) {
            if (data.status === 200) {
                let infoObj = data.data;

                $("#paperName").text(infoObj.paperName);

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
                                "                        <label class='layui-form-label question_text'>答案：</label>" +
                                "                        <div class='layui-input-block'>";

                            for (let j = 0; j < optionStrArray.length; j++) {
                                let curLetter = String.fromCharCode(j + 65);
                                appendStr = appendStr +
                                    "<input type='checkbox' name='qaChoose" + i + "An' title='" + curLetter + "' value=" + getLetterValue(curLetter) + ">";
                            }

                            appendStr = appendStr + "</div></div></div></div></div>";
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

                refreshTimeLeft();
                setInterval("refreshTimeLeft()", 20000);
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

// 根据字母，获取十进制值
function getLetterValue(sourceLetter) {
    let sourceValue = sourceLetter.charCodeAt();
    let tempStr = "1";
    for (let i = 0; i < sourceValue - 65; i++) {
        tempStr = tempStr + "0";
    }
    return parseInt(tempStr, 2);
}

// 剩余时间刷新
function refreshTimeLeft() {
    $.get("/Score/getTimeLeft.do",
        {
            paperId: $.cookie("curDoExamPaperId")
            , token: token
        },
        function (data) {
            if (data.status === 200) {
                $("#timeLeft").text(data.data.timeLeft);
            } else if (data.status === 103) {
                $("#timeLeft").text("<1");
                let timeLeft = parseInt(data.data.timeLeft, 10);
                if (timeLeft > 0) {
                    setTimeout("answerCommit(null)", timeLeft * 1000);
                } else {
                    answerCommit(null);
                }
            }
        }
    );
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

//试卷提交
function answerCommit(answerInfo) {
    if (fieldIsWrong(answerInfo)) {
        answerInfo = getAnswer()[1];
    }
    $.post("/Score/saveAnswer.do",
        {
            paperId: $.cookie("curDoExamPaperId")
            , answerInfo: strMapToJson(answerInfo)
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                layer.msg(data.msg);
                $.post("/Score/autoCorrect.do", {scoreId: data.data.scoreId, token: token},
                    function (data) {
                        window.parent.changeView("examPaper/exam-paper-list.html");
                    });
            } else {
                layer.msg(data.msg);
            }
        }
    );
}

// 获取答案
function getAnswer() {
    let noInputQaIndex = "";//未填写的题目的题号以顿号隔开的字符串
    let answerMap = new Map();
    for (let i = 1; i <= countOfQuestion; i++) {
        let qaType = qaIndexType.get("qa" + i);
        if (!fieldIsWrong(qaType)) {
            if (qaType === "0") {
                let curAnswerCount = 0;// 选择题答案的和
                $("input[name='qaChoose" + i + "An']:checked").each(function () {
                    //由于复选框一般选中的是多个,所以可以循环输出
                    curAnswerCount += parseInt($(this).val(), 10);
                });
                if (curAnswerCount === 0) {
                    noInputQaIndex += i + "、";
                }
                answerMap.set(qaIndexIdMap.get("qa" + i), curAnswerCount);
            } else if (qaType === "1") {
                let answerStr = "";
                let allInput = true;// 本题是否填完
                for (let j = 0; j < blankNumIndexMap.get("qa" + i); j++) {
                    let curAn = $("input[name='qaBlank" + i + j + "']").val();
                    if (fieldIsWrong(curAn)) {
                        answerStr += "$$";
                        allInput = false;
                    } else {
                        answerStr += curAn + "$$";
                    }
                }
                if (allInput === false) {
                    noInputQaIndex += i + "、";
                }
                answerMap.set(qaIndexIdMap.get("qa" + i), answerStr);
            }
        }
    }
    if (!fieldIsWrong(noInputQaIndex)) {
        noInputQaIndex = noInputQaIndex.substring(0, noInputQaIndex.length - 1);
    }
    return [noInputQaIndex, answerMap];
}