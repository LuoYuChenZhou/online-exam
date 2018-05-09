let FloorObject = null;//弹出层对象
let curOperatePaperId;//当前操作试卷id

$(document).ready(function () {
    setErList();
});

let form;
layui.use('form', function () {
    form = layui.form;
});

let layer;
layui.use('table', function () {
    layer = layui.layer;
    let table = layui.table;

    table.render({
        elem: '#mainTable'
        , height: 'full-80'//高度设置为距底部80
        , url: '../../Papers/selectPapersByErName.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'papersName', title: '试卷名称', width: 300, fixed: 'left'}
            , {field: 'erName', title: '出题考官', width: 180}
            , {field: 'examTime', title: '考试时间（分钟）', width: 180}
            , {field: 'fullScore', title: '总分', width: 120}
            , {title: '操作', width: 120, align: 'center', toolbar: '#mainTableBar'}
        ]]
        , where: {
            token: token
            , paperErId: $("#paperErId").val()
            , searchPaperName: $("#searchPaperName").val()
        }
    });

    //监听工具条
    table.on('tool(mainTableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'do') {
            $.cookie("curDoExamPaperId", data.id);
            window.parent.changeView("examPaper/doExam.html");
        } else if (layEvent === 'watch') {
            $.cookie("curWatchExamPaperId", data.id);
            window.parent.changeView("examPaper/doWatch.html");
        }
    });
});

//搜索按钮点击
function searchBtnClick() {
    let table = layui.table;

    table.reload('mainTable', {
        where: {
            token: token
            , paperErId: $("#paperErId").val()
            , searchPaperName: $("#searchPaperName").val()
        },
        page: {
            curr: 1
        }
    });
}

// 获取并设置考官下拉列表
function setErList() {
    if (typeof(form) === "undefined" || form === null) {
        setTimeout("setErList()", 500);
        return;
    }
    $.get("/ErEe/getErListByEe.do",
        {
            token: token
        },
        function (data) {
            let erListSelect = $("#paperErId");
            erListSelect.html("<option value='' selected>不限</option>");
            if (data.status === 200) {
                $.each(data.data, function (index, val) {
                    erListSelect.append("<option value='" + val.erId + "'>" + val.erName + "</option>");
                });
            }
            form.on('select(paperErId)', function (data) {
                searchBtnClick();
            });

            form.render();
        });
}