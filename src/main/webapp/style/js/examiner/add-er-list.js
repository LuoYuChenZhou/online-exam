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
        , height: 'full-80'//高度设置为距底部30
        , url: '../../ErEe/getOtherErListByEe.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'realName', title: '考官姓名', width: 210, fixed: 'left'}
            , {field: 'sex', title: '性别', width: 210}
            , {title: '操作', width: 210, align: 'center', toolbar: '#mainTableBar'}
        ]]
        , where: {
            token: token
            , paperId: $("#searchErName").val()
        }
    });

    //监听工具条
    table.on('tool(mainTableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'add') { //进入考试信息查看页面
            $.post("/ErEe/requestEeEr.do"
                , {
                    targetId: data.erId
                    , operate: "eeRequest"
                    , token: token
                }
                , function (data) {
                    if (data.status === 201) {
                        layer.msg('申请成功', {icon: 6, offset: ['100px']});
                    } else {
                        layer.msg(data.msg);
                    }
                    searchBtnClick();
                });
        }
    });
});

//搜索按钮点击
function searchBtnClick() {
    let table = layui.table;

    table.reload('mainTable', {
        where: {
            token: token
            , paperId: $("#searchPaperId").val()
            , searchErName: $("#searchErName").val()
        },
        page: {
            curr: 1
        }
    });
}
