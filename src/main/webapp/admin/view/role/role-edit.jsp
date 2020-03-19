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
<title>角色管理</title>
</head>
<body>
<div class="page-container">
  <form class="form form-horizontal" id="form-admin-add">
	 <input type="hidden" name="id" value="${entity.id}">
	 <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">角色名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${entity.name}" placeholder="请输入角色名称" id="name" name="name">
		</div>
	 </div>
	

	  <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色编码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${entity.code}" placeholder="请输入角色编码" id="code" name="code">
			</div>
	  </div>
	  
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">是否启用：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width: 150px;"> <select id="selectPermissionType"
				class="select" name="state" size="1">
					<option value="1" <c:if test="${entity.state == 1}">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${entity.state == 0}">selected="selected"</c:if>>禁用</option>
			</select>
			</span>
		</div>
	 </div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="note" cols="" rows="" class="textarea"  placeholder="角色备注...500个字符以内" dragonfly="true" onKeyUp="$.Huitextarealength(this,500)">${entity.note}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
			</div>
		</div>
	
	<div id="function-content">
        
	</div>
	
	<div id="page-content" style="display:none;">
	
	</div>
	
	
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<button id="submit" class="btn btn-primary radius" type="submit" >保存</button>
		</div>
	</div>
	</form>
</div>
<script type="text/javascript">

$(function(){

});


$(function(){
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-admin-add").validate({
		rules:{
			name:{
				required:true,
			},
			code:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){;
			$(form).ajaxSubmit({
				type: 'post',
				url: "${pageContext.request.contextPath}/web/role/update" ,
                success: function(data){
                    if(data.success){
                        showSuccess("添加成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.$('.btn-refresh').click();
                        parent.location.reload();
                        parent.layer.close(index);
                    }
                    else{
                        showFail(data.message);
                    }
                },
                error: function(XmlHttpRequest, textStatus, errorThrown){
                    showError();
                }
			});
			
		}
	});
});

</script>
</body>
</html>