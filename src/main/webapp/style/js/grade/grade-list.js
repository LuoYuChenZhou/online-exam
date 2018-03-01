// function searchBtnClick() {
//     let jsonFormData = getEntity("#grade_list_form");
//     let table = layui.table;
//
//     table.reload('gradeListTable', {
//         where: {
//             token: token
//             , searchGradeName: jsonFormData.searchGradeName
//         }
//     });
// }

layui.use('table', function () {
    let layer = layui.layer;
    let table = layui.table;

    let log_tab = table.render({
        elem: '#grade_list_table'
        , height: 'full-80'//高度设置为距底部30
        , url: '../../Grade/getGradeListByNameUser.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'logTitle', title: '班级名称', width: 180, fixed: 'left'}
            , {field: 'moduleName', title: '当前人数', width: 200}
            , {field: 'createTime', title: '创建时间', width: 177, templet: '#time2string'}
            , {fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#grade_list_bar'}
        ]]
        , id: 'gradeListTable'
        , where: {
            token: token
        }
    });

    log_tab.reload({
        where: {
            token: token
            , searchStartTime: $("#search_grade_name").val()
        }
    });

    //监听工具条
    table.on('tool(grade-t)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'sort') { //排序
            layui.use('layer', function () {
                layer.open({
                    type: 1,
                    title: '设置排序号',
                    area: ['600px', '400px'],
                    offset: ['10%', '20%'],
                    shade: 0.6,
                    shadeClose: true,
                    content: ''
                });
            });
        }
    });
});
