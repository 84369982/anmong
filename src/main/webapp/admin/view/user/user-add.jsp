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
<title>用户管理</title>
</head>
<body>
<div class="page-container">
  <form class="form form-horizontal" id="form-admin-add">
	
	 <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>账号：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" placeholder="请输入6-16位账号，只能为字母或数字" id="username" name="username">
		</div>
	 </div>
	

	  <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入手机号" id="phone" name="phone">
			</div>
	  </div>
	  
	    <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>验证码：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" value="" placeholder="请输入验证码" id="authcode" name="authcode">
				
			</div>
			<div class="formControls col-xs-4 col-sm-4">
				<button id="getAuthcode" class="btn btn-default radius" type="button" >获取验证码</button>
			</div>
			
	  </div>
	  
	   <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" value="" placeholder="请输入6-16位密码，只能为字母或数字" id="password" name="password">
			</div>
	  </div>
	  
	   <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>确认密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" value="" placeholder="请再次输入密码" id="confirmPassword" name="confirmPassword">
			</div>
	  </div>
	  
	  	 <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">是否启用：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width: 150px;"> <select id="selectPermissionType"
				class="select" name="state" size="1">
					<option value="1">启用</option>
					<option value="0">禁用</option>
			</select>
			</span>
		</div>
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
			username:{
				required:true,
				isNumberOrLetter:true,
				minlength:6,
				maxlength:16,
			},
			phone:{
				required:true,
				isMobile:true,
			},
			authcode:{
				required:true,
				isInteger:true,
			},
			password:{
				required:true,
				isNumberOrLetter:true,
				minlength:6,
				maxlength:16,
			},
			confirmPassword:{
				required:true,
				isNumberOrLetter:true,
				minlength:6,
				maxlength:16,
			    equalTo:"#password",
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){;
			$(form).ajaxSubmit({
				type: 'post',
				url: "${pageContext.request.contextPath}/web/user/add" ,
				success: function(data){
					if(data.success){
						showSuccess("添加成功!"); 
						setTimeout(function(){},2000);
						var index = parent.layer.getFrameIndex(window.name);
						parent.$('.btn-refresh').click();
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

var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数
var code = ""; //验证码
$("#getAuthcode").click(function() {
	var phone  = $("#phone").val();
	if(isPhone(phone)){
		   curCount = count;
		 //设置button效果，开始计时
		 	$("#getAuthcode").addClass("disabled");
		 	$("#getAuthcode").text( + curCount + "秒后再获取");
		 	InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
		 //向后台发送处理数据
			$.ajax({
				type:"get",
				url:"/public/sms/send",
				data:{
					phone:phone,
					type:1
				},
				success:function(data){
					if(data.success){  //手机号可以注册
						showSuccess("发送成功!");
					}else{  //手机号已被注册
						showFail(data.message);
					}
				},
				error:function(){
					showError();
				}
			});	
	}
	else{
		showError("请输入正确的手机号!");
	}

});

//timer处理函数
function SetRemainTime() {
	if (curCount == 0) {                
		window.clearInterval(InterValObj);//停止计时器
		$("#getAuthcode").removeClass("disabled");//启用按钮
		$("#getAuthcode").text("重新发送验证码");
		code = ""; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效    
	}
	else {
		curCount--;
		$("#getAuthcode").text( + curCount + "秒后再获取");
	}
}

</script>
</body>
</html>