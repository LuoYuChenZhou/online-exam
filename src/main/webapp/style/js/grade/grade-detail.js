let FloorObject = null;//弹出层对象
let curGradeId = $.cookie("curOperateGradeId");//当前选中的班级的id
let curEeId;

layui.use('table', function () {
    let layer = layui.layer;
    let table = layui.table;

    table.render({
        elem: '#main_show_table'
        , height: 'full-80'//高度设置为距底部30
        , url: '../../EeGrade/getEeListByNameNoClass.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'realName', title: '考生姓名', width: 130, fixed: 'left'}
            , {field: 'eeNo', title: '考生号', width: 180}
            , {field: 'sex', title: '性别', width: 80, templet: '#dtf'}
            , {field: 'phone', title: '联系电话', width: 150}
            , {field: 'email', title: '电子邮箱', width: 200}
            , {title: '操作', width: 100, align: 'center', toolbar: '#main_table_bar'}
        ]]
        , where: {
            searchClass: curGradeId
            , token: token
        }
    });

    //监听工具条
    table.on('tool(main-filter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        curEeId = data.eeId;
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if (layEvent === 'sort') { //排序
            $('#sort_cur_ee').val(data.realName);
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
                removeEeFromGrade("4");
            }, function () {
                closeFloor(FloorObject);
            });
        }
    });
});

$(function () {
    //默认值的赋值要在页面加载完成以后
    $("#curGradeName").val($.cookie("curOperateGradeName"));
});

//搜索按钮点击
function searchBtnClick() {
    let jsonFormData = getEntity("#main_search_form");
    let table = layui.table;

    table.reload('main_show_table', {
        where: {
            searchClass: curGradeId
            , token: token
            , searchString: jsonFormData.searchString
        },
        page: {
            curr: 1
        }
    });
}

//设置排序号
function setSortNo() {
    $.post("/EeGrade/setEeGradeSortNo.do",
        {
            gradeId: curGradeId
            , targetSortNo: $("#sort_sortNo").val()
            , eeId: curEeId
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                layer.msg("排序完成", {icon: 6, offset: ['100px']});
                closeFloor(FloorObject);
                searchBtnClick();
            } else {
                layer.msg(data.msg);
            }
        });
}

//返回列表页
function backToList() {
    window.parent.changeView("grade/grade-list.html");
}

//跳转到添加考生页面
function geToAddEe() {
    window.parent.changeView("examinee/my-examinee-list.html");
}

//从班级内删除考生
function removeEeFromGrade() {
    $.post("/EeGrade/removeEeFromGrade.do",
        {
            gradeId: curGradeId
            , eeId: curEeId
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                layer.msg("删除成功", {icon: 6, offset: ['100px']});
                closeFloor(FloorObject);
                searchBtnClick();
            } else {
                layer.msg(data.msg);
            }
        });
}