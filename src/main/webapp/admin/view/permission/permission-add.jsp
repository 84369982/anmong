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
<style type="text/css"> 
 
		

body,ul,li{ margin:0; padding:0; font-size:13px;}
ul,li{list-style:none;}
i{font-style: normal;}
#divselect{width:140px;  position:relative; z-index:10000;}
#divselect .cite{width:150px; height:24px;line-height:24px; display:block; color:#333; cursor:pointer;font-style:normal;
padding-left:4px; padding-right:30px;-webkit-box-sizing:border-box; box-sizing:border-box; }
#divselect ul{width:150px;border:1px solid #ddd; background-color:#ffffff; position:absolute;height:480px;overflow-y:scroll; z-index:20000; margin-top:-1px; display:none;}
#divselect  li{height:24px; line-height:24px;padding-left: 5px;}
</style>
<title>权限管理</title>
</head>
<body>
<div class="page-container">
  <form class="form form-horizontal" id="form-admin-add">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">权限类型：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width: 150px;"> <select id="selectPermissionType"
				class="select" name="type" size="1">
					<option value="1">功能</option>
					<option value="3">页面</option>
					<option value="2">菜单</option>
			</select>
			</span>
		</div>
	</div>
	
	<div id="menu-content" style="display:none;">
	    <div class="row cl">
		 <label class="form-label col-xs-4 col-sm-3">上级菜单：</label>
		 <div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width: 150px;"> 
			 <select id="selectTopmenu" class="select" name="parentId" size="1">
			   <option value="">-顶级菜单-</option>
			 </select>
			</span>
		 </div>
	  </div>
	    
	    <div id="icon-content" style="display:none;">
		   <div class="row cl">
			 <label class="form-label col-xs-4 col-sm-3">图标：</label>
			 <div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width: 150px; padding:0;"> 
				
			<div id="divselect">
			      <div class="cite">请选择</div>
			      <ul>
			       
			      </ul>
		   </div>
		   <input type="hidden" name="icon" value="" id="theHide">
				 <!-- <select id="selectTopmenu" class="select" name="parentId" size="1">
				   <option value="&#xe625;"><i class="Hui-iconfont">&#xe640;</i>主页</option>
				 </select> -->
				</span>
			 </div>
		  </div>
		</div>
		
		 <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">菜单排序：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" class="input-text" value="" placeholder="请输入菜单排序" id="sort" name="sort">
			</div>
	  </div>
	</div>
	
	 
	<div id="select-submenu-content">
     <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">所属菜单：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width: 150px;"> 
			 <select id="selectSubmenu" class="select" name="menuId" size="1">
			 </select>
			</span>
		</div>
	 </div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">是否为系统权限：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width: 150px;"> <select id="selectPermissionType"
				class="select" name="isRoot" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
			</select>
			</span>
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
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>资源名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入资源名称" id="name" name="name">
			</div>
	  </div>
	 
	  <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">资源地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入资源访问地址，顶级菜单时此处留空!" id="url" name="url">
			</div>
	  </div>
	  
	  <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">资源编码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入资源编码" id="code" name="code">
			</div>
	  </div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="note" cols="" rows="" class="textarea"  placeholder="权限备注...500个字符以内" dragonfly="true" onKeyUp="$.Huitextarealength(this,500)"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
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
	findAllSubmenu();
});


function findAllSubmenu(){
	  $.ajax({
			url:"${pageContext.request.contextPath}/web/permission/find-all-submenu",
			data:{
			},
			success:function(data) {
				if(data.success){
					if(data.collection.length > 0){
						data.collection.forEach(function(value,index,array){
							 $("#selectSubmenu").append('<option value="'+value.id+'">'+value.name+'</option>');
							})
					}
				}
				else{
					showFail(data.message);
				}
			},
			error:function(){
				showError()
			}
		  })
}

function findAllTopmenu(){
	  $.ajax({
			url:"${pageContext.request.contextPath}/web/permission/find-all-topmenu",
			data:{
			},
			success:function(data) {
				if(data.success){
					if(data.collection.length > 0){
						data.collection.forEach(function(value,index,array){
							 $("#selectTopmenu").append('<option value="'+value.id+'">'+value.name+'</option>');
							})
					}
				}
				else{
                    showFail(data.message);
				}
			},
			error:function(){
				showError()
			}
		  })
}

$("#selectPermissionType").change(function(){
	var selectValue = $("#selectPermissionType").val();
	if(selectValue == '1'){
		$("#function-content").css("display","block");
		$("#page-content").css("display","none");
		$("#menu-content").css("display","none");
		$("#select-submenu-content").css("display","block");
	}
	if(selectValue == '3'){
		$("#function-content").css("display","none");
		$("#page-content").css("display","block");
		$("#menu-content").css("display","none");
		$("#select-submenu-content").css("display","block");
	}
	if(selectValue == '2'){
		$("#function-content").css("display","none");
		$("#page-content").css("display","none");
		$("#menu-content").css("display","block");
		$("#select-submenu-content").css("display","none");
		var topmenuNum = $("#selectTopmenu option").length;
		if(topmenuNum <= 1){
			findAllTopmenu();
			//如果是顶级菜单，则显示图标选项框
			var topmenu = $("#selectTopmenu").val();
			if(isEmpty(topmenu)){
				initIconSelect();
				$("#icon-content").css("display","block");
			}
		}
		
			
	}
});	


$("#selectTopmenu").change(function(){
	var topmenu = $("#selectTopmenu").val();
	if(isEmpty(topmenu)){
		//initIconSelect();
		$("#icon-content").css("display","block");
	}
	else{
		$("#icon-content").css("display","none");
	}

});

$(function(){
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-admin-add").validate({
		rules:{
			sort:{
				isInteger:true
			},
			name:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			var icon = $("#theHide").val();
			$("#theHide").val("&#"+icon);
			$(form).ajaxSubmit({
				type: 'post',
				url: "${pageContext.request.contextPath}/web/permission/add" ,
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


function initIconSelect(){
	var data = [
		{"icon":"&#xe61d;","text":"管理"},{"icon":"&#xe720;","text":"阅读"},{"icon":"&#xe726;","text":"电源"},
		{"icon":"&#xe728;","text":"计时器"},{"icon":"&#xe625;","text":"首页"},{"icon":"&#xe66f;","text":"语音"},
		{"icon":"&#xe62e;","text":"系统"},{"icon":"&#xe633;","text":"帮助"},{"icon":"&#xe646;","text":"图片"},
		{"icon":"&#xe681;","text":"分类"},{"icon":"&#xe637;","text":"任务管理"},{"icon":"&#xe692;","text":"意见反馈"},
		{"icon":"&#xe623;","text":"日志"},{"icon":"&#xe63c;","text":"管理"},{"icon":"&#xe627;","text":"订单"},
		{"icon":"&#xe612;","text":"图片"},{"icon":"&#xe72d;","text":"模版"},{"icon":"&#xe60d;","text":"用户"},
		{"icon":"&#xe705;","text":"个人中心"},{"icon":"&#xe602;","text":"用户ID"},{"icon":"&#xe70d;","text":"执业证"},
		{"icon":"&#xe62b;","text":"群组"},{"icon":"&#xe653;","text":"站长"},{"icon":"&#xe62d;","text":"管理员"},
		{"icon":"&#xe643;","text":"公司"},{"icon":"&#xe6b4;","text":"会员卡"},{"icon":"&#xe6d0;","text":"客服"},
		{"icon":"&#xe72c;","text":"版主"},{"icon":"&#xe6d3;","text":"皇冠"},{"icon":"&#xe6aa;","text":"分享"},
		{"icon":"&#xe693;","text":"朋友圈"},{"icon":"&#xe694;","text":"微信"},{"icon":"&#xe67b;","text":"QQ"},
		{"icon":"&#xe6c8;","text":"QQ空间"},{"icon":"&#xe6da;","text":"微博"},{"icon":"&#xe715;","text":"更多"},
		{"icon":"&#xe649;","text":"喜欢"},{"icon":"&#xe680;","text":"已关注"},{"icon":"&#xe622;","text":"评论"},
		{"icon":"&#xe686;","text":"累计评价"},{"icon":"&#xe68a;","text":"消息"},{"icon":"&#xe61b;","text":"收藏"},
		{"icon":"&#xe630;","text":"收藏-选中"},{"icon":"&#xe697;","text":"点赞"},{"icon":"&#xe62f;","text":"通知"},
		{"icon":"&#xe6b5;","text":"积分"},{"icon":"&#xe6ce;","text":"订阅"},{"icon":"&#xe6cd;","text":"提示"},
		{"icon":"&#xe621;","text":"数据统计"},{"icon":"&#xe61e;","text":"数据统计"},{"icon":"&#xe61a;","text":"统计"},
		{"icon":"&#xe618;","text":"柱状统计"},{"icon":"&#xe61c;","text":"线状统计"},{"icon":"&#xe6cf;","text":"排行榜"},
		{"icon":"&#xe669;","text":"物流"},{"icon":"&#xe670;","text":"购物车"},{"icon":"&#xe628;","text":"信用卡"},
		{"icon":"&#xe6bb;","text":"礼物"},{"icon":"&#xe6b7;","text":"红包"},{"icon":"&#xe6f1;","text":"link"},
		{"icon":"&#xe6ef;","text":"剪切"},{"icon":"&#xe6ea;","text":"复制"},{"icon":"&#xe6eb;","text":"粘贴"},
		{"icon":"&#xe6c7;","text":"电话"},{"icon":"&#xe696;","text":"iphone手机"},{"icon":"&#xe64f;","text":"PC"},
		{"icon":"&#xe682;","text":"扫一扫"},{"icon":"&#xe683;","text":"搜索"},{"icon":"&#xe690;","text":"时间"},
		{"icon":"&#xe6c2;","text":"拍摄"},{"icon":"&#xe6c3;","text":"热销"},{"icon":"&#xe6c4;","text":"上新"},
		{"icon":"&#xe6c9;","text":"定位"},{"icon":"&#xe64a;","text":"苹果"},{"icon":"&#xe6a2;","text":"android"},
		{"icon":"&#xe70a;","text":"我的评价"},{"icon":"&#xe6f5;","text":"无序列表"},{"icon":"&#xe667;","text":"列表"}
	];
	
	
	for(var i=0;i<data.length;i++){
		var getVal = data[i].icon.substr(2);
		var li = '<li value='+getVal+'>'+
				'<i class="Hui-iconfont">'+data[i].icon+'</i>&nbsp;'+data[i].text+"</li>";
	
	$('#divselect ul').append(li);
	}
	$('#divselect ul li').click(function(){
		$('#divselect .cite').html($(this).html());
		$('#theHide').val($(this).attr('value'));
		$("#divselect ul").hide();
	})
	setIcon({"icon":"&#xe6bb;","text":"礼物"});
	function setIcon(e){
		var getVal = e.icon.substr(2);
		var li = '<li value='+getVal+'>'+
		'<i class="Hui-iconfont">'+e.icon+'</i>&nbsp;'+e.text+"</li>";
		for(var i=0;i<data.length;i++){
			if(data[i].icon == e.icon ){
				$('#divselect .cite').html('').append(li);
				$('#theHide').val(getVal);
				break;
			}
		}
	}
}

$('#divselect .cite').click(function(){
	var ul = $('#divselect ul');
	if(ul.css("display")=="none"){
            ul.slideDown("fast");
    }else{
        ul.slideUp("fast");
    }
})



</script>
</body>
</html>