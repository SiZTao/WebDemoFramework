$(function () {
    var colModel =    [{title: '序号', width:"2%", align:'center',
        formatter: function (value, row, index){return index+1;}},
        {field: 'state', checkbox: true},
        {field: 'roleId', title: '角色ID', sortable: true,  width:"8%", align:'center', halign: 'center'},
        {field: 'roleName', title: '角色名称', sortable: true, halign: 'center'},
        {field: 'deptName', title: '所属部门', sortable: true, halign: 'center'},
        {field: 'remark', title: '备注', sortable: true, halign: 'center'},
        {field: 'createTime', title: '创建时间', sortable: true, halign: 'center',width:"10%",align:'center',formatter:function (value,row,index) {
                return transDate(value);
            }},
        {field: 'action', title: '操作', halign: 'center', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}];
    var table = $("#Table");
    table.bootstrapTable({
        url: 'sys/role/list',
        height: window.height,
        striped: true,
        search: true,
        searchOnEnterKey: true,
        showRefresh: true,
        showToggle: true,
        showColumns: true,
        minimumCountColumns: 2,
        showPaginationSwitch: true,
        clickToSelect: true,
        detailView: true,
        detailFormatter: 'detailFormatter',
        pagination: true,
        pageNumber:1,//初始化加载第一页，默认第一页
        pageSize: 10,//每页的记录行数（*）
        pageList: [25,50,100],//可供选择的每页的行数（*）
        paginationLoop: false,
        queryParamsType:'',
        queryParams: function queryParams(params) {
            var param = {

            };
            return param;
        },
        classes: 'table table-hover  table-striped table-condensed table-bordered',
        sidePagination: 'server',
        silentSort: false,
        smartDisplay: false,
        idField: 'roleId',
        sortName: 'roleId',
        sortOrder: 'desc',
        escape: true,
        searchOnEnterKey: true,
        maintainSelected: true,
        toolbar: '#toolbar',
        columns:colModel
    }).on('all.bs.table', function(e, name, args) {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    });
});
function actionFormatter(value, row, index) {
    return [
        '<a href="javascript:;" class="btn btn-xs btn-primary btn-dragsort like" title="Like" data-table-id="table" data-field-index="13" data-row-index="0" data-button-index="0"><i class="fa fa-arrows"></i></a>',
        '<a href="javascript:;" class="btn btn-xs btn-success btn-dragsort edit ml10" title="Edit" data-table-id="table" data-field-index="13" data-row-index="0" data-button-index="0"><i class="fa fa-pencil"></i></a>',
        '<a href="javascript:;" class="btn btn-xs btn-danger btn-dragsort remove ml10" title="Remove" data-table-id="table" data-field-index="13" data-row-index="0" data-button-index="0"><i class="fa fa-trash"></i></a>'
    ].join('');
}
window.actionEvents = {
    'click .like': function(e, value, row, index) {
        alert('You click like icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    },
    'click .edit': function(e, value, row, index) {
        alert('You click edit icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    },
    'click .remove': function(e, value, row, index) {
        alert('You click remove icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    }
};
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

var roleApp = new Vue({
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

        },
        add:function(){

        },
        update:function(){

        },
        del:function(event){

        },
        getRole:function(roleId){

        },
        saveOrUpdate:function(event){

        },
        getMenuTree:function(event){

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

