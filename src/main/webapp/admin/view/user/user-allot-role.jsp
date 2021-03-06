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
<title>角色授予</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/static/thirdparty/css/ztree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/js/ztree_v3/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/static/thirdparty/js/ztree_v3/jquery.ztree.exhide.min.js"></script>
</head>
<body>

<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
</div>

<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<button id="submit" class="btn btn-primary radius" >保存</button>
		</div>
</div>
<br>
<div>
<i class="Hui-tags-icon Hui-iconfont">&#xe64b;</i><label>注:分配了角色后，该角色的用户需重新登录才能使分配的权限生效！</label>
</div>

	
 
<script type="text/javascript">
var ownedList = JSON.parse('${owned}');

$(function(){
	var roleList = JSON.parse('${list}');
	var zNodes = [];
	roleList.forEach(function(value,index,array){
		var node = {}
		node.id = value.id;
		node.name = value.name;

		//判断已拥有的角色，有则勾选上
		var isOwned = false;
		ownedList.forEach(function(value2,index2,array2){
			if(value.id == value2.roleId){
				isOwned = true;
				return false;
			}
		})
		if(isOwned){
			node.checked = true;
		}
		else{
			node.checked = false;
		}
		node.open = true;
		zNodes.push(node);
		
	})
	
	var setting = {	
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	$.fn.zTree.init($("#treeDemo"), setting, zNodes);		

});

/**
 * 遍历以前的数据和现在重新选的来确定增加和删除的数据。
 */
$("#submit").click(function(){
	var ztree = $.fn.zTree.getZTreeObj("treeDemo");
	var ckeckedList = ztree.getCheckedNodes(true);
	var addList = getAddList(ckeckedList,ownedList);
	var delList = getDelList(ckeckedList,ownedList);
	var data = {addList:addList,delList:delList,userId:"${userId}"};
	  $.ajax({
			url:"${pageContext.request.contextPath}/web/user-role/allot-role",
			type:"POST",
			data:JSON.stringify(data),
			contentType: "application/json",
			dataType: "json",
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
		  })

});

/*
 * 选中列表有而原有列表中没有，则是需要添加的数据
 */
function getAddList(ckeckedList,ownedList){
	var addList = [];
	ckeckedList.forEach(function(value,index,array){
		var isNeedAdd = true;
		ownedList.forEach(function(value2,index2,array2){
			if(value.id == value2.permissionId){
				isNeedAdd = false;
				return false;
			}
		})
		if(isNeedAdd){
			addList.push(value.id);
		}
	})
	return addList;
}
/*
 * 原有列表有而选中列表中没有，则是需要删除的数据
 */
function getDelList(ckeckedList,ownedList){
	var delList = [];
	ownedList.forEach(function(value,index,array){
		var isNeedDel = true;
		ckeckedList.forEach(function(value2,index2,array2){
			if(value.permissionId == value2.id){
				isNeedDel = false;
				return false;
			}
		})
		if(isNeedDel){
			delList.push(value.id);
		}
	})
	return delList;
}
</script>
</body>
</html>