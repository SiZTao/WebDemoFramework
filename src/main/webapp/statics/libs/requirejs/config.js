/**
 * Created by wrp on 2017/9/22.
 */
var contextPath = window.location.protocol+"//"+window.location.host+"/AdminPL/";
var _common = "statics/";
require.config({
    baseUrl: contextPath + _common,
    paths: {
    	adminpl:'js/adminpl',
    	app_iframe:'js/app_iframe',
    	app:'js/app',
    	demo:'js/demo',
    	Vue:'js/vue.min',
        jquery: 'libs/jquery/jquery-2.2.4.min.js',
        domReady: 'libs/requirejs/domReady',
        bootstrap: 'libs/bootstrap/js/bootstrap.min',
		bootstrap-table:'libs/bootstrap-table/bootstrap-table.min',
		bootstrap-table-export:'libs/bootstrap-table/extensions/export/bootstrap-table-export.min',
		bootstrap-table-mobile:'libs/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min',
		bootstrap-table-lang:'libs/bootstrap-table/locale/bootstrap-table-zh-CN.min',
		layer:'libs/layer/layer'
    },
    map:{
        "*":{
            "css": "libs/requirejs/css.min"
        }
    },
    shim: {
        bootstrap: { deps: ["jquery"] },
        bootstrap-table: { deps: ["bootstrap"] }
    },
    waitSeconds: 30,
    charset:'utf-8'

});

