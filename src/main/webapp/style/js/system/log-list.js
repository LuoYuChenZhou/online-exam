//日期选择器
layui.use('laydate', function () {
    let laydate = layui.laydate;

    laydate.render({
        elem: '#startTime' //指定元素
        , type: 'datetime'
    });
    laydate.render({
        elem: '#endTime' //指定元素
        , type: 'datetime'
    });
});

function searchBtnClick() {
    let jsonFormData = getEntity("#log_form");
    let table = layui.table;

    table.reload('logTable', {
        where: {
            token: token
            , searchStartTime: jsonFormData.searchStartTime
            , searchEndTime: jsonFormData.searchEndTime
            , searchLevel: jsonFormData.searchLevel
            , searchTitle: jsonFormData.searchTitle
        },
        page: {
            curr: 1
        }
    });
}

layui.use('table', function () {
    let layer = layui.layer;
    let table = layui.table;

    let log_tab = table.render({
        elem: '#log_table'
        , height: 'full-80'//高度设置为距底部30
        , url: '../../SysLog/getSysLogList.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'logTitle', title: '标题', width: 180, fixed: 'left', sort: true}
            , {field: 'moduleName', title: '模块', width: 200, sort: true}
            , {field: 'logLevel', title: '等级', width: 100, templet: '#levelName'}
            , {field: 'logDescription', title: '描述', width: 350}
            , {field: 'createTime', title: '发生时间', width: 177, templet: '#time2string', sort: true}
            , {field: 'logUser', title: '操作人', width: 120}
            , {fixed: 'right', title: '操作', width: 100, align: 'center', toolbar: '#log_bar'}
        ]]
        , id: 'logTable'
        , where: {
            token: token
        }
    });

    //监听工具条
    table.on('tool(log-t)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        // let tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //查看
            layui.use('layer', function () {
                layer.open({
                    type: 0,
                    title: '日志详情',
                    area: ['600px', '400px'],
                    offset: ['10%', '20%'],
                    shade: 0.6,
                    shadeClose: true,
                    content: '日志标题：<span style="color: #1E9FFF">' + data.logTitle + "</span><hr/>" +
                    "模块：<span style=\"color: #1E9FFF\">" + data.moduleName + "</span><hr/>" +
                    "描述：<div style='border: 1px solid #E6E6E6;border-radius: 5px; height: 150px; overflow: auto;font-size:12px;color: #1E9FFF;'>" + data.logDescription + "</div>"
                });
            });
        }
    });
});

layui.use('form', function () {
    let form = layui.form;

    form.on('select(level-log)', function () {
        searchBtnClick();
    });
});

//实际上已使用
function getLevelName(logLevel) {
    switch (logLevel) {
        case "0":
            return "系统信息";
        case "1":
            return "错误";
        case "2":
            return "严重错误";
        default:
            return "";
    }
}