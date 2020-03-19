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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/admin/static/self/css/admin.css" />
<title>安梦网后台管理系统</title>
<style>
.personMessages .header_img{background:url(${user.headUrl}) center center no-repeat;background-size:cover;}
</style>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/aboutHui.shtml">安梦网后台管理系统</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a> 
			<!-- <span class="logo navbar-slogan f-l mr-10 hidden-xs">v3.1</span>  -->
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav class="nav navbar-nav">
				<ul class="cl">
					
			</ul>
		</nav>
		<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
			<ul class="cl">
				<li>欢迎您 </li>
				<li class="dropDown dropDown_hover">
					<a href="#" class="dropDown_A">${user.username} <i class="Hui-iconfont">&#xe6d5;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" onclick="layer_show('修改密码','${pageContext.request.contextPath}/admin/view/user/user-change-password.jsp','800','500')" href="javascript:void(0)">修改密码</a></li>
						<li><a href="javascript:logout()">退出</a></li>
				</ul>
			</li>
				<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
				<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
						<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
						<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
						<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>
</header>
<!-- 左边 -->
<aside class="Hui-aside">
	<div class="personMessages">
		<div class="header_img">
			<!-- <img src="${user.headUrl}" id="headImg"/>  -->
		</div>
		<h4>${user.nickname}</h4>
		<div class="menu_dropdown bk_2">
           <a data-href="${pageContext.request.contextPath}/web/user/find-one" data-title="编辑个人信息" href="javascript:void(0)">我的主页</a>
	    </div>
	</div>
	
	<div class="menu_dropdown bk_2">
	<c:forEach var="menu" items="${menuList}">
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">${menu.icon}</i> ${menu.name}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				    <c:forEach var="subMenu" items="${menu.subMenuList}">
					<li><a data-href="${subMenu.url}" data-title="${subMenu.name}" href="javascript:void(0)">${subMenu.name}</a></li>
					</c:forEach>
			</ul>
		</dd>
	</dl>
   </c:forEach>
</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active">
					<span title="我的桌面" data-href="${pageContext.request.contextPath}/admin/view/main/welcome.jsp">我的桌面</span>
					<em></em></li>
		</ul>
	</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="${pageContext.request.contextPath}/admin/view/main/welcome.jsp"></iframe>
	</div>
</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
</ul>
</div>
<!--_footer 作为公共模版分离出去-->


<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">

function setHeadImg(){ //未使用
	var imgSrc = $(".header_img img").attr("src");
	console.log(imgSrc);
	getImage(imgSrc,function(w,h){
		console.log(w,h);
		if(w>h){
			console.log(1);
			$('.header_img img').css({'height':'100%'});
		}else{
			$('.header_img img').css({'width':'100%'});
		}
	});
	function getImage(url,callback){
		var img = new Image();
		img.src = url;
		if(img.complete){//调用缓存
		    callback(img.width, img.height);
		}else{
			img.onload = function(){
				callback(img.width, img.height);
			}
        }
	}
	
}
function logout() { 
    $.get(
     "${pageContext.request.contextPath}/web/auth/logout",
     {},
     function(data){
   	  if(data.success){
   		  window.location.href="${pageContext.request.contextPath}/admin/login.html";
   	  }
   	  else{
   		  showFail(data.message);
   	  }
     }
    )
	
	}; 
	

</script> 
</body>
</html>
