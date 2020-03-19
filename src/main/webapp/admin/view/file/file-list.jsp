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
<title>文件管理</title>
<style>
.dataTables_wrapper .dataTables_length{padding:10px 0 0 10px;}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 业务管理 <span class="c-gray en">&gt;</span> 文件管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray">
		<!-- 搜索栏 -->
		<div class="row cl">
		   <label class="form-label col-sm-1">文件类型：</label>
			<div class="formControls col-xs-3"> 
				<span class="select-box">
					<select class="select" id="type" name="type" size="1">
						<option value="">全部</option>
						<option value="1">图片</option>
						<option value="2">视频</option>
						<option value="3">音频</option>
					</select>
				</span>
			</div>
			 <label class="form-label col-xs-1">创建时间：</label>
				 <div class="formControls col-xs-5" style="margin-left:-50px;">
					<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="startTime" class="input-text Wdate" style="width:160px;margin-left: 50px;" name="startTime" />
					至
					<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})" id="endTime" class="input-text Wdate" style="width:160px;" name="endTime" />
				</div>
		</div>
		<div class="row cl">
		    <label class="form-label col-sm-1">创建人:</label>
			<div class="formControls col-xs-3">
				<input type="text" class="input-text" id="createMan" name="createMan"/>
			</div>
			<label class="form-label col-sm-1">业务ID:</label>
			<div class="formControls col-xs-3">
				<input type="text" class="input-text" id="bizId" name = "bizId"/>
			</div>
			<label class="form-label col-sm-1">是否已关联业务：</label>
			<div class="formControls col-xs-3"> 
				<span class="select-box">
					<select class="select" id="hasBizId" name="hasBizId" size="1">
						<option value="">全部</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
		    <label class="form-label col-sm-1">所属模块：</label>
			<div class="formControls col-xs-3"> 
				<span class="select-box">
					<select class="select" id="moduleCode" name="moduleCode" size="1">
						<option value="">全部</option>
						<option value="user">用户模块</option>
					</select>
				</span>
			</div>
			<label class="form-label col-sm-1">存储类型：</label>
			<div class="formControls col-xs-3"> 
				<span class="select-box">
					<select class="select" id="storeType" name="storeType" size="1">
						<option value="">全部</option>
						<option value="1">本地</option>
						<option value="2">静态资源服务器</option>
						<option value="3">网易云</option>
						<option value="4">腾讯云1</option>
						<option value="5">腾讯云2</option>
						<option value="6">七牛云</option>
						<option value="7">阿里云</option>
						<option value="8">优刻云</option>
					</select>
				</span>
			</div>
			<label class="form-label col-sm-1">状态：</label>
			<div class="formControls col-xs-3"> 
				<span class="select-box">
					<select class="select" id="state" name="state" size="1">
						<option value="">全部</option>
						<option value="0">禁用</option>
						<option value="1">启用</option>
					</select>
				</span>
			</div>
		</div>
	  	<div class="row cl">
	  	 	<div class="formControls col-xs-11 text-c"> 
				<a href="javascript:;" onclick="query()" style="margin-top:10px;width:15%" class="btn btn-success radius"><i class="Hui-iconfont">&#xe665;</i> 查询</a>
			</div>
	  	</div>
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th width="25">序号</th>
					<th width="120">预览</th>
					<th width="120">所属模块</th>
					<th width="120">文件名称</th>
					<th width="120">原文件名</th>
					<th width="150">新文件名</th>
					<th width="60">存储方式</th>
					<th width="180">文件后缀</th>
					<th width="120">文件大小</th>
					<th width="120">文件类型</th>
					<th width="120">是否已关联业务</th>
					<th width="120">状态</th>
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
			url: "${pageContext.request.contextPath}/web/file/find-all",
			type: 'post',
			data: function(d) {
                d.type=$("#type").val();
                d.startTime=$("#startTime").val();
                d.endTime=$("#endTime").val(); 
                d.createMan=$("#createMan").val(); 
                d.bizId=$("#bizId").val(); 
                d.moduleCode=$("#moduleCode").val(); 
                d.storeType=$("#storeType").val(); 
                d.state=$("#state").val(); 
                d.hasBizId=$("#hasBizId").val(); 
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
			"data": "url",
			"defaultContent": "",
			"class": "text-c",
			"render":function(data, type, row, meta){
				if(row.type == '1'){
					return  '<img src="'+data+'" width="70px" height="70px" onclick="layer_show(\'预览\',\'${pageContext.request.contextPath}/admin/view/file/file-show.jsp?url='+row.url+'&type='+row.type+'\',\'800\',\'500\')" />';
				}
				else{
					return  '<img src="${pageContext.request.contextPath}/admin/static/self/images/play.jpg" width="30px" height="30px" onclick="layer_show(\'预览\',\'${pageContext.request.contextPath}/admin/view/file/file-show.jsp?url='+row.url+'&type='+row.type+'\',\'800\',\'500\')" />';
					
				}
				
				//onclick="layer_show(\'预览\',\'<img src="'+data+'" />\',\'800\',\'500\')"
				
			}
		},{
			"data": "moduleName",
			"defaultContent": "",
			"class": "text-c"
		},{
			"data": "name",
			"defaultContent": "",
			"class": "text-c"
		},{
			"data": "oldFileName",
			"defaultContent": "",
			"class": "text-c"
		},{
			"data": "newFileName",
			"defaultContent": "",
			"class": "text-c"
	    }, {
            "data": "storeType",
            "defaultContent": "",
            "class": "text-c",
            "render" : function (data, type, row, meta) {
            	if(data == '1'){
					return '本地';
				}
            	else if(data == '2'){
					return '静态资源服务器';
				}
            	else if(data == '3'){
					return '网易云';
				}
            	else if(data == '4'){
					return '腾讯云1';
				}
            	else if(data == '5'){
					return '腾讯云2';
				}
            	else if(data == '6'){
					return '七牛云';
				}
            	else if(data == '7'){
					return '阿里云';
				}
            	else if(data == '8'){
					return '优刻云';
				}
            	else{
            		return '默认';
            		}
            }
	    },{
			"data": "suffix",
			"defaultContent": "",
			"class": "text-c"
		},{
			"data": "size",
			"defaultContent": "",
			"class": "text-c",
			"render":function(data, type, row, meta){
				if(data >= 1024 && data < 1048576){
					var num = new Number(data/1024);
					return num.toFixed(2).toString() + 'Kb'
				}
				else if(data >= 1048576 && data < 1073741824){
					var num = new Number(data/1048576);
					return num.toFixed(2).toString() + 'Mb'
				}
				else if(data >= 1073741824){
					var num = new Number(data/1073741824);
					return num.toFixed(2).toString() + 'Gb'
				}
				else{
					return data + 'byte';
				}
			}
		 }, {
            "data": "type",
            "defaultContent": "",
            "class": "text-c",
            "render" : function (data, type, row, meta) {
            	if(data == '1'){
					return '图片';
				}
            	else if(data == '2'){
					return '视频';
				}
            	else if(data == '3'){
					return '音频';
				}
            	else {
					return '其他';
				}
            }
		 }, {
	            "data": "bizId",
	            "defaultContent": "",
	            "class": "text-c",
	            "render" : function (data, type, row, meta) {
	            	if(isEmpty(data)){
						return '否';
					}
	            	else {
						return '是';
					}
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
           	  if(row.state == '1'){
                    btns[i] = butInfo("&#xe6de;","冻结","changeState('${pageContext.request.contextPath}/web/file/change-state','"+row.id+"',"+row.type+")");
                    i++;
           	  }
           	  btns[i] = butInfo("&#xe6e2;","删除","delEntity('${pageContext.request.contextPath}/web/file/delete','"+row.id+"')");
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