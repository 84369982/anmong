/**
 * 通用上传
 * @param object:文件选择框元素
 * userId:用户id
 * name:文件说明(例:用户头像)
 * type:文件类型:1.图片;2.视频3.音频;4.3d扫描数据
 * module:模块(例:user)
 * elementId:html元素id
 * urlId:存储url的html标签id(input元素)
 * fileId:存储fileId的html标签id(input元素)
 * @returns
 */
function upload(object,userId,name,type,module,elementId,urlId,fileId){
	if(object.val() == ''){//如果没有选择文件则不触发
		showFail("请选择文件!");
	    return false;
	}
	var data = new FormData();
	var file = object.get(0).files[0];
	data.append("userId",userId);
	data.append("name",name);
	data.append("type",type);											
	data.append("module",module);						
	data.append("file",file,file.name);
	$.ajax({
		url: "/upload/file/upload",
		data: data,
		type: "POST",
		processData: false,
		contentType: false,
		async:false,
		success: function(re) {
			var imageDIV = $("#"+elementId);
//			console.log(elementId);
			if(re.success) {
				if(imageDIV.length < 1){
					if(type == 1){
						var template = ' <div id = "'+elementId+'" style="text-align:left;margin-top:10px;">'+
						  '<image src = "'+re.collection[0].url+'" width = "100px" height = "100px" style="margin: 0 auto;"/>'+
								'</div> ';
//						object.append(template);
						object.parent().parent().append(template);
						$("#"+urlId).val(re.collection[0].url);
						$("#"+fileId).val(re.collection[0].id);
//						console.log(1);
					}
					else{
						var template = ' <div id = "'+elementId+'" style="text-align:left;margin-top:10px;">'+
						  '<p style="color:green;">上传成功</p>'+
								'</div> ';
//						object.html(template);
						object.parent().parent().append(template);
//						console.log(2);
						$("#"+urlId).val(re.collection[0].url);
						$("#"+fileId).val(re.collection[0].id);
					}
				}
				else{
					//生成图片预览
					if(type == 1){
						var template = '<image src = "'+re.collection[0].url+'" width = "100px" height = "100px" style="margin: 0 auto;"/>';
						imageDIV.html(template);
						$("#"+urlId).val(re.collection[0].url);
						$("#"+fileId).val(re.collection[0].id);
					}
					else{
						var template = ' <div id = "'+elementId+'" style="text-align:left;margin-top:10px;">'+
						  '<p style="color:green;">上传成功</p>'+
								'</div> ';
						imageDIV.html(template);
						$("#"+urlId).val(re.collection[0].url);
						$("#"+fileId).val(re.collection[0].id);
					}
				}
		
			}
			else{
				if(imageDIV.length < 1){
					var template = ' <div id = "'+elementId+'" style="text-align:left;margin-top:10px;">'+
					  '<p style="color:red;">文件上传失败，请重新上传</p>'+
							'</div> ';
					object.parent().parent().append(template);
					showFail(re.message);
				}
				else{
					var template = ' <div id = "'+elementId+'" style=text-align:left;margin-top:10px;">'+
					  '<p style="color:red;">文件上传失败，请重新上传</p>'+
							'</div> ';
					imageDIV.html(template);
					showFail(re.message);
				}
			
			}
		},
		error:function(){
			var imageDIV = $("#"+elementId);
			if(imageDIV.length < 1){
				var imageDIV = $("#"+elementId);
				var template = ' <div id = "'+elementId+'" style="text-align:left;margin-top:10px;">'+
				  '<p style="color:red;">文件上传失败，请重新上传</p>'+
						'</div> ';
				object.append(template);
				showError('出错了，请稍后再试！');
			}
			else{
				var imageDIV = $("#"+elementId);
				var template = ' <div id = "'+elementId+'" style="text-align:left;margin-top:10px;">'+
				  '<p style="color:red;">文件上传失败，请重新上传</p>'+
						'</div> ';
				imageDIV.html(template);
				showError('出错了，请稍后再试！');
			}
		
		}
	});
}