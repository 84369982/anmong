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

.box1{
  position: absolute;
            top:50%;
            left:50%;
            width:100%;
            transform:translate(-50%,-50%);
            text-align: center;
}
</style>
<title>文件管理</title>
</head>
<body>
<div class="page-container box1" >
   <div id="imageContent" style="display:none">
    <img id="image" alt="" src="">
   </div>
   <div id="videoContent" style="display:none">
    <video id="video" src="" controls="controls">
	您的浏览器不支持 直接播放视频。
	</video>
   </div>
   <div id="audioContent" style="display:none">
    <audio id="audio" src="" controls="controls">
	 您的浏览器不支持 直接播放音频。
	</audio>
   </div>
</div>
<script type="text/javascript">

$(function(){
	var url = getUrlParameter("url");
	var type = getUrlParameter("type");
  	if(type == '1'){
  		$("#image").attr('src',url);
  		$("#imageContent").css('display','block');
  		$("#videoContent").css('display','none');
  		$("#audioContent").css('display','none');
	}
	if(type == '2'){
		$("#video").attr('src',url);
		$("#imageContent").css('display','none');
  		$("#videoContent").css('display','block');
  		$("#audioContent").css('display','none');
	}
	if(type == '3'){
		$("#audio").attr('src',url);
		$("#imageContent").css('display','none');
  		$("#videoContent").css('display','none');
  		$("#audioContent").css('display','block');
	}
	
});

function getUrlParameter(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

</script>
</body>
</html>