let FloorObject = null;//弹出层对象
let curOperatePaperId;//当前操作试卷id

let layer;
layui.use('table', function () {
    layer = layui.layer;
    let table = layui.table;

    table.render({
        elem: '#mainTable'
        , height: 'full-80'//高度设置为距底部30
        , url: '../../Papers/selectPapersByName.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'papersName', title: '试卷名称', width: 210, fixed: 'left'}
            , {field: 'examTime', title: '考试时间（分钟）', width: 180}
            , {field: 'fullScore', title: '总分', width: 80}
            , {field: 'studentNum', title: '当前状态', width: 200, templet: '#getPaperStatus'}
            , {field: 'examNum', title: '当前考试人数', width: 200}
            , {title: '操作', width: 150, align: 'center', toolbar: '#mainTableBar'}
        ]]
        , where: {
            token: token
        }
    });

    //监听工具条
    table.on('tool(mainTableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        curOperatePaperId = data.id;

        if (layEvent === 'changeName') { //修改试卷名称
            $("#curPaperName").val(data.papersName);
            FloorObject = layer.open({
                type: 1,
                title: '试卷名称修改',
                area: ['400px', '240px'],
                offset: ['10%', '20%'],
                shade: 0.6,
                shadeClose: true,
                content: $('#changeNameForm')
            });
        } else if (layEvent === 'edit') { //进入编辑
            toPaperEditForm(data.id);
        } else if (layEvent === 'delete') { //删除
            FloorObject = layer.confirm('确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                changePaperStatus("4");
            }, function () {
                closeFloor(FloorObject);
            });
        } else if (layEvent === 'enOrDis') { //启用或禁用
            let PromptMsg = null;
            let GoalStatus = null;
            if (data.status === "1") {
                PromptMsg = "确认发布？";
                GoalStatus = "2";
            } else {
                PromptMsg = "取消发布将清空当前考生的成绩，确认取消？";
                GoalStatus = "1";
            }
            FloorObject = layer.confirm(PromptMsg, {
                btn: ['确定', '取消'] //按钮
            }, function () {
                changePaperStatus(GoalStatus);
            }, function () {
                closeFloor(FloorObject);
            });
        }
    });
});

//搜索按钮点击
function searchBtnClick() {
    let jsonFormData = getEntity("#mainSearchForm");
    let table = layui.table;

    table.reload('mainTable', {
        where: {
            token: token
            , searchPaperName: jsonFormData.searchPaperName
        },
        page: {
            curr: 1
        }
    });
}

//跳转到试卷新增页面
function toPaperAddForm() {
    window.parent.changeView("papers/paper-add-form.html");
}

//跳转到试卷编辑页面
function toPaperEditForm(id) {
    $.cookie("curPaperEditId", id);
    window.parent.changeView("papers/paper-edit-form.html");
}

//修改试卷名称
function changePaperName() {
    $.post("/Papers/changePaperName.do",
        {
            paperId: curOperatePaperId
            , newPaperName: $("#newPaperName").val()
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                layer.msg(data.msg, {icon: 6, offset: ['100px']});
                closeFloor(FloorObject);
                searchBtnClick();
            } else {
                layer.msg(data.msg);
            }
        });
}

//修改试卷状态
function changePaperStatus(status) {
    $.post("/Papers/changePaperStatus.do",
        {
            paperId: curOperatePaperId
            , targetStatus: status
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                layer.msg(data.msg, {icon: 6, offset: ['100px']});
                closeFloor(FloorObject);
                searchBtnClick();
            } else {
                layer.msg(data.msg);
            }
        });
}

//根据状态转文字
function getPaperStatus(status) {
    if (status === "1") {
        return "编写中";
    } else if (status === "2") {
        return "已发布";
    }
}