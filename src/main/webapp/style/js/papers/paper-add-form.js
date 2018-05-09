// 本来设计是可以切换问题类型，由于填空题空数问题，取消了，但页面没有重写，有很大优化空间
// bug:多选批改模式设置默认值有效，但不会显示
// 暂时放弃编辑试卷时将试题放入题库，如果后面要加需要在问题div中加入复选框
// 放弃填空题模糊批改功能

let nextDivIndex = 1;//下一个div的下标
let correctMap = new Map();//所有批改类型的Map
let questionTypeMap = new Map();//所有问题类型的Map
let curChooseIndexMap = new Map();// 选择题区域的最后一个字母，键为qaChoose+index值为A、B、C...
let latestBlankIndexMap = new Map();// 填空题区域的最大数字+1，只增不减
let subjectIsLoad = false;// 科目列表是否获取
let subjectOptions = ""; // 科目列表选项
let FloorObject;// 弹出层对象

let layer;
layui.use('layer', function () {
    layer = layui.layer;
});

let form;
layui.use('form', function () {
    form = layui.form;

    //设置默认科目后，新增的问题的科目设为默认科目
    form.on('select(defaultSubject)', function (data) {
        subjectOptions = subjectOptions.replace(/selected /g, '');
        if (subjectOptions.indexOf(data.value) > 0) {
            let preS = subjectOptions.substring(0, subjectOptions.indexOf(data.value) - 7);
            let sufS = "selected " + subjectOptions.substring(subjectOptions.indexOf(data.value) - 7);
            subjectOptions = preS + sufS;
        }
    });
});

$(document).ready(function () {
    getSubjectList();
    setDefaultSubjectList();// 默认科目下拉刷新
});

// 创建一个div并完善相关步骤
function addQuestionDiv(type) {
    $("#addButtonGroup").hide();
    if (typeof(form) === "undefined" || form === null || subjectIsLoad === false) {
        setTimeout("addQuestionDiv()", 500);
        return;
    }
    onlyAddDiv();

    correctMap.set("correct" + nextDivIndex, "1");// 默认批改模式是自动
    questionTypeMap.set("qType" + nextDivIndex, type);
    curChooseIndexMap.set("qaChoose" + nextDivIndex, "D");
    latestBlankIndexMap.set("qaBlank" + nextDivIndex, "1");
    typeRadioAddListen(nextDivIndex);// 添加单选监听
    $("input[name='qType" + nextDivIndex + "'][value=" + type + "]").prop("checked", true).siblings("input").prop("checked", false);
    let choose = $(".chooseEdit" + nextDivIndex);
    let blank = $(".blankEdit" + nextDivIndex);
    let correct = $("input[name='correct" + nextDivIndex + "']");
    switch (type) {
        // 0是选择，1是填空，2是简答
        case "0":
            choose.show();
            blank.hide();
            correct.attr("disabled", false);
            break;
        case "1":
            if (correctMap.get("correct" + nextDivIndex) === "1") {
                choose.hide();
                blank.show();
            }
            correct.attr("disabled", false);
            $("input[name='qa" + nextDivIndex + "Score']").addClass("inputDisable").attr("disabled", true);
            break;
        case "2":
            choose.hide();
            blank.hide();

            let hand = $("input[name='correct" + nextDivIndex + "'][value='0']");
            let auto = $("input[name='correct" + nextDivIndex + "'][value='1']");

            hand.prop("checked", true);
            auto.prop("checked", false);
            correct.attr("disabled", true);
            correctMap.set("correct" + nextDivIndex, "0");
    }
    $("input[name='qType" + nextDivIndex + "']").attr("disabled", true);
    form.render();
    nextDivIndex = nextDivIndex + 1;
}

// 题目类型按钮添加监听
function typeRadioAddListen(index) {
    if (typeof(form) === "undefined" || form === null) {
        setTimeout("typeRadioAddListen(" + index + ")", 200);
        return;
    }

    form.on('radio(correct' + index + ')', function (data) {
        let choose = $(".chooseEdit" + index);
        let blank = $(".blankEdit" + index);

        correctMap.set("correct" + index, data.value);
        if (data.value === "1") {
            switch (questionTypeMap.get("qType" + index)) {
                // 0是选择，1是填空，2是简答
                case "0":
                    choose.show();
                    blank.hide();
                    break;
                case "1":
                    choose.hide();
                    blank.show();
                    break;
                case "2":
                    choose.hide();
                    blank.hide();
                default:
                    choose.hide();
                    blank.hide();
            }
        } else {
            if (questionTypeMap.get("qType" + index) === "0") {
                choose.show();
                blank.hide();
            } else {
                choose.hide();
                blank.hide();
            }
        }

    });
}

// 删除指定问题div
function deleteQuestion(index) {
    $("#qa" + index).remove();
    $("#questionIndex" + index).remove();
}

// 删除指定的填空题的指定项
function deleteOption(index, optionIndex) {
    $(".qaBlankOneBlank" + index + optionIndex).remove();
    let desc = $("#questionDesc" + index);
    desc.val(desc.val().replace("[$" + optionIndex + "$]", ""));
    autoInputCountScore(index);
}

// 在指定填空题内添加空位
function addNewOption(index) {
    // 添加输入框
    let newBlankIndex = latestBlankIndexMap.get("qaBlank" + index);
    $(".qaBlankDiv" + index).append("" +
        "<div class='qaBlankOneBlank" + index + newBlankIndex + "'>" +
        "<div class='layui-inline' style='margin-left: 7px;width: 28px;'>" +
        "    <label class='layui-form-label smallText' style='font-size: 10px;'>[$" + newBlankIndex + "$]</label>" +
        "</div>" +
        "<div class='layui-inline' style='width: 410px;'>" +
        "    <div class='layui-input-inline smallML' style='margin-left: 7px;'>" +
        "        <input type='text' name='qaBlank" + index + newBlankIndex + "' autocomplete='off' class='layui-input' required" +
        "               lay-verify='required' style='width: 385px;' placeholder='请输入内容'>" +
        "    </div>" +
        "</div>" +
        "<div class='layui-inline' style='width: 144px;margin-right:0;'>" +
        "    <label class='layui-form-label smallText'>分数</label>" +
        "    <div class='layui-input-inline smallML'>" +
        "        <input type='text' lay-verify='required'  oninput='autoInputCountScore(" + index + ")' onporpertychange='autoInputCountScore(" + index + ")' name='qaBlank" + index + newBlankIndex + "Score' autocomplete='off' class='layui-input'" +
        "               style='width: 65px;'>" +
        "    </div>" +
        "</div>" +
        "<div class='layui-inline'>" +
        "    <a href='javascript:void(0);' onclick='deleteOption(" + index + "," + newBlankIndex + ")'>" +
        "        <svg class='icon' aria-hidden='true'>" +
        "            <use xlink:href='#icon-jianshao'></use>" +
        "        </svg>" +
        "    </a>" +
        "</div>" +
        "</div>");
    latestBlankIndexMap.set("qaBlank" + index, parseInt(newBlankIndex, 10) + 1);

    //问题描述部分添加标记
    let desc = $("#questionDesc" + index);
    desc.val(desc.val() + "[$" + newBlankIndex + "$]");
}

// 移除选择题最后一个选项
function removeLatestChooseOption(index) {
    let latestLetter = curChooseIndexMap.get("qaChoose" + index);
    if (latestLetter === "B") {
        layer.msg("选项不能少于两个！");
    } else if (latestLetter !== "NONE") {
        let allRemove = $(".qaChoose" + index + latestLetter);
        allRemove.next(".layui-form-checkbox").remove();
        allRemove.remove();
        getPreLetter(index);
    }
}

// 选择题新增一个选项
function addNewChooseOption(index) {
    let latestLetter = curChooseIndexMap.get("qaChoose" + index);
    if (latestLetter !== "Z") {
        getNextLetter(index);
        latestLetter = curChooseIndexMap.get("qaChoose" + index);
        let letterValue = getLetterValue(latestLetter);
        $(".chooseOptions" + index).append("" +
            "<label class='layui-form-label qaChoose" + index + latestLetter + "' style='width: auto;padding-left: 0;padding-right: 0'>" + latestLetter + "</label>" +
            "<div class='layui-input-block smallML qaChoose" + index + latestLetter + "'>" +
            "   <input type='text' name='qaChoose" + index + latestLetter + "'   placeholder='请输入内容'" +
            "         autocomplete='off' class='layui-input' style='width: 556px;'>" +
            "</div>");
        $(".qaChooseAn" + index).append("<input type='checkbox' class='qaChoose" + index + latestLetter + "' name='qaChoose" + index + "An' title='" + latestLetter + "' value=" + letterValue + ">");
        form.render("checkbox");
    }
}

// 获取下一个字母
function getNextLetter(index) {
    let sourceLetter = curChooseIndexMap.get("qaChoose" + index);
    if (sourceLetter === "Z") {
        //不做操作
    } else if (sourceLetter === "NONE") {
        curChooseIndexMap.set("qaChoose" + index, "A");
    } else {
        curChooseIndexMap.set("qaChoose" + index, String.fromCharCode(sourceLetter.charCodeAt() + 1));
    }
}

// 获取上一个字母
function getPreLetter(index) {
    let sourceLetter = curChooseIndexMap.get("qaChoose" + index);
    if (sourceLetter === "A") {
        curChooseIndexMap.set("qaChoose" + index, "NONE");
    } else if (sourceLetter === "NONE") {
        //不做操作
    } else {
        curChooseIndexMap.set("qaChoose" + index, String.fromCharCode(sourceLetter.charCodeAt() - 1));
    }
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

// 显示按钮组
function showButtonGroup() {
    $("#addButtonGroup").show();
}

// 关闭按钮组
function closeButtonGroup() {
    $("#addButtonGroup").hide();
}

// 提交试题
function paperCommit(type) {
    let jsonFormObject = getEntity("#basicForm");

    //试卷名称必填
    if (fieldIsWrong(jsonFormObject.paperName)) {
        layer.msg("试卷名称必填");
        return false;
    }

    // 数字问题必须解决
    if (!fieldIsWrong(jsonFormObject.examTimeNum) && !isNum(jsonFormObject.examTimeNum)) {
        layer.msg("考试时间必须是数字");
        return false;
    }

    // 如果是发布，需要校验，保存不用
    if (type === '2') {
        if (formItemValid(jsonFormObject) === false) {
            return;
        }
    }

    let allInfo = {
        papersName: jsonFormObject.paperName
        , defaultSubject: jsonFormObject.defaultSubject
        , examTime: jsonFormObject.examTimeNum
        , fullScore: $("input[name='paperFullScore']").val()
        , status: type
        , token: token
    };

    // 循环处理问题
    for (let i = 1; i < nextDivIndex; i++) {
        if (!fieldIsWrong(jsonFormObject["qa" + i + "CoScore"]) && !isNum(jsonFormObject["qa" + i + "CoScore"])) {
            layer.msg("第" + i + "题指定分数必须是数字");
            return false;
        }

        // i 表示当前处理的是第几题，index表示在数组中的位置
        let index = i - 1;

        // 加入通用的字段
        allInfo["paperQuestionList[" + index + "].correctType"] = jsonFormObject["correct" + i];
        allInfo["paperQuestionList[" + index + "].scoreType"] = jsonFormObject["qa" + i + "Co"];
        allInfo["paperQuestionList[" + index + "].assignScore"] = jsonFormObject["qa" + i + "CoScore"];
        allInfo["paperQuestionList[" + index + "].questionType"] = jsonFormObject["qType" + i];
        allInfo["paperQuestionList[" + index + "].fullScore"] = jsonFormObject["qa" + i + "Score"];

        allInfo["baseQuestionsList[" + index + "].questionDesc"] = jsonFormObject["questionDesc" + i];
        allInfo["baseQuestionsList[" + index + "].subject"] = jsonFormObject["subject" + i];
        allInfo["baseQuestionsList[" + index + "].questionType"] = jsonFormObject["qType" + i];
        allInfo["baseQuestionsList[" + index + "].status"] = "1";
        allInfo["baseQuestionsList[" + index + "].questionAnalyze"] = jsonFormObject["questionAnalyze" + i];

        // 根据题目类型不同，加入独有的字段
        let scoreObj = jsonFormObject["qa" + i + "Score"];
        if (!fieldIsWrong(scoreObj)) {
            if (!isNum(scoreObj)) {
                layer.msg("第" + i + "题分数必须是数字");
                return false;
            }
        }
        if (jsonFormObject["qType" + i] === '0') {
            let chooseStr = getChooseOptionsStr(jsonFormObject, i);
            if (chooseStr === null) {
                layer.msg("第" + i + "题获取选项出现错误");
                return false;
            } else if (chooseStr === "[have$$error]") {
                layer.msg("第" + i + "题选项中不允许有$$");
                return false;
            }

            allInfo["baseQuestionsList[" + index + "].options"] = chooseStr;
            allInfo["paperQuestionList[" + index + "].questionScore"] = scoreObj;

            // 选择题答案需要单独获取
            let curAnswerCount = 0;
            let trueAnswerNum = 0;
            $("input[name='qaChoose" + i + "An']:checked").each(function () {
                //由于复选框一般选中的是多个,所以可以循环输出
                curAnswerCount += parseInt($(this).val(), 10);
                trueAnswerNum++;
            });
            allInfo["baseQuestionsList[" + index + "].answer"] = curAnswerCount;
            allInfo["baseQuestionsList[" + index + "].isMulti"] = trueAnswerNum > 1 ? "1" : "0";
        } else if (jsonFormObject["qType" + i] === '1') {
            allInfo["baseQuestionsList[" + index + "].answer"] = getBlankOptionsStr(jsonFormObject, i);
            allInfo["baseQuestionsList[" + index + "].blankIndex"] = getBlankIndexStr(i);
            if (getBlankOptionsStr(jsonFormObject, i) === "[have$$error]") {
                layer.msg("第" + i + "题答案/要点中不允许有$$");
                return false;
            }
            allInfo["paperQuestionList[" + index + "].questionScore"] = getBlankOptionScoresStr(jsonFormObject, i);
        }
    }

    $.post("/Papers/addPaper.do", allInfo,
        function (data) {
            if (data.status === 201) {
                layer.msg("保存成功", {icon: 6, offset: ['100px']});
                closeFloor(FloorObject);
                window.parent.changeView("papers/paper-list.html");
            } else {
                layer.msg(data.msg);
            }
        });
}

// 获取index对应的选择题的所有选项以$$连接起来的字符串
function getChooseOptionsStr(jsonFormObject, index) {
    let endNum = curChooseIndexMap.get("qaChoose" + index).charCodeAt();
    let optionsStr = "";
    for (let i = 65; i <= endNum; i++) {
        let obj = jsonFormObject["qaChoose" + index + String.fromCharCode(i)];
        // 选择题是按顺序下去的，如果出问题则中断提交
        if (typeof(obj) === "undefined" || obj === null) {
            return null;
        }
        if (obj.indexOf("$$") >= 0) {
            return "[have$$error]";
        }
        optionsStr = optionsStr + obj + "$$";
    }
    return optionsStr;
}

// 获取index对应的填空题的所有选项以$$连接起来的字符串
function getBlankOptionsStr(jsonFormObject, index) {
    let endNum = latestBlankIndexMap.get("qaBlank" + index);
    let optionsStr = "";
    for (let i = 1; i < endNum; i++) {
        // 有输入框才操作
        if ($("input[name='qaBlank" + index + i + "']").length > 0) {
            let obj = jsonFormObject["qaBlank" + index + i];
            // 填空题序号可能不连续，出现为空继续执行
            if (typeof(obj) === "undefined" || obj === null) {
                optionsStr = optionsStr + "$$";
            }
            if (obj.indexOf("$$") >= 0) {
                return "[have$$error]";
            }
            optionsStr = optionsStr + obj + "$$";
        }
    }
    return optionsStr;
}

// 获取index对应的填空题的所有分数以,连接起来的字符串
function getBlankOptionScoresStr(jsonFormObject, index) {
    let endNum = latestBlankIndexMap.get("qaBlank" + index);
    let optionsStr = "";
    for (let i = 1; i < endNum; i++) {
        // 有输入框才操作
        if ($("input[name='qaBlank" + index + i + "Score']").length > 0) {
            let obj = jsonFormObject["qaBlank" + index + i + "Score"];
            if (typeof(obj) === "undefined" || obj === null) {
                optionsStr = optionsStr + ",";
            } else if (!isNum(obj)) {
                layer.msg("第" + index + "题第" + i + "空分数必须是数字");
                return false;
            }
            optionsStr = optionsStr + obj + ",";
        }
    }
    return optionsStr;
}

// 填空题部分选项分数设置后，自动算总分
function autoInputCountScore(index) {
    let jsonFormObject = getEntity("#qa" + index);
    let endNum = latestBlankIndexMap.get("qaBlank" + index);
    let countScore = 0;
    for (let i = 1; i < endNum; i++) {
        let obj = jsonFormObject["qaBlank" + index + i + "Score"];
        if (typeof(obj) === "undefined" || obj === null || obj === "") {
            continue;
        }
        countScore += parseInt(obj, 10);
    }
    $("input[name='qa" + index + "Score']").val(countScore);
    getPaperFullScore();
}

// 只是添加一个新的问题div的基础html
function onlyAddDiv() {
    $("#question_edit").append("" +
        "    <div>" +
        "       <div class='questionIndex' id='questionIndex" + nextDivIndex + "'>" + nextDivIndex + "</div>" +
        "       <div class='question_bg layui-form' id='qa" + nextDivIndex + "' lay-filter='qa" + nextDivIndex + "'>" +
        "            <div>" +
        "                <div class='layui-inline'>" +
        "                    <label class='layui-form-label question_text'>分数:</label>" +
        "                    <div class='layui-input-inline'>" +
        "                        <input type='text' name='qa" + nextDivIndex + "Score' autocomplete='off'" +
        "                               class='layui-input question_input' oninput='getPaperFullScore()' onporpertychange='getPaperFullScore()' style='margin-left: 39px'>" +
        "                    </div>" +
        "                </div>" +
        "                <div class='layui-inline flag'>" +
        "                    <label class='layui-form-label' style='margin-left: 198px;'>批改模式：</label>" +
        "                    <input type='radio' name='correct" + nextDivIndex + "' lay-filter='correct" + nextDivIndex + "' value='0' title='手动'>" +
        "                    <input type='radio' name='correct" + nextDivIndex + "' lay-filter='correct" + nextDivIndex + "' value='1' title='自动' checked>" +
        "                </div>" +
        "                <div class='layui-inline' style='margin-left: 62px;'>" +
        "                    <a href='javascript:void(0);' onclick='deleteQuestion(" + nextDivIndex + ")'>" +
        "                        <svg class='icon' aria-hidden='true'>" +
        "                            <use xlink:href='#icon-guanbi'></use>" +
        "                        </svg>" +
        "                    </a>" +
        "                </div>" +
        "            </div>" +
        "            <div class='separate'>" +
        "                <div class='layui-inline flag'>" +
        "                    <label class='layui-form-label'>题目类型：</label>" +
        "                    <input type='radio' name='qType" + nextDivIndex + "' lay-filter='qType" + nextDivIndex + "' value='0' title='选择'>" +
        "                    <input type='radio' name='qType" + nextDivIndex + "' lay-filter='qType" + nextDivIndex + "' value='1' title='填空'>" +
        "                </div>" +
        "                <div class='layui-inline' style='margin-left: 82px'>" +
        "                    <label class='layui-form-label'>科目：</label>" +
        "                    <div class='layui-input-block'>" +
        "                        <select lay-search name='subject" + nextDivIndex + "'>" +
        "                           <option selected value=''>暂不指定</option>" + subjectOptions +
        "                        </select>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "            <div class='separate'>" +
        "                <div class='layui-form-item layui-form-text'>" +
        "                    <label class='layui-form-label question_text'>问题描述:</label>" +
        "                    <div class='layui-input-block'>" +
        "                        <textarea name='questionDesc" + nextDivIndex + "' placeholder='请输入内容'" +
        "                                  class='layui-textarea question_desc' id='questionDesc" + nextDivIndex + "'></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "            <div class='chooseEdit" + nextDivIndex + "' style='display: none;'>" +
        "                <div class='separate'>" +
        "                    <div class='chooseLeftText'>" +
        "                        <label class='layui-form-label question_text' style='padding-right: 0;'>选项：</label>" +
        "                    </div>" +
        "                    <div class='chooseCenterOptions'>" +
        "                        <div class='layui-form-item chooseOptions" + nextDivIndex + "' style='height: auto'>" +
        "                            <label class='layui-form-label qaChoose" + nextDivIndex + "A'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>A</label>" +
        "                            <div class='layui-input-block smallML  qaChoose" + nextDivIndex + "A'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "A' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                            <label class='layui-form-label qaChoose" + nextDivIndex + "B'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>B</label>" +
        "                            <div class='layui-input-block smallML  qaChoose" + nextDivIndex + "B'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "B' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                            <label class='layui-form-label qaChoose" + nextDivIndex + "C'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>C</label>" +
        "                            <div class='layui-input-block smallML  qaChoose" + nextDivIndex + "C'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "C' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                            <label class='layui-form-label qaChoose" + nextDivIndex + "D'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>D</label>" +
        "                            <div class='layui-input-block smallML  qaChoose" + nextDivIndex + "D'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "D' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                        </div>" +
        "                    </div>" +
        "                    <div class='chooseRightButton'>" +
        "                        <a href='javascript:void(0);' style='margin-left:20px;' onclick='addNewChooseOption(" + nextDivIndex + ")'>" +
        "                            <svg class='icon' aria-hidden='true'>" +
        "                                <use xlink:href='#icon-tianjia'></use>" +
        "                            </svg>" +
        "                        </a>" +
        "                        <a href='javascript:void(0);' onclick='removeLatestChooseOption(" + nextDivIndex + ")'>" +
        "                            <svg class='icon' aria-hidden='true'>" +
        "                                <use xlink:href='#icon-jianshao'></use>" +
        "                            </svg>" +
        "                        </a>" +
        "                    </div>" +
        "                </div>" +
        "                <div class='layui-form-item separate'>" +
        "                    <label class='layui-form-label question_text'>答案：</label>" +
        "                    <div class='layui-input-block qaChooseAn" + nextDivIndex + "'>" +
        "                        <input type='checkbox' class='qaChoose" + nextDivIndex + "A' name='qaChoose" + nextDivIndex + "An' title='A' value=1>" +
        "                        <input type='checkbox' class='qaChoose" + nextDivIndex + "B' name='qaChoose" + nextDivIndex + "An' title='B' value=2>" +
        "                        <input type='checkbox' class='qaChoose" + nextDivIndex + "C' name='qaChoose" + nextDivIndex + "An' title='C' value=4>" +
        "                        <input type='checkbox' class='qaChoose" + nextDivIndex + "D' name='qaChoose" + nextDivIndex + "An' title='D' value=8>" +
        "                    </div>" +
        "                </div>" +
        "                <div class='separate'>" +
        "                    <label class='layui-form-label question_text'>多选得分模式：</label>" +
        "                    <div>" +
        "                        <input type='radio' name='qa" + nextDivIndex + "Co' value='1' checked title='全部正确选项选中才得分'>" +
        "                    </div>" +
        "                    <div class='layui-inline'>" +
        "                        <div class='layui-input-inline'>" +
        "                            <input type='radio' name='qa" + nextDivIndex + "Co' value='2' title='选中部分正确选项且无错误选项得'>" +
        "                        </div>" +
        "                        <div class='layui-input-inline'>" +
        "                            <input type='text' name='qa" + nextDivIndex + "CoScore' autocomplete='off' class='layui-input'" +
        "                                   style='width: 70px;'>" +
        "                        </div>" +
        "                    </div>" +
        "                    <span class='question_text'>分</span>" +
        "                </div>" +
        "            </div>" +
        "            <div class='blankEdit" + nextDivIndex + "' style='display: none;'>" +
        "                <div class='separate'>" +
        "                    <div class='chooseLeftText' style='width: 99px;'>" +
        "                        <label class='layui-form-label question_text' style='padding-right: 0;'>答案/要点：</label>" +
        "                    </div>" +
        "                    <div class='blankCenterOptions'>" +
        "                        <div class='qaBlankDiv" + nextDivIndex + "'></div>" +
        "                    </div>" +
        "                    <div class='blankRightButton' style='margin-top: 8px;'>" +
        "                        <a href='javascript:void(0);' onclick='addNewOption(" + nextDivIndex + ")'>" +
        "                            <svg class='icon' aria-hidden='true'>" +
        "                                <use xlink:href='#icon-tianjia'></use>" +
        "                            </svg>" +
        "                        </a>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "            <div class='separate'>" +
        "                <div class='layui-form-item layui-form-text'>" +
        "                    <label class='layui-form-label question_text'>问题解析:</label>" +
        "                    <div class='layui-input-block'>" +
        "                        <textarea name='questionAnalyze" + nextDivIndex + "' placeholder='请输入内容'" +
        "                                  class='layui-textarea question_desc' id='questionAnalyze" + nextDivIndex + "'></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </div>" +
        "     </div>");
}

// 获取科目列表
function getSubjectList() {
    $.get("/SysDict/getIdValueNameByCode.do",
        {
            dictCode: "subject"
            , token: token
        },
        function (data) {
            if (data.status === 200) {
                $.each(data.data, function (index, val) {
                    subjectOptions = subjectOptions + "<option value='" + val.id + "'>" + val.dictName + "</option>";
                });
            }
            subjectIsLoad = true;
        });
}

// 获取并设置默认科目列表
function setDefaultSubjectList() {
    if (typeof(form) === "undefined" || form === null || subjectIsLoad === false) {
        setTimeout("setDefaultSubjectList()", 500);
        return;
    }
    $("#defaultSubject").append("<option value=''>暂不指定</option>" + subjectOptions);
    form.render();
}

// 不保存返回列表页
function returnWithOutSave() {
    FloorObject = layer.confirm("不保存并返回列表页？", {
        btn: ['确定', '取消'] //按钮
    }, function () {
        window.parent.changeView("papers/paper-list.html");
    }, function () {
        closeFloor(FloorObject);
    });
}

// 表单数据校验
function formItemValid(jsonFormObject) {
    if (fieldIsWrong(jsonFormObject.paperName)) {
        layer.msg("试卷名称必填");
        return false;
    }
    if (fieldIsWrong(jsonFormObject.examTimeNum)) {
        layer.msg("考试时间必填");
        return false;
    } else if (isNum(jsonFormObject.examTimeNum) === false) {
        layer.msg("考试时间必须是数字");
        return false;
    }

    // 循环处理问题
    let optionNum = 0;// 选项的个数（用于填空题）
    for (let i = 1; i < nextDivIndex; i++) {
        optionNum = 0;//初始化
        let curQa = $("#qa" + i);
        if (typeof(curQa) === "undefined" || curQa === null || curQa.length === 0) {
            continue;
        }
        if (fieldIsWrong(jsonFormObject["correct" + i])) {
            layer.msg("第" + i + "题的批改模式必选");
            return false;
        }
        if (fieldIsWrong(jsonFormObject["qType" + i])) {
            layer.msg("第" + i + "题的题目类型出现错误，请删除重试");
            return false;
        }
        if (fieldIsWrong(jsonFormObject["questionDesc" + i])) {
            layer.msg("第" + i + "题的问题描述必填");
            return false;
        }
        if (jsonFormObject["qType" + i] === '0') {
            let endNum = curChooseIndexMap.get("qaChoose" + i).charCodeAt();
            if (endNum - 65 < 1) {
                layer.msg("第" + i + "题的至少要两个选项");
                return false;
            }
            for (let j = 65; j <= endNum; j++) {
                if (fieldIsWrong(jsonFormObject["qaChoose" + i + String.fromCharCode(j)])) {
                    layer.msg("第" + i + "题的" + String.fromCharCode(j) + "选项未填");
                    return false;
                }
            }
            if (fieldIsWrong(jsonFormObject["qa" + i + "Score"])) {
                layer.msg("第" + i + "题的分数必填");
                return false;
            } else if (isNum(jsonFormObject["qa" + i + "Score"]) === false) {
                layer.msg("第" + i + "题的分数必须是数字");
                return false;
            }
            let chooseObj = $("input[name='qaChoose" + i + "An']:checked");
            if (chooseObj.length === 0) {
                layer.msg("第" + i + "题至少需要一个答案");
                return false;
            } else if (chooseObj.length > 1) {
                if (fieldIsWrong(jsonFormObject["qa" + i + "Co"])) {
                    layer.msg("第" + i + "题请指定多选得分模式");
                    return false;
                } else if (jsonFormObject["qa" + i + "Co"] === "2") {
                    if (fieldIsWrong(jsonFormObject["qa" + i + "CoScore"])) {
                        layer.msg("第" + i + "题请指定部分正确得分");
                        return false;
                    } else if (isNum(jsonFormObject["qa" + i + "CoScore"]) === false) {
                        layer.msg("第" + i + "题指定部分正确得分必须是数字");
                        return false;
                    }
                }
            }
        } else if (jsonFormObject["qType" + i] === '1') {
            let endNum = latestBlankIndexMap.get("qaBlank" + i);
            for (let j = 1; j < endNum; j++) {
                // 有输入框才校验
                if ($("input[name='qaBlank" + i + j + "']").length > 0) {
                    optionNum++;
                    if (jsonFormObject["questionDesc" + i].indexOf("[$" + j + "$]") < 0) {
                        layer.msg("第" + i + "题问题描述中占位符[$" + j + "$]被破坏");
                        return false;
                    }
                    if (fieldIsWrong(jsonFormObject["qaBlank" + i + j])) {
                        layer.msg("第" + i + "题第" + j + "个空位答案需要填写");
                        return false;
                    }
                    if (fieldIsWrong(jsonFormObject["qaBlank" + i + j + "Score"])) {
                        layer.msg("第" + i + "题第" + j + "个空位答案需要指定分数");
                        return false;
                    } else if (isNum(jsonFormObject["qaBlank" + i + j + "Score"]) === false) {
                        layer.msg("第" + i + "题第" + j + "个空位答案指定分数必须是数字");
                        return false;
                    }
                }
            }
            if (optionNum < 1) {
                layer.msg("第" + i + "题至少需要一个空位");
                return false;
            }
            if (fieldIsWrong(jsonFormObject["qa" + i + "Co"])) {
                layer.msg("第" + i + "题请指定要点得分模式");
                return false;
            }
        }
    }
}

// 计算满分
function getPaperFullScore() {
    let countScore = 0;
    for (let i = 1; i <= nextDivIndex; i++) {
        let qaScore = $("input[name='qa" + i + "Score']").val();
        if (!fieldIsWrong(qaScore)) {
            countScore += parseInt(qaScore, 10);
        }
    }
    $("input[name='paperFullScore']").val(countScore);
}

// 获取index对应的填空题的所有选项的index以,连接起来的字符串
function getBlankIndexStr(index) {
    let endNum = latestBlankIndexMap.get("qaBlank" + index);
    let optionsStr = "";
    for (let i = 1; i < endNum; i++) {
        // 有输入框才操作
        if ($("input[name='qaBlank" + index + i + "']").length > 0) {
            optionsStr = optionsStr + i + ",";
        }
    }
    return optionsStr;
}
