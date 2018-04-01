let FloorObject = null;//弹出层对象
let curGradeId = $.cookie("curOperateGradeId");//当前选中的班级的id

layui.use('table', function () {
    let layer = layui.layer;
    let table = layui.table;

    table.render({
        elem: '#main_show_table'
        , height: 'full-80'//高度设置为距底部30
        , url: '../../Grade/getGradeListByNameUser.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'gradeName', title: '班级名称', width: 180, fixed: 'left'}
            , {field: 'studentNum', title: '当前人数', width: 200}
            , {field: 'createTime', title: '创建时间', width: 177, templet: '#time2string'}
            , {fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#grade_list_bar'}
        ]]
        , where: {
            token: token
        }
    });

    //监听工具条
    table.on('tool(grade-t)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        curGradeId = data.id;
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'detail') { //查看详情
        } else if (layEvent === 'sort') { //排序
            $('#sort_cur_grade').val(data.gradeName);
            $('#sort_sortNo').val(data.sortNo);
            layui.use('layer', function () {
                FloorObject = layer.open({
                    type: 1,
                    title: '设置排序号',
                    area: ['400px', '250px'],
                    offset: ['10%', '20%'],
                    shade: 0.6,
                    shadeClose: true,
                    content: $('#sort_form')
                });
            });
        } else if (layEvent === 'delete') { //删除
            FloorObject = layer.confirm('确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                changeGradeStatus("4");
            }, function () {
                closeFloor(FloorObject);
            });
        } else if (layEvent === 'enOrDis') { //启用或禁用
            let PromptMsg = null;
            let GoalStatus = null;
            if (data.status === "1") {
                PromptMsg = "确定禁用？"
                GoalStatus = "0";
            } else {
                PromptMsg = "确定启用？"
                GoalStatus = "1";
            }
            FloorObject = layer.confirm(PromptMsg, {
                btn: ['确定', '取消'] //按钮
            }, function () {
                changeGradeStatus(GoalStatus);
            }, function () {
                closeFloor(FloorObject);
            });
        }
    });
});

//搜索按钮点击
function searchBtnClick() {
    let jsonFormData = getEntity("#grade_list_form");
    let table = layui.table;

    table.reload('grade_list_table', {
        where: {
            token: token
            , searchGradeName: jsonFormData.searchGradeName
        },
        page: {
            curr: 1
        }
    });
}

//设置排序号
function setSortNo() {
    $.post("/Grade/setGradeSortNo.do",
        {
            gradeId: curGradeId
            , sortNo: $("#sort_sortNo").val()
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                closeFloor(FloorObject);
                searchBtnClick();
            } else {
                layer.msg(data.msg);
            }
        });
}

//根据班级id获取班级信息
function changeGradeStatus(status) {
    $.get("/Grade/getGradeInfoById.do",
        {
            id: curGradeId
            , status: status
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                closeFloor(FloorObject);
                searchBtnClick();
            } else {
                layer.msg(data.msg);
            }
        });
}