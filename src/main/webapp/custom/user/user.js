$(function () {
    var colModel=
    [{title: '序号', width:"2%", align:'center',
      formatter: function (value, row, index){return index+1;}},
    {field: 'state', checkbox: true},
    {field: 'userId', title: '用户ID', sortable: true,  width:"8%", align:'center', halign: 'center'},
    {field: 'userName', title: '账号', sortable: true, halign: 'center'},
    {field: 'deptName', title: '所属部门', sortable: true, halign: 'center'},
    {field: 'mobile', title: '手机号', sortable: true, halign: 'center'},
    {field: 'status', title: '状态', sortable: true, halign: 'center',width:"10%",align:'center',formatter:function (value,row,index) {
            return value==0?'<span class="label label-danger">禁用</span>':'<span class="label label-success">正常</span>';
     }},
    {field: 'action', title: '操作', halign: 'center', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}];

    var table =$("#Table");
    table.bootstrapTable({
        url: '/sys/user/list',
        height: window.height,
        striped: true,
        search: true,
        showRefresh: true,
        showToggle: true,
        showColumns: true,
        searchOnEnterKey: true,
        minimumCountColumns: 2,
        showPaginationSwitch: true,
        clickToSelect: true,
        detailView: true,
        detailFormatter: 'detailFormatter',
        queryParamsType:'',
        queryParams: function queryParams(params) {
            var param = {
            };
            return param;
        },
        pagination: true,
        paginationLoop: false,
        classes: 'table table-hover  table-striped  table-bordered',
        sidePagination: 'server',
        silentSort: false,
        smartDisplay: false,
        escape: true,
        searchOnEnterKey: true,
        idField: 'roleId',
        maintainSelected: true,
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        toolbar: '#toolbar',
        columns:colModel
    }).on('all.bs.table', function(e, name, args) {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    });

});
function actionFormatter2(value, row, index) {
    return [
        '<a class="like" href="javascript:void(0)" data-toggle="tooltip" title="Like"><i class="fa fa-arrows"></i></a>　',
        '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="Edit"><i class="fa fa-pencil"></i></a>　',
        '<a class="remove ml10" href="javascript:void(0)" data-toggle="tooltip" title="Remove"><i class="fa fa-trash"></i></a>'
    ].join('');
}
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

function detailFormatter(index, row) {
    var html = [];
    $.each(row, function(key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

var app = new Vue({
    el: '#mApp',
    data: {
        isShow: false,
    },
    methods: {
        toggle: function() {
            this.isShow = !this.isShow;
        }
    }
});