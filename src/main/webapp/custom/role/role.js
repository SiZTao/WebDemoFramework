
$(function () {
    var table = $("#Table");
    var colModel =    [
        {title: '序号', width:"2%", align:'center', formatter: function (value, row, index){return index+1;}},
        {field: 'state', checkbox: true},
        {field: 'roleId', title: '角色ID', sortable: true,  width:"8%", align:'center', halign: 'center'},
        {field: 'roleName', title: '角色名称', sortable: true, align:'center',halign: 'center'},
        {field: 'deptName', title: '所属部门', sortable: true,align:'center', halign: 'center'},
        {field: 'remark', title: '备注', sortable: true,align:'center', halign: 'center'},
        {field: 'createTime', title: '创建时间', sortable: true, halign: 'center',width:"10%",align:'center',align:'center'},
        {field: 'action', title: '操作', halign: 'center', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}
    ];
    table.bootstrapTable({
        url: '/sys/role/list',
        height: window.height,
        method:'get',			//请求方法
        toolbar: '#toolbar',	//工具栏
        striped: true,			//是否显示行间隔色
        search: true,		 	//是否显示表格搜索，此搜索是客户端搜索，不会进服务端，
        searchOnEnterKey: true,
        showRefresh: true,		//是否显示刷新按钮
        locale: 'zh-CN',
        showToggle: true,		//是否显示详细视图和列表视图的切换按钮
        showColumns: true,		//是否显示所有的列
//	    showExport: true,
//      exportDataType: "all",
//      exportTypes: ['json', 'xml', 'csv', 'txt', 'doc', 'excel'],
        minimumCountColumns: 2,	//最少允许的列数
        showPaginationSwitch: true,
        clickToSelect: true, 	//是否启用点击选中
        singleSelect: false, 	//是否启用单选	//是否启用点击选中行
        detailView: true,		//是否显示父子表
        detailFormatter: 'detailFormatter',
        pageSize: 10,
        pageList: [10, 25, 50, 'All'],
        pagination: true,			//是否显示分页（*）
        paginationLoop: false,
        classes: 'table table-hover  table-striped table-condensed table-bordered',
        cardView: true, //卡片视图
        sidePagination: 'server',
        //silentSort: false,
        smartDisplay: false,
        pk: 'roleId',
        sortName: 'roleId',
        sortOrder: 'desc',
        escape: true,
        searchOnEnterKey: true,
        idField: 'roleId',
        maintainSelected: true,
        mobileResponsive: true, //是否自适应移动端
        columns:colModel
    }).on('all.bs.table', function(e, name, args) {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    });
    $(".btn-refresh").on("click",function(){
        $("#menuTable").bootstrapTable('refresh');
    });
    table.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table fa.event.check', function () {
        var ids = selectedids(table);
        $(".btn-disabled").toggleClass("disabled",!ids.length);
    });
});

//菜单树
var menu_ztree;
var menu_setting ={
    data:{
        simpleData:{
            enable:true,
            idKey:"menuId",
            pIdKey:"parentId",
            rootPId:-1
        },
        key:{
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true
    }
};
//部门结构树
var dept_ztree;
var dept_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};

//数据树
var data_ztree;
var data_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};
var vm = new Vue({
    el: '#mApp',
    data: {
        isShow: false,
        roleForm:false,
        q: {
            roleName: null
        },
        role: {deptId: '', deptName: ''},
        ruleValidate: {
            roleName: [
                {required: true, message: '角色名称不能为空', trigger: 'blur'}
            ],
            deptName:[
                {required: true, message: '部门不能为空', trigger: 'blur'}
            ]
        }
    },
    methods: {
        toggle: function() {
            this.isShow = !this.isShow;
        },
        ok:function(){
            toastr.info("保存角色");
        },
        cancel:function(){
            return;
        },
        reset:function(){

        },
        query:function(){
            vm.reload();
        },
        add:function(){
            vm.roleForm = true;
            vm.isShow=false;
            vm.title ="新增"
            vm.role = {deptId: '', deptName: ''};
            vm.getMenuTree(null);
            vm.getDept();
            vm.getDataTree();
        },
        update:function(roleId){


        },
        getRole:function(roleId){

        },
        saveOrUpdate:function(event){
            //获取选择的菜单
            var nodes = menu_ztree.getCheckedNodes(true);
            var menuIdList = new Array();
            for(var i =0;i< nodes.lengh;i++){
                menuIdList.push(nodes[i].menuId);
            }
            vm.role.menuIdList = menuIdList;
            //获取选中的数据
            var nodes = data_ztree.getCheckedNodes(true);
            var deptIdList = new Array();
            for(var i= 0;i<nodes.length;i++){
                deptIdList.push(nodes[i].deptId);
            }
            vm.role.deptIdList =deptIdList;
            var url = vm.role.roleId == null ?"/sys/role/save":"/sys/role/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.role),
                contentType: "application/json",
                type: 'POST',
                successCallback: function () {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        getMenuTree:function(roleId){
            Ajax.request({
                url:"/sys/menu/perms",
                async:true,
                successCallback:function (r) {
                    menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r.menuList);
                    //展开所有节点
                    menu_ztree.expandAll(true);
                    if (roleId != null) {
                        vm.getRole(roleId);
                    }
                }
            })
        },
        getDataTree:function () {
            //加载菜单树
            Ajax.request({
                url: "/sys/dept/list",
                async: true,
                successCallback: function (r) {
                    data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r.list);
                    //展开所有节点
                    data_ztree.expandAll(true);
                }
            });
        },
        getDept: function () {
            //加载部门树
            Ajax.request({
                url: "/sys/dept/list",
                async: true,
                successCallback: function (r) {
                    dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting, r.list);
                    var node = dept_ztree.getNodeByParam("deptId", vm.role.deptId);
                    if (node != null) {
                        dept_ztree.selectNode(node);
                        vm.role.deptName = node.name;
                    }
                }
            });
        },
        deptTree:function(){
            //选择机构
            openWindow({
                title: "选择部门",
                area: ['300px', '450px'],
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = dept_ztree.getSelectedNodes();
                    //选择上级部门
                    vm.role.deptId = node[0].deptId;
                    vm.role.deptName = node[0].name;
                    layer.close(index);
                }
            });
        },
        reload:function(event){
            $("#Table").bootstrapTable('refresh');
            vm.handleReset('formValidate');
        },
        handleSubmit:function(name){
            handleSubmitValidate(this,name,function(){
                vm.saveOrUpdate()
            });
        },
        handleReset:function(name){
            handleResetForm(this,name);
        }
    }
});

function selectedids(table) {
    var options = table.bootstrapTable('getOptions');
    if (options.templateView) {
        return $.map($("input[data-id][name='checkbox']:checked"), function (dom) {
            return $(dom).data("id");
        });
    } else {
        return $.map(table.bootstrapTable('getSelections'), function (row) {
            return row[options.pk];
        });
    }
}
function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}
function actionFormatter(value, row, index) {
    return [
        '<a href="javascript:;" class="btn btn-xs btn-primary btn-dragsort like" title="详情" data-table-id="table" data-field-index="13" data-row-index="0" data-button-index="0"><i class="fa fa-arrows"></i></a>',
        '<a href="javascript:;" class="btn btn-xs btn-success btn-dragsort edit ml10" onclick="updateAction()" title="编辑" data-table-id="table" data-field-index="13" data-row-index="0" data-button-index="0"><i class="fa fa-pencil"></i></a>',
        '<a href="javascript:;" class="btn btn-xs btn-danger btn-dragsort remove ml10" title="删除" data-table-id="table" data-field-index="13" data-row-index="0" data-button-index="0"><i class="fa fa-trash"></i></a>'
    ].join('');
}
window.actionEvents = {
    'click .like': function(e, value, row, index) {
        alert('You click edit icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    },
    'click .edit': function(e, value, row, index) {
        JSON.stringify(row);
        var roleId = row.roleId;
        console.log(roleId);
        vm.update(roleId);
    },
    'click .remove': function(e, value, row, index) {
        var roleIds =row.roleId;
        console.log(roleIds);
    }
};
function deleteAction() {
    var rows  = $("#Table").bootstrapTable('getSelections');
    var roleIds = new Array();
    for (var i in rows) {
        roleIds.push(rows[i].roleId);
    }
    console.log(JSON.stringify(roleIds));
    Ajax.request({
        url: "/sys/role/delete",
        contentType: "application/json",
        params: JSON.stringify(roleIds),
        type: 'POST',
        successCallback: function () {
            alert('操作成功', function (index) {
                vm.reload();
            });
        }
    });
}
function updateAction() {
    var rows  = $("#Table").bootstrapTable('getSelections');
    if (rows.length != 1) {
        alert('请选择一条记录');
    }else {

    }
}

