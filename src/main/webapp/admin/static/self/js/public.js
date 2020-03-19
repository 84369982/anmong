/**
 * 验证是否为空,null,空格等
 **/
function isEmpty(str) {
	if (str == "") {
		return true;
	}
	if (str == null) {
		return true;
	}
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}

/**
 * 验证是否为价格数据
 **/
function validateMoney(money){
	 var reg = new RegExp("^[0-9]([0-9]+)?(\.[0-9]{1,2})?$");
	 if(reg.test(money)){
		 return true;
	 }
	 else{
		 return false;
	 }
}
/**
 * 验证是否为数字
 **/
function isNumber(number){
	var reg = new RegExp("^[0-9]+$");
	if(reg.test(number)){
		return true;
	}
	else{
		return false;
	}
	
}
//监听ajax开始事件
$(document).ajaxStart(function(event,request, settings) {
	running();
	 }); 

//监听ajax完成事件
$(document).ajaxComplete(function(event,request, settings) {
	finish();
	 });   

function running(){
	$("#submit").addClass("disabled");
	$("#submit").text('提交中...');
	
}

function finish(){
	$("#submit").removeClass("disabled");
	$("#submit").text("保存");
}

function delEntity(url,id){
	layer.confirm("确定要删除该数据？",function(index){
		$.ajax({
			url:url,
			type:'post',
			data:{"id":id},
			dataType:"json",
			cache:false,
			async:false,
			success:function(data){
				if(data.success){
					layer.msg('成功删除!', {icon:1,time:2000}); 
					reloadTable();
					layer.close(index);
				}else{
					layer.msg(data.message, {time: 20000,btn: ['知道了']}); 
				}
			},error:function(){
				layer.msg("内部出错啦，请稍后再试!", {time: 20000,btn: ['知道了']});
			}
		});
	});
}

function changeState(url,id,state){
	layer.confirm("确定要修改状态？",function(index){
		$.ajax({
			url:url,
			type:'post',
			data:{
				"id":id,
				"state":state
				},
			dataType:"json",
			cache:false,
			async:false,
			success:function(data){
				if(data.success){
					layer.msg('修改成功!', {icon:1,time:2000}); 
					reloadTable();
					layer.close(index);
				}else{
					layer.msg(data.message, {time: 20000,btn: ['知道了']}); 
				}
			},error:function(){
				layer.msg("内部出错啦，请稍后再试!", {time: 20000,btn: ['知道了']});
			}
		});
	});
}

function isPhone(phone){
	var reg = new RegExp("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
	if(reg.test(phone)){
		return true;
	}
	else{
		return false;
	}
}

function showSuccess(message){
	if(isEmpty(message)){
		message = "操作成功!"
	}
	layer.msg(message, {time: 2000,btn:['知道了'],icon:1}); 
}

function showFail(message){
	if(isEmpty(message)){
		message = "操作失败!"
	}
	layer.msg(message, {time: 4000,btn:['知道了']}); 
}

function showError(message){
	if(isEmpty(message)){
		message = "出错啦，请稍后再试!"
	}
	layer.msg(message, {time: 4000,btn:['知道了']}); 
}

function getUrlParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

/**
 * 日期格式化
 * @param fmt
 * @returns {*}
 */
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}

function getUrl(){
    //获取当前网址，如： http://localhost:8080/Tmall/index.jsp
    var curWwwPath=window.document.location.href;

    //获取主机地址之后的目录如：/Tmall/index.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);

    //获取主机地址，如： http://localhost:8080
    var localhostPaht=curWwwPath.substring(0,pos);
    return localhostPaht;
}

function getHost(){
    var localhostPath = location.hostname;
    var port = location.port;
    if (!isEmpty(port)){
        localhostPath +=':' + port;
    }
    return localhostPath;
}

/**
 * 从数组最后读取且正序的分页函数
 * @param pageNO 页号
 * @param pageSize 每页内容大小
 * @param list 初始数据
 */
function pageResultReverseASC(pageNO,pageSize,list) {
	//总页数
	var totalPage = 0;
	//起始位置索引
	var startIndex = 0;
	//分页后数据
	var pageResult = [];
	if (pageSize == null || pageSize <= 0){
        pageSize = 10;
	}
	if (pageNO == null || pageNO <= 0){
		pageNO = 1;
	}
	if (list == null){
        return pageResult;
	}
    var length = list.length;
	//计算总页数
    var mod = length % pageSize;
	if (mod == 0){
        totalPage = length % pageSize;
	}
	else {
        totalPage = parseInt(length/pageSize) +1;
	}
	if (pageNO > totalPage){
        pageNO = totalPage;
	}
	if (length <= pageSize){
        return list;
	}
    startIndex = length - (pageNO * pageSize - pageSize) -pageSize;
    if (startIndex < 0){
        startIndex = 0;
    }
    if (pageNO == totalPage){
        pageSize = length % pageSize;
    }
	for (var i = 0;i < pageSize;i++){
        pageResult.push(list[startIndex]);
        startIndex++;
	}
	return pageResult;

}

function saveMessage(friendId,message) {
    var messageList = JSON.parse(window.localStorage.getItem("message-"+friendId));

    if (messageList != null && messageList.length >= 100){
        messageList.splice(0,1);
	}
    if(messageList == null){
        messageList = [];
	}
    messageList.push(message);
    window.localStorage.setItem("message-"+friendId,JSON.stringify(messageList));
}