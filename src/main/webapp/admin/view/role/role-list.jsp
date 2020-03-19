<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%@include file="/admin/view/main/resource.jsp" %>
<title>权限管理</title>
<style>
.dataTables_wrapper .dataTables_length{padding:10px 0 0 10px;}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 角色管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray">
		<!-- 搜索栏 -->
		<div class="row cl">
			 <label class="form-label col-xs-1">创建时间：</label>
				 <div class="formControls col-xs-5" style="margin-left:-50px;">
					<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="startTime" class="input-text Wdate" style="width:160px;margin-left: 50px;" name="startTime" />
					至
					<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})" id="endTime" class="input-text Wdate" style="width:160px;" name="endTime" />
				</div>
		</div>
	  	<div class="row cl">
	  	 	<div class="formControls col-xs-11 text-c"> 
				<a href="javascript:;" onclick="query()" style="margin-top:10px;width:15%" class="btn btn-success radius"><i class="Hui-iconfont">&#xe665;</i> 查询</a>
			</div>
	  	</div>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-5"> 
		<span class="l">
			<a href="javascript:;" onclick="layer_show('添加角色','${pageContext.request.contextPath}/admin/view/role/role-add.jsp','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加角色</a>
		</span> 
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th width="25">序号</th>
					<th width="120">角色名称</th>
					<th width="120">编码</th>
					<th width="120">权限</th>
					<th width="60">状态</th>
					<th width="120">创建人</th>
					<th width="120">创建时间</th>
					<th width="80">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
var datatable = null;
$(function(){
	datatable = $('.table-sort').DataTable({
		pagingType: "input",
		"sScrollX": "100%",  
		"sScrollXInner": "100%",  
		"bScrollCollapse": true,
		//order: [[7, 'desc']],
		//aoColumnDefs: [{"orderable":false,"aTargets":[0,1,2,3,4,5,6,7,8,9]}],// 制定列不参与排序
		ordering:false,
		ajax: {
			url: "${pageContext.request.contextPath}/web/role/find-all",
			type: 'post',
			data: function(d) {
                d.startTime=$("#startTime").val();
                d.endTime=$("#endTime").val(); 
            },
            dataSrc: function ( json ) {
              if(json.success){
                return json.data;
              }else{
            	  layer.msg(json.message, {time: 20000,btn: ['知道了']}); 
              }
		   }
		},
		fnDrawCallback:function(oSettings){//表格画完的回调函数
			var obj = oSettings.json.otherData;
		 	/*$("name").val(obj.name);
            $("type").val(obj.type);
            $("icon").val(obj.icon);
            $("url").val(obj.url);
            $("sort").val(obj.sort);
            $("state").val(obj.state);
            $("createMan").val(obj.createMan);
            $("createAt").val(obj.createAt);  */
            
        },
		columns: [{
			"data": null,
			"class": "text-c",
			"render":function(data, type, row, meta){
				return  meta.row + 1;
			}
		},{
			"data": "name",
			"defaultContent": "",
			"class": "text-c"
		},{
			"data": "code",
			"defaultContent": "",
			"class": "text-c"
		},{
			"data": null,
			"defaultContent": "",
			"class": "text-c",
			"render":function(data, type, row, meta){
				return  '<i class="Hui-iconfont">&#xe61d;</i><button type="button" class="btn btn-primary radius size-MINI" onclick="layer_show(\'分配权限\',\'${pageContext.request.contextPath}/web/role-permission/find-all-allot-permission?roleId='+row.id+'\',\'700\',\'800\')">分配权限</button>';
			}
			
        }, {
            "data": "state",
            "defaultContent": "",
            "class": "text-c",
            "render" : function (data, type, row, meta) {
            	if(data == '0'){
					return '<span class="label radius">冻结</span>';
				}else{
					return '<span class="label label-success radius">启用</span>';
				}
            }
        }, {
            "data": "createMan",
            "defaultContent": "",
            "class": "text-c"
        },{
            "data": "createAt",
            "defaultContent": "",
            "class": "text-c"
        }, {
            "data": null,
            "render": function(data, type, row, meta) {
              var btns = new Array();
              var i = 0;
           	  if(row.state == '0'){
                    btns[i] = butInfo("&#xe6dc;","启用","changeState('${pageContext.request.contextPath}/web/role/change-state','"+row.id+"',1)");
              }else{
                    btns[i] = butInfo("&#xe6de;","冻结","changeState('${pageContext.request.contextPath}/web/role/change-state','"+row.id+"',0)");
               }
           	  i++;

              btns[i] = butInfo("&#xe6df;","编辑","layer_show('用户管理','${pageContext.request.contextPath}/web/role/find-one?id="+row.id+"','770','400')");
              i++;
               
              btns[i] = butInfo("&#xe6e2;","删除","delEntity('${pageContext.request.contextPath}/web/role/delete','"+row.id+"')");
  			  i++;


              return getDTOperateBtn(btns);
            }
        }],
		initComplete:function(data){
		}
	});
});

</script>
</body>
</html>