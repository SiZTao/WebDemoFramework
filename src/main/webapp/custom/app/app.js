$(function () {
    var colModel =    [{title: '序号', width:"2%", align:'center',
        formatter: function (value, row, index){return index+1;}},
        {field: 'state', checkbox: true},
        {field: 'id', title: 'ID', sortable: true,  width:"8%", align:'center', halign: 'center'},
        {field: 'domainCode', title: '域编码', sortable: true, halign: 'center'},
        {field: 'domainName', title: '域名称', sortable: true, halign: 'center'},
        {field: 'icon', title: '图标', sortable: true, halign: 'center',formatter:function(value){
                if (!value) {
                    return '';
                }
                return '<i class="' + value + ' fa-lg">';
            }},
        {field: 'domainUrl', title: '域地址', sortable: true, halign: 'center'},
        {field: 'domainStatus', title: '状态', sortable: true, halign: 'center',formatter:function (value) {return  transStatus(value);}},
        {field: 'createTime', title: '创建时间', sortable: true, halign: 'center',width:"10%",align:'center',formatter:function (value,row,index) {
                return transDate(value);
            }},
        {field: 'remark', title: '备注', sortable: true, halign: 'center'},

        {field: 'action', title: '操作', halign: 'center', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}];
    var table = $("#Table");
    table.bootstrapTable({
        url: '/sys/domain/list',
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
        idField: 'id',
        sortName: 'id',
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
            ]
        }
    },
    methods: {

    }
});

