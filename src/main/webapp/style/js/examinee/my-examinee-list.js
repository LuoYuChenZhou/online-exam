let curChooseGradeId;//当前选中的班级id

layui.use('form', function () {
    let form = layui.form;

    $.get("/Grade/getGradeListByNameUser.do", {page: 1, limit: 1000, token: token}, function (data) {
        let searchClass = $("#searchClass");
        searchClass.empty();
        if (data.code === 0) {
            searchClass.append("<option value=\"allGrade\">所有</option>");

            $.each(data.data, function (index, val) {
                searchClass.append("<option value=" + val.id + ">" + val.gradeName + "</option>");
            });
        } else if (data.code === 204) {
            searchClass.html("<option value=\"allGrade\">所有</option>");
        }
        //如果将下面这句话放到方法外面，需要注意异步问题导致的页面渲染失败
        form.render('select');
    });

    form.on('select(searchClass)', function (data) {
        curChooseGradeId = data.value;
        searchBtnClick();
    });
});

layui.use('table', function () {
    let layer = layui.layer;
    let table = layui.table;

    table.render({
        elem: '#main_table'
        , height: 'full-80'//高度设置为距底部30
        , url: '../../EeGrade/getEeListByNameNoClass.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'realName', title: '考生姓名', width: 120, sort: true, fixed: 'left'}
            , {field: 'eeNo', title: '考生号', width: 200}
            , {field: 'sex', title: '性别', width: 80, templet: '#num2Sex'}
            , {field: 'phone', title: '联系电话', width: 180}
            , {field: 'email', title: '电子邮箱', width: 240}
            , {field: 'gradeNames', title: '所在班级', width: 240}
            , {title: '操作', width: 100, align: 'center', toolbar: '#main_table_bar'}
        ]]
        , id: 'logTable'
        , where: {
            token: token
        }
    });

    //监听工具条
    table.on('tool(main_table_filter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        // let tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'addToGrade') { //添加到班级
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

//搜索按钮点击
function searchBtnClick() {
    let jsonFormData = getEntity("#search_form");
    let table = layui.table;

    table.reload('logTable', {
        where: {
            token: token
            , searchClass: curChooseGradeId
            , searchString: jsonFormData.searchString
        },
        page: {
            curr: 1
        }
    });
}
