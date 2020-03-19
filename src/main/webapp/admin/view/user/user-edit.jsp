<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%-- <%@include file="/admin/view/main/resource.jsp" %> --%>
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/css/bootstrap.min.css">
<link href="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/head/cropper.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/head/sitelogo.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/admin/static/thirdparty/lib/citySelect/css/city-select.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/admin/static/thirdparty/lib/PostbirdAlertBox/css/postbirdAlertBox.min.css">
<style type="text/css">
.avatar-btns button {
	height: 35px;
}
</style>
<script src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/head/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/head/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/head/cropper.js"></script>
<script src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/head/sitelogo.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/self/js/public.js"></script>
<script src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/headimg/head/html2canvas.min.js" type="text/javascript" charset="utf-8"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/citySelect/js/citydata.min.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/citySelect/js/newcitydata.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/citySelect/js/citySelect-1.0.3.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/My97DatePicker/4.8/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/lib/PostbirdAlertBox/js/postbirdAlertBox.min.js"></script>
</head>
<body>
<div class="page-container">

<div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<!--<form class="avatar-form" action="upload-logo.php" enctype="multipart/form-data" method="post">-->
			<form class="avatar-form">
				<div class="modal-header">
					<button class="close" data-dismiss="modal" type="button">&times;</button>
					<h4 class="modal-title" id="avatar-modal-label">上传图片</h4>
				</div>
				<div class="modal-body">
					<div class="avatar-body">
						<div class="avatar-upload">
							<input class="avatar-src" name="avatar_src" type="hidden">
							<input class="avatar-data" name="avatar_data" type="hidden">
							<label for="avatarInput" style="line-height: 35px;">图片上传</label>
							<button class="btn btn-danger"  type="button" style="height: 35px;" onClick="$('input[id=avatarInput]').click();">请选择图片</button>
							<span id="avatar-name"></span>
							<input class="avatar-input hide" id="avatarInput" name="avatar_file" type="file"></div>
						<div class="row">
							<div class="col-md-9">
								<div class="avatar-wrapper"></div>
							</div>
							<div class="col-md-3">
								<div class="avatar-preview preview-lg" id="imageHead"></div>
								<!--<div class="avatar-preview preview-md"></div>
						<div class="avatar-preview preview-sm"></div>-->
							</div>
						</div>
						<div class="row avatar-btns">
							<div class="col-md-4">
								<div class="btn-group">
									<button class="btn btn-danger fa fa-undo" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees"> 向左旋转</button>
								</div>
								<div class="btn-group">
									<button class="btn  btn-danger fa fa-repeat" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees"> 向右旋转</button>
								</div>
							</div>
							<div class="col-md-5" style="text-align: right;">								
								<button class="btn btn-danger fa fa-arrows" data-method="setDragMode" data-option="move" type="button" title="移动">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;setDragMode&quot;, &quot;move&quot;)">
								</span>
							  </button>
							  <button type="button" class="btn btn-danger fa fa-search-plus" data-method="zoom" data-option="0.1" title="放大图片">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, 0.1)">
								  <!--<span class="fa fa-search-plus"></span>-->
								</span>
							  </button>
							  <button type="button" class="btn btn-danger fa fa-search-minus" data-method="zoom" data-option="-0.1" title="缩小图片">
								<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, -0.1)">
								  <!--<span class="fa fa-search-minus"></span>-->
								</span>
							  </button>
							  <button type="button" class="btn btn-danger fa fa-refresh" data-method="reset" title="重置图片">
									<span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;reset&quot;)" aria-describedby="tooltip866214">
							   </button>
							</div>
							<div class="col-md-3">
								<button class="btn btn-danger btn-block avatar-save fa fa-save" type="button" data-dismiss="modal"> 保存修改</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

  <form class="form-horizontal" id="form-admin-add">
  
       <input value="${entity.city}" type="hidden" id="city" name="city">
       <input type="hidden" id="fileId"  name="fileId" value="">
       <input type="hidden" id="headUrl"  name="headUrl" value="">
  
	 <div style="text-align:center">
	    <div class="user_pic" style="margin: 10px;">
	      <img src="${entity.headUrl}" width="300px" height="300px">
        </div>
	    <button type="button" class="btn btn-info"  data-toggle="modal" data-target="#avatar-modal" style="margin: 10px;">修改头像</button>
	 </div>
	 
	  <div class="form-group">
	   <label class="col-sm-2 control-label">昵称</label>
	   <div class="col-sm-8">
	     <input type="text" class="form-control" id="nickname" placeholder="请输入昵称" name="nickname" value="${entity.nickname}">
	   </div>
	 </div>
	 <div class="form-group">
		 <label class="col-sm-2 control-label">城市</label>
		 <div class="col-sm-8">
			<div class="city-select" id="single-select-1">
			    <div class="city-info">
					<div class="city-input">
						<input type="text" class="input-search">
					</div>
				</div>
			</div>
		 </div>
	 </div>
	    <div class="form-group">
	   <label class="col-sm-2 control-label" name="sex">性别</label>
	   <div class="col-sm-8">
	     <select class="form-control" name ="sex" id="sex">
	      <c:if test="${empty entity.sex}">
		   <option value="0" selected="selected">保密</option>
		  </c:if>
		  <option value="1" <c:if test="${entity.sex == 1}">selected="selected"</c:if>>男</option>
		  <option value="2" <c:if test="${entity.sex == 2}">selected="selected"</c:if>>女</option>
		  <c:if test="${not empty entity.sex}">
		   <option value="3" <c:if test="${entity.sex == 3}">selected="selected"</c:if>>保密</option>
		  </c:if>
		 </select>
	   </div>
	 </div>
	    <div class="form-group">
	   <label class="col-sm-2 control-label">生日</label>
	   <div class="col-sm-8">
	     <fmt:formatDate value="${entity.birthday}" pattern="yyyy-MM-dd" var="d" />
	     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" class="form-control Wdate" id="birthday" placeholder="请输入生日" name="birthday" value="${d}">
	   </div>
	 </div>
	    <div class="form-group">
	   <label class="col-sm-2 control-label">简介</label>
	   <div class="col-sm-8">
	     <textarea id="summary" class="form-control" placeholder="请输入简介" name="summary" rows="3">${entity.summary}</textarea>
	   </div>
	 </div>


	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<button id="submit" class="btn btn-primary radius" type="button" >保存</button>
		</div>
	</div>
	</form>
</div>
<script type="text/javascript">

$(function(){
	// 单选
	var singleSelect1 = $('#single-select-1').citySelect({
		dataJson: cityData,
		multiSelect: false,
		whole: true,
		shorthand: true,
		search: true,
		searchPlaceholder:'上次保存:${entity.city}',
		hotCity: ['北京市', '上海市', '广州市', '深圳市', '南京市', '杭州市', '天津市', '重庆市', '成都市', '青岛市', '苏州市', '无锡市', '常州市', '温州市', '武汉市', '长沙市', '石家庄市', '南昌市', '三亚市', '合肥市'],
		onInit: function () {
			/* console.log(this) */
		},
		onTabsAfter: function (target) {
			/* console.log(target) */
		},
		onCallerAfter: function (target, values) {
			/* console.log(JSON.stringify(values)) */
			$("#city").val(values.name);
		}
	});
	
	singleSelect1.setCityVal('${entity.city}');
	
	

});


	$('#avatarInput').on('change', function(e) {
		var filemaxsize = 1024 * 6;//6M
		var target = $(e.target);
		var Size = target[0].files[0].size / 1024;
		if(Size > filemaxsize) {
			alert('图片过大，请重新选择!');
			$(".avatar-wrapper").childre().remove;
			return false;
		}
		if(!this.files[0].type.match(/image.*/)) {
			alert('请选择正确的图片!')
		} else {
			var filename = document.querySelector("#avatar-name");
			var texts = document.querySelector("#avatarInput").value;
			var teststr = texts; //你这里的路径写错了
			testend = teststr.match(/[^\\]+\.[^\(]+/i); //直接完整文件名的
			filename.innerHTML = testend;
		}
	
	});

	$(".avatar-save").on("click", function() {
		var img_lg = document.getElementById('imageHead');
		// 截图小的显示框内的内容
		html2canvas(img_lg, {
			allowTaint: true,
			taintTest: false,
			onrendered: function(canvas) {
				canvas.id = "mycanvas";
				//生成base64图片数据
				var dataUrl = canvas.toDataURL("image/jpeg");
				var newImg = document.createElement("img");
				newImg.src = dataUrl;
				imagesAjax(dataUrl)
			}
		});
	})
	
	function imagesAjax(src) {
		var data = new FormData();
		var blobData = convertImgDataToBlob(src); //convertImgDataToBlob base64 转化为 blob 数据
		var file = $("#avatarInput").get(0).files[0];
		
		data.append("userId","${entity.id}");
		data.append("name","用户头像");
		data.append("type",1);											
		data.append("module","user");						
		data.append("file",blobData,file.name);
		$.ajax({
			url: "${pageContext.request.contextPath}/upload/file/upload",
			data: data,
			type: "POST",
			processData: false,
			contentType: false,
			success: function(re) {
				if(re.success) {
					$('.user_pic img').attr('src',src );
					$("#headUrl").val(re.collection[0].url);
					$("#fileId").val(re.collection[0].id);
				}
				else{
					showMsg(re.message);
				}
			},
			error:function(){
				showMsg('出错了，请稍后再试！');
			}
		});
	}
	
	/*
	 把 base64 转化为blob 数据
	 */
	function convertImgDataToBlob(base64Data,fileName) {  
     var format = "image/jpeg";  
     var base64 = base64Data;  
     var code = window.atob(base64.split(",")[1]);  
     var aBuffer = new window.ArrayBuffer(code.length);  
     var uBuffer = new window.Uint8Array(aBuffer);  
     for(var i = 0; i < code.length; i++){  
         uBuffer[i] = code.charCodeAt(i) & 0xff ;  
     }  

     var blob=null;  
     try{  
         blob = new Blob([uBuffer], {type : format});  
     }  
     catch(e){  
         window.BlobBuilder = window.BlobBuilder ||  
         window.WebKitBlobBuilder ||  
         window.MozBlobBuilder ||  
         window.MSBlobBuilder;  
         if(e.name == 'TypeError' && window.BlobBuilder){  
             var bb = new window.BlobBuilder();  
             bb.append(uBuffer.buffer);  
             blob = bb.getBlob("image/jpeg");  

         }  
         else if(e.name == "InvalidStateError"){  
             blob = new Blob([aBuffer], {type : format});  
         }  
         else{  

         }  
     }  
     return blob;  
      
 } 
	
  function showMsg(msg) {
        PostbirdAlertBox.alert({
            'title': '提示',
            'content': msg,
            'okBtn': '好的',
            'contentColor': 'black',
            'onConfirm': function () {
               
            }
        });
    }
  
  $("#submit").click(function(){
		$.ajax({
			url: "${pageContext.request.contextPath}/web/user/update",
			data: {
				headUrl:$("#headUrl").val(),
				nickname:$("#nickname").val(),
				city:$("#city").val(),
				sex:$("#sex").val(),
				birthday:$("#birthday").val(),
				summary:$("#summary").val(),
				fileId:$("#fileId").val()
			},
			type: "POST",
			success: function(re) {
				if(re.success) {
					showMsg('修改成功');
				}
				else{
					showMsg(re.message);
				}
			},
			error:function(){
				showMsg('出错了，请稍后再试！');
			}
		});
  })
	

</script>
</body>
</html>