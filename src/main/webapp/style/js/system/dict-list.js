let FloorObject = null;//弹出层对象
let curOperateDictId;//当前操作字典id
let curChooseUpperId;//当前选中的父级字典id
let curEditType;//当前字典编辑类型（add-新增，modify-修改）

let layer;
layui.use('layer', function () {
    layer = layui.layer;
});

let form;
layui.use('form', function () {
    form = layui.form;

    getUpperZeroDictList("");

    form.on('select(upperId)', function (data) {
        curChooseUpperId = data.value;
        searchBtnClick();
    });
});

let table;
layui.use('table', function () {
    table = layui.table;

    table.render({
        elem: '#mainTable'
        , height: 'full-80'//高度设置为距底部30
        , url: '../../SysDict/getDictListByNameValueCodeUpperId.do' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'dictName', title: '字典名称', width: 200, fixed: 'left'}
            , {field: 'dictCode', title: '字典编码', width: 200}
            , {field: 'dictValue', title: '字典值', width: 200}
            , {field: 'status', title: '当前状态', width: 120, templet: '#getStatusText'}
            , {title: '操作', width: 150, align: 'center', toolbar: '#mainTableBar'}
        ]]
        , where: {
            upperId: '0'
            , token: token
        }
    });

    //监听工具条
    table.on('tool(mainTableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        curOperateDictId = data.id;

        if (layEvent === 'edit') { //修改试卷名称
            curEditType = "modify";
            getUpperZeroDictList(data.id);
            $("#editUpperId option[value='" + data.upperId + "']").attr("selected", "selected");
            $("#editDictName").val(data.dictName);
            $("#editDictCode").val(data.dictCode);
            $("#editDictValue").val(data.dictValue);
            FloorObject = layer.open({
                type: 1,
                title: '字典编辑',
                area: ['400px', '350px'],
                offset: ['10%', '20%'],
                shade: 0.6,
                shadeClose: true,
                content: $('#dictEditFloor')
            });
        } else if (layEvent === 'delete') { //删除
            FloorObject = layer.confirm('确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                modifyDictStatus("4");
            }, function () {
                closeFloor(FloorObject);
            });
        } else if (layEvent === 'enOrDis') { //启用或禁用
            let PromptMsg = null;
            let GoalStatus = null;
            if (data.status === "1") {
                PromptMsg = "确认禁用？";
                GoalStatus = "0";
            } else {
                PromptMsg = "确认启用？";
                GoalStatus = "1";
            }
            FloorObject = layer.confirm(PromptMsg, {
                btn: ['确定', '取消'] //按钮
            }, function () {
                modifyDictStatus(GoalStatus);
            }, function () {
                closeFloor(FloorObject);
            });
        }
    });
});

//搜索按钮点击
function searchBtnClick() {
    table.reload('mainTable', {
        where: {
            token: token
            , searchString: $('#searchString').val()
            , upperId: curChooseUpperId
        },
        page: {
            curr: 1
        }
    });
}

//字典提交
function editDictCommit() {
    let postUrl, dictId;
    if (curEditType === 'add') {
        postUrl = "/SysDict/addDict.do";
        dictId = "";
    } else {
        postUrl = "/SysDict/updateDict.do";
        dictId = curOperateDictId;
    }

    let jsonFormData = getEntity("#dictEditFloor");

    $.post(postUrl,
        {
            id: dictId
            , dictName: jsonFormData.editDictName
            , dictCode: jsonFormData.editDictCode
            , dictValue: jsonFormData.editDictValue
            , upperId: jsonFormData.editUpperId
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

//修改字典状态
function modifyDictStatus(targetStatus) {
    $.post("/SysDict/modifyDictStatus.do",
        {
            dictId: curOperateDictId
            , targetStatus: targetStatus
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

//获取父级id为0的字典列表
function getUpperZeroDictList(verifyDictId) {
    $.get("/SysDict/getUpperZeroDictList.do",
        {
            verifyDictId: verifyDictId
            , token: token
        },
        function (data) {
            let upperIdObj;
            if (verifyDictId !== "") {
                upperIdObj = $('#editUpperId');
            } else {
                upperIdObj = $('#upperId');
            }
            upperIdObj.empty();
            if (data.status === 200) {
                upperIdObj.attr("disabled", false);
                if (verifyDictId === "") {
                    upperIdObj.append("<option value=''>全部</option>");
                }
                upperIdObj.append("<option value='0'>顶级字典</option>");
                $.each(data.data, function (index, val) {
                    upperIdObj.append("<option value='" + val.id + "'>" + val.dictName + "</option>");
                });
            } else if (data.status === 204) {
                upperIdObj.attr("disabled", false);
                upperIdObj.append("<option value='0'>顶级字典</option>");
            } else if (data.status === 203) {
                upperIdObj.attr("disabled", true);
                upperIdObj.append("<option value='0'>顶级字典</option>");
            }
            form.render('select');
        });
}

//打开新增弹窗
function openAddFloor() {
    curEditType = "add";
    getUpperZeroDictList();
    $("#editUpperId option[value='0']").attr("selected", "selected");
    $("#editDictName").val("");
    $("#editDictCode").val("");
    $("#editDictValue").val("");
    FloorObject = layer.open({
        type: 1,
        title: '字典编辑',
        area: ['400px', '350px'],
        offset: ['10%', '20%'],
        shade: 0.6,
        shadeClose: true,
        content: $('#dictEditFloor')
    });
}