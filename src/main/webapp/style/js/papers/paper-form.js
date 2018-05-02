let nextDivIndex = 1;//下一个div的下标
let correctMap = new Map();//所有批改类型的Map
let questionTypeMap = new Map();//所有问题类型的Map
let subjectIsLoad = false;// 科目列表是否获取
let subjectOptions = ""; // 科目列表选项

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
        console.log(subjectOptions);
    });
});

$(document).ready(function () {
    getSubjectList();
    setDefaultSubjectList();// 默认科目下拉刷新
    addQuestionDiv();//初始新增一个div
});

// 展示选择或填空的输入表单
// index填入是第几个div，传数字1、2、3等
// type填入choose或blank
function showChooseOrBlankForm(index, type) {

}

// 题目类型按钮添加监听
function typeRadioAddListen(index) {
    if (typeof(form) === "undefined" || form === null) {
        setTimeout("typeRadioAddListen(" + index + ")", 200);
        return;
    }

    form.on('radio(qType' + index + ')', function (data) {
        let choose = $(".chooseEdit" + index);
        let blank = $(".blankEdit" + index);
        let correct = $("input[name='correct" + index + "']");

        questionTypeMap.set("qType" + index, data.value);
        switch (data.value) {
            // 0是选择，1是填空，2是简答
            case "0":
                choose.show();
                blank.hide();
                correct.attr("disabled", false);
                form.render('radio', 'qa' + index);
                break;
            case "1":
                if (correctMap.get("correct" + index) === "1") {
                    choose.hide();
                    blank.show();
                }
                correct.attr("disabled", false);
                form.render('radio', 'qa' + index);
                break;
            case "2":
                choose.hide();
                blank.hide();

                let hand = $("input[name='correct" + index + "'][value='0']");
                let auto = $("input[name='correct" + index + "'][value='1']");

                hand.prop("checked", true);
                auto.prop("checked", false);
                correct.attr("disabled", true);
                form.render('radio', 'qa' + index);
                correctMap.set("correct" + index, "0");
        }
    });

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

// 删除选中的div
function deleteQuestion(index) {
    $("#qa" + index).remove();
}

// 删除指定的填空题的指定项
function deleteOption(index, optionIndex) {

}

// 在指定填空题内添加空位
function addNewOption(index) {

}

// 创建一个div并完善相关步骤
function addQuestionDiv() {
    if (typeof(form) === "undefined" || form === null || subjectIsLoad === false) {
        setTimeout("addQuestionDiv()", 500);
        return;
    }
    onlyAddDiv();

    correctMap.set("correct" + nextDivIndex, "1");// 默认批改模式是自动
    questionTypeMap.set("qType" + nextDivIndex, "-9999");// 默认问题类型是未确定
    typeRadioAddListen(nextDivIndex);// 添加单选监听
    form.render();// 页面会报form是undefined错误，不用管
    nextDivIndex = nextDivIndex + 1;
}

function onlyAddDiv() {
    $("#question_edit").append("<div class='question_bg layui-form' id='qa" + nextDivIndex + "' lay-filter='qa" + nextDivIndex + "'>" +
        "            <div>" +
        "                <div class='layui-inline'>" +
        "                    <label class='layui-form-label question_text'>分数:</label>" +
        "                    <div class='layui-input-inline'>" +
        "                        <input type='text' name='qa" + nextDivIndex + "Score' required lay-verify='required' autocomplete='off'" +
        "                               class='layui-input question_input' style='margin-left: 39px'>" +
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
        "                    <input type='radio' name='qType" + nextDivIndex + "' lay-filter='qType" + nextDivIndex + "' value='2' title='简答'>" +
        "                </div>" +
        "                <div class='layui-inline'>" +
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
        "                                  class='layui-textarea question_desc'></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "            <div class='chooseEdit" + nextDivIndex + "' style='display: none;'>" +
        "                <div class='separate'>" +
        "                    <div class='chooseLeftText'>" +
        "                        <label class='layui-form-label question_text' style='padding-right: 0;'>选项：</label>" +
        "                    </div>" +
        "                    <div class='chooseCenterOptions'>" +
        "                        <div class='layui-form-item'>" +
        "                            <label class='layui-form-label'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>A</label>" +
        "                            <div class='layui-input-block smallML'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "A' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                            <label class='layui-form-label'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>B</label>" +
        "                            <div class='layui-input-block smallML'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "B' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                            <label class='layui-form-label'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>C</label>" +
        "                            <div class='layui-input-block smallML'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "C' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                            <label class='layui-form-label'" +
        "                                   style='width: auto;padding-left: 0;padding-right: 0'>D</label>" +
        "                            <div class='layui-input-block smallML'>" +
        "                                <input type='text' name='qaChoose" + nextDivIndex + "D' required lay-verify='required' placeholder='请输入内容'" +
        "                                       autocomplete='off' class='layui-input' style='width: 556px;'>" +
        "                            </div>" +
        "                        </div>" +
        "                    </div>" +
        "                    <div class='chooseRightButton'>" +
        "                        <a href='javascript:void(0);' style='margin-left:20px;'>" +
        "                            <svg class='icon' aria-hidden='true'>" +
        "                                <use xlink:href='#icon-tianjia'></use>" +
        "                            </svg>" +
        "                        </a>" +
        "                        <a href='javascript:void(0);'>" +
        "                            <svg class='icon' aria-hidden='true'>" +
        "                                <use xlink:href='#icon-jianshao'></use>" +
        "                            </svg>" +
        "                        </a>" +
        "                    </div>" +
        "                </div>" +
        "                <div class='layui-form-item separate'>" +
        "                    <label class='layui-form-label question_text'>答案：</label>" +
        "                    <div class='layui-input-block'>" +
        "                        <input type='checkbox' name='qaChoose" + nextDivIndex + "An' title='A' value=1>" +
        "                        <input type='checkbox' name='qaChoose" + nextDivIndex + "An' title='B' value=2>" +
        "                        <input type='checkbox' name='qaChoose" + nextDivIndex + "An' title='C' value=4>" +
        "                        <input type='checkbox' name='qaChoose" + nextDivIndex + "An' title='D' value=8>" +
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
        "                        <div>" +
        "                            <div class='layui-inline' style='width: 418px;'>" +
        "                                <div class='layui-input-inline smallML' style='margin-left: 7px;'>" +
        "                                    <input type='text' name='qaBlank" + nextDivIndex + "A' autocomplete='off' class='layui-input' required" +
        "                                           lay-verify='required' style='width: 392px;' placeholder='请输入内容'>" +
        "                                </div>" +
        "                            </div>" +
        "                            <div class='layui-inline' style='width: 150px;margin-right:0;'>" +
        "                                <label class='layui-form-label smallText'>分数</label>" +
        "                                <div class='layui-input-inline smallML'>" +
        "                                    <input type='text' name='qaBlank\" + nextDivIndex + \"AScore' autocomplete='off' class='layui-input'" +
        "                                           style='width: 83px;'>" +
        "                                </div>" +
        "                            </div>" +
        "                            <div class='layui-inline'>" +
        "                                <a href='javascript:void(0);' onclick='deleteOption(" + nextDivIndex + ",'A')'>" +
        "                                    <svg class='icon' aria-hidden='true'>" +
        "                                        <use xlink:href='#icon-jianshao'></use>" +
        "                                    </svg>" +
        "                                </a>" +
        "                            </div>" +
        "                        </div>" +
        "                    </div>" +
        "                    <div class='blankRightButton' style='margin-top: 8px;'>" +
        "                        <a href='javascript:void(0);' onclick='addNewOption(" + nextDivIndex + ")'>" +
        "                            <svg class='icon' aria-hidden='true'>" +
        "                                <use xlink:href='#icon-tianjia'></use>" +
        "                            </svg>" +
        "                        </a>" +
        "                    </div>" +
        "                </div>" +
        "                <div class='separate'>" +
        "                    <label class='layui-form-label question_text'>要点得分模式：</label>" +
        "                    <div>" +
        "                        <input type='radio' name='qa" + nextDivIndex + "Co' value='2' checked title='填入文字与预设答案近似就得分'>" +
        "                    </div>" +
        "                    <div>" +
        "                        <input type='radio' name='qa" + nextDivIndex + "Co' value='1' title='填入文字与预设答案完全相同才得分'>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </div>");
}

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

function setDefaultSubjectList() {
    if (typeof(form) === "undefined" || form === null || subjectIsLoad === false) {
        setTimeout("setDefaultSubjectList()", 500);
        return;
    }
    $("#defaultSubject").append("<option value=''>暂不指定</option>" + subjectOptions);
    form.render();
}

