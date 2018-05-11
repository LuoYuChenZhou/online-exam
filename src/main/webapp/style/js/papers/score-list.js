let layer;
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
            , paperId: $.cookie("paperIdForScoreList")
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

//搜索按钮点击
function searchBtnClick() {
    let table = layui.table;

    table.reload('mainTable', {
        where: {
            token: token
            , paperId: $.cookie("paperIdForScoreList")
            , searchEeInfo: $("#searchEeInfo").val()
        },
        page: {
            curr: 1
        }
    });
}

//跳转到试卷编辑页面
function toPaperEditForm(id) {
    $.cookie("curPaperEditId", id);
    window.parent.changeView("papers/paper-edit-form.html");
}
