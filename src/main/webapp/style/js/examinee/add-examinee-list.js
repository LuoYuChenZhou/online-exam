
layui.use('table', function () {
    let layer = layui.layer;
    let table = layui.table;

    table.render({
        elem: '#main_table'
        , height: 'full-80'//高度设置为距底部80
        , url: '../../ErEe/getExamineeNoRelation.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'realName', title: '考生姓名', width: 120, fixed: 'left'}
            , {field: 'eeNo', title: '考生号', width: 200}
            , {field: 'sex', title: '性别', width: 80, templet: '#num2Sex'}
            , {field: 'phone', title: '联系电话', width: 180}
            , {field: 'email', title: '电子邮箱', width: 240}
            , {fixed: 'right', title: '操作', width: 100, align: 'center', toolbar: '#main_table_bar'}
        ]]
        , where: {
            token: token
        }
    });

    //监听工具条
    table.on('tool(main_table_filter)', function (obj) { //注：tool是工具条事件名，tool括号内是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        // let tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //查看
            layui.use('layer', function () {
                layer.open({
                    type: 1,
                    title: '添加班级',
                    area: ['400px', '250px'],
                    offset: ['10%', '20%'],
                    shade: 0.6,
                    shadeClose: true,
                    content: $('#add_form')
                });
            });
        }
    });
});

//搜索按钮点击
function searchBtnClick() {
    let jsonFormData = getEntity("#search_form");
    let table = layui.table;

    table.reload('main_table', {
        where: {
            token: token
            , searchEeName: jsonFormData.searchEeName
            , searchEeNo: jsonFormData.searchEeNo
        },
        page: {
            curr: 1
        }
    });
}
