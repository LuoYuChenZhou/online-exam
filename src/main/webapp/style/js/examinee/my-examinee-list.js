let curChooseGradeId;//当前选中的班级id
let curAddFormGradeId;//考生添加到班级表单当前所选班级id
let curOperateEeId;//当前操作的考生
let floorObject;//弹出层对象


let form;
layui.use('form', function () {
    form = layui.form;

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
    form.on('select(grade_select_filter)', function (data) {
        console.log(111);
        curAddFormGradeId = data.value;
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

        curOperateEeId = data.eeId;

        if (layEvent === 'addToGrade') { //添加到班级
            //获取考生不在的班级列表(异步问题搬进来)
            $.get("/EeGrade/getNoEeGradeList.do", {eeId: data.eeId, token: token}, function (data) {
                let otherGradeListSelect = $("#otherGradeListSelect");
                otherGradeListSelect.empty();
                if (data.status === 200) {
                    curAddFormGradeId = data.data[0].gradeId;
                    $.each(data.data, function (index, val) {
                        otherGradeListSelect.append("<option value=" + val.gradeId + ">" + val.gradeName + "</option>");
                    });
                } else if (data.status === 204) {
                    //没有班级列表时，返回false不展开弹层
                    layer.msg("该考生没有需要加入的班级", {icon: 0, offset: ["100px"]});
                    return;
                } else {
                    layer.msg("系统繁忙", {icon: 5, offset: ["100px"]});
                    return;
                }
                //如果执行到了这步，表示没有问题，展示弹层
                form.render('select', 'addToGradeFormFilter');
                layui.use('layer', function () {
                    floorObject = layer.open({
                        type: 1,
                        title: '添加到班级',
                        area: ['400px', '260px'],
                        offset: ['10%', '20%'],
                        shade: 0.6,
                        shadeClose: true,
                        content: $('#addToGradeForm')
                    });
                });
            });
        } else if (layEvent === 'delete') {
            floorObject = layer.confirm('确认删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                deleteEe();
            }, function () {
                layer.close(floorObject);
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

//将考生添加到其它班级中
function insertEeToGrade() {
    $.post("/EeGrade/insertEeToGrade.do"
        , {
            eeId: curOperateEeId
            , gradeId: curAddFormGradeId
            , sortNo: $("#ee_add_sort").val()
            , token: token
        }
        , function (data) {
            if (data.status === 201) {
                layer.msg("添加成功", {icon: 1, offset: ["100px"]});
                closeFloor(floorObject);
            } else {
                layer.msg(data.msg, {icon: 5, offset: ["100px"]});
            }
            searchBtnClick();
        });
}

//删除考生
function deleteEe() {
    $.post("/ErEe/removeEeEr.do"
        , {
            targetId: curOperateEeId
            , operate: "erRemove"
            , token: token
        }
        , function (data) {
            if (data.status === 201) {
                layer.msg("删除成功", {icon: 1, offset: ["100px"]});
                closeFloor(floorObject);
            } else {
                layer.msg(data.msg, {icon: 5, offset: ["100px"]});
            }
            searchBtnClick();
        });
}


