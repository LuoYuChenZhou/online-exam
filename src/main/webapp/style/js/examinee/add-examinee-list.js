let FloorObject;
let curEeId;//当前操作的考生的id
let curChooseGradeId;//当前选中的班级id

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
            , {title: '操作', width: 100, align: 'center', toolbar: '#main_table_bar'}
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

        if (layEvent === 'add') {
            curEeId = data.id;
            layui.use('form', function () {
                let form = layui.form;
                getAllGradeList(form);
                form.on('select(grade_select_filter)', function (data) {
                    curChooseGradeId = data.value;
                });
            });

            layui.use('layer', function () {
                FloorObject = layer.open({
                    type: 1,
                    title: '添加考生',
                    area: ['400px', '320px'],
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
            , searchString: jsonFormData.searchString
        },
        page: {
            curr: 1
        }
    });
}

//获取所有班级列表
function getAllGradeList(form) {
    $.get("/Grade/getGradeListByNameUser.do",
        {
            page: 1
            , limit: 10000
            , token: token
        },
        function (data) {
            let gradeListSelect = $("#gradeListSelect");
            gradeListSelect.empty();
            if (data.code === 0) {
                gradeListSelect.append("<option value='noAddToGrade' selected>暂不添加进班级</option>");
                for (let i = 0; i < data.data.length; i++) {
                    gradeListSelect.append("<option value='" + data.data[i].id + "'>" + data.data[i].gradeName + "</option>");
                }
            } else if (data.code === 204) {
                gradeListSelect.append("<option value='noAddToGrade' selected>暂不添加进班级</option>");
            } else {
                layer.msg(data.msg);
            }
            form.render();
        });
}

//添加考生
function addEe() {
    $.post("/ErEe/requestEeEr.do",
        {
            targetId: curEeId
            , operate: "erRequest"
            , gradeId: curChooseGradeId
            , sortNo: $("input[name='ee_add_sort']").val()
            , extraMsg: $("#ee_add_extraMsg").val()
            , token: token
        },
        function (data) {
            if (data.status === 201) {
                layer.msg("邀请成功，请等待考生回应", {icon: 6, offset: ['100px']});
            } else {
                layer.msg(data.msg, {icon: 4, offset: ['100px']});
            }
            closeFloor(FloorObject);
            searchBtnClick();
        });

}