let form;
layui.use('form', function () {
    form = layui.form;
});
let layer;

// 先获取试卷下拉列表，在获取
getPaperList();

// 获取试卷列表
function getPaperList() {
    if (fieldIsWrong(form)) {
        setTimeout("getPaperList()", 200);
        return;
    }
    $.get("/Papers/selectPapersByName.do",
        {
            page: 1
            , limit: 1000
            , token: token
        },
        function (data) {
            let selectObj = $("#searchPaperId");
            selectObj.empty();
            if (data.code === 0) {
                let subjectOptions = "";
                $.each(data.data, function (index, val) {
                    subjectOptions = subjectOptions + "<option value='" + val.id + "'>" + val.papersName + "</option>";
                });
                selectObj.append(subjectOptions);
            } else {
                selectObj.append("<option value='noPaper'>暂无发布的试卷</option>");
            }
            form.render();
            setTimeout("initTable()", 500);
        });
}

function initTable() {
    layui.use('table', function () {
        layer = layui.layer;
        let table = layui.table;

        table.render({
            elem: '#mainTable'
            , height: 'full-80'//高度设置为距底部30
            , url: '../../Score/getScoreListByPaperId.do' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {field: 'realName', title: '考生姓名', width: 210, fixed: 'left'}
                , {field: 'fullScore', title: '总分', width: 80}
                , {field: 'score', title: '得分', width: 80}
                , {field: 'eeNo', title: '考生号', width: 200}
                , {field: 'blurStartTime', title: '开始考试时间', width: 200, templet: '#time2string1'}
                , {field: 'commitTime', title: '交卷时间', width: 200, templet: '#time2string2'}
                , {title: '操作', width: 100, align: 'center', toolbar: '#mainTableBar'}
            ]]
            , where: {
                token: token
                , paperId: $("#searchPaperId").val()
            }
        });

        //监听工具条
        table.on('tool(mainTableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            let data = obj.data; //获得当前行数据
            let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

            if (layEvent === 'lookDo') { //进入考试信息查看页面
                $.cookie("ErDoWatchScoreId", data.scoreId, {path: '/'});
                window.parent.changeView("examPaper/erDoWatch.html");
            }
        });
    });
}

//搜索按钮点击
function searchBtnClick() {
    let table = layui.table;

    table.reload('mainTable', {
        where: {
            token: token
            , paperId: $("#searchPaperId").val()
            , searchEeInfo: $("#searchEeInfo").val()
        },
        page: {
            curr: 1
        }
    });
}
