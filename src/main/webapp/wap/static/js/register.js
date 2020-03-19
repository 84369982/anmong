/*
 * 注册 , 重复密码提交才判断，手机号，密码类似 失去焦点判断，实则 是其他焦点事件判断。
 */
  //用户名验证  6-16位 字母数字
  var reName = /^[a-zA-z0-9]{6,16}$/;
  //密码验证  6-16位 字母数字下划线
  var rePass = /^[a-zA-Z_0-9]{6,16}$/;
  //手机号验证
  var phoneNum =/^1(3|4|5|7|8)\d{9}$/;
  //错误提示
  var waring2 = '<div class="waring2"></div>';
  // ajax返回错误提示
  var errorMsg = '<div class="errorMsg"></div>';
var userOk = false;//用户可用判断
var phoneOk = false;//手机号是否可以注册
var isWhere = 0;//用来判断 当前焦点框 （密码和重复密码） 全局
/*1
 * e 当前元素
 * 键盘弹起事件，焦点事件  判断 input 长度大于0就出现 重置按钮  并移除警告，和警告框
 */
function resetBlock(e){
	if(e.val().length>0){
		e.next().removeClass('amBlock').next().addClass('amBlock').removeClass('waring');
	}
}
/*3发送验证码，接收验证码
 * 
 */
sendYzm(60);
function sendYzm(tm){
	var canClick = true; //是否点击
	var allOk = false; //所有信息是否匹配
	$('.send_yzm').click(function(){
		//判断发送条件
		if($('#phone_num').val()){
			if(phoneNum.test($('#phone_num').val())){ //匹配手机
					
				var phone_num = $('#phone_num').val();
				$.ajax({
					type:"get",
					url:"/public/sms/send",
					data:{
						phone:phone_num,
						type:1
					},
					async:false,
					success:function(data){
						if(data.success){  //手机号可以注册
							phoneOk  = true;
							allok = true;
						}else{  //手机号已被注册
							phoneOk  = false;
							allok = false;
							showBackMsg(data.message);
							$('#phone_num').next().next().removeClass('amBlock').parent().append(waring2);
						}
					}
				});	
			}else{
				if($('#phone_num').attr('wr')!=1){
					$('#phone_num').attr('wr',1).focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('手机号码不正确').parent().append(waring2);
				}
				allok = false;
			}
		}else{
			if($('#phone_num').attr('wr')!=1){
				$('#phone_num').attr('wr',1).focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('请输入手机号').parent().append(waring2);
			}
			allok = false;
		}
		//发送
		if(canClick==true && allok == true){
			// 根据返回如果手机已被注册，则可以继续发送，否则，
			if(phoneOk){ //手机号可以注册

				canClick = false;
				var djs = '<span class="djs">'+tm+'</span>';
				$(this).children('p').html('秒').css({'color':'#999'}).prepend(djs);
				var time_= Number(tm);
				var t = setInterval(function(){
					time_--;
					$('.djs').html(time_);
					if(time_<1){
						clearInterval(t);
						canClick = true;
						$('.send_yzm').children('p').html('重新发送').css({'color':'#58b1f1'});
					}
				},1000)
			}else{
				canClick = true;
			}
			
		}	
	})
}
/*4防止表单重复提交1.2 测试阶段
 * 
 */
var hadCheck = false;
function checkSubmit(){				
	if(hadCheck == true){ //当表单被提交过一次后hadCheck将变为true,再次提交则不生效。 
		return false;				
	}
	hadCheck = true;
	return true;
}
//阻止 重复提交
$(document).ready(function() {
  $('form').submit(function() {
    if(typeof jQuery.data(this, "disabledOnSubmit") == 'undefined') {
      jQuery.data(this, "disabledOnSubmit", { submited: true });
      $('input[type=submit], input[type=button]', this).each(function() {
        $(this).attr("disabled", "disabled");
      });
      return true;
    }
    else
    {
      return false;
    }
  });
});
/*5 下一步成功后做的事情
 * 
 */
function allok(){
	$('.register_body').addClass('amNone');
	$('.register_next').addClass('amBlock');
	var send_phoneNum = '<span class="sendPhoneNum">'+"我们已向手机号码"+$('#phone_num').val()+"发送了一条验证短信"+'</span>';
	$('body').append(send_phoneNum);
	setTimeout(function(){
		$('.sendPhoneNum').toggle();
	},1000)
}

/*6,有效的手机号
 * 
 */
function hadPhone(){
	if($('#phone_num').val()){
		if(!phoneNum.test($('#phone_num').val())){
			if($('#phone_num').attr('wr')!=1){
				$('#phone_num').attr('wr',1);
				$('#phone_num').next().addClass('amBlock').css({'right':'0.18rem'}).html('请输入有效的手机号').parent().append(waring2);
			}				
		}
	}
}
/*7 .返回错误信息弹出
*
* 	
 */
function showBackMsg(data){
	$('body').append(errorMsg);
	$('.errorMsg').html(data);
	if($('.errorMsg').length == 1){
		var t1 = setTimeout(function(){
			$('.errorMsg').remove();
		},2500)
	}
}



/*主要，键盘事件，焦点事件
 * 
 * 
 */
//点击切换验证码
	$('.changTwm').click(function(){
		$('#twm').focus().val("").next().removeClass('amBlock').next().removeClass('waring').removeClass('amBlock');
		createCode();
	})

//密码可见,改变input 的type 
	$('.eye').click(function(){
		if($(this).attr('rel')!=1){
			$(this).attr('rel',1).addClass('eye_open').parent().children('input').attr('type','text');
			$('#pwd_repeat').attr('type','text');
		}else{
			$(this).attr('rel',0).removeClass('eye_open').parent().children('input').attr('type','password');
			$('#pwd_repeat').attr('type','password');
		}
		//用来判断 focus 在哪里
		if(isWhere == 1){ //密码
			$('#phone_pwd').attr('rel',1).focus();
		}else if(isWhere ==2){ //重复密码
			$('#pwd_repeat').attr('rel',1).focus();
		}
		$('#pwd_repeat').next().removeClass('amBlock');
		$(this).siblings('.waring2').remove();		
	})

//重置
	$('.login_reset').click(function(e){
		e.stopPropagation();
		$(this).parent().children('input').val('').focus().next().removeClass('amBlock');
		$(this).removeClass('amBlock').removeClass('waring');
	})
//重置2
	$('.login_reset2').click(function(){
		$(this).parent().children('input').val('').focus().attr('wr',0).next().removeClass('amBlock');
		$(this).siblings('.waring2').remove();
		$(this).removeClass('amBlock');
	})
	

/*元素
 * 
 * 
 */
//账号：ajax
	$('#user_num').keyup(function(){
		if($(this).val().length>0){
			$(this).attr('wr',0).next().removeClass('amBlock').next().addClass('amBlock').css({'right':'0.4rem'});
			$(this).siblings('.waring2').remove();
		}else{
			$(this).next().removeClass('amBlock');
			$(this).siblings('.waring2').remove();
		}
	}).focus(function(){
		$(this).siblings('.waring2').remove();
		if($(this).val()){//判断存在输入值
			$(this).next().next().addClass('amBlock');
			if($(this).attr('wr') == 1){ //表示有错误
				$(this).next().addClass('amBlock').next().addClass('amBlock').css({'right':'1.03rem'}).parent().append(waring2);
			}
		}
		hadPhone();	
	}).blur(function(){//ajax1
		if($(this).val()){
			if(reName.test($(this).val())){ //用户格式正确			
				var user = $(this).val();
				$.ajax({
					type:"get",
					url:"/wap/auth/query_username_is_exist",
					data:{
						username:user
					},
					async:false,
					success:function(data){
						if(data.success){
							userOk = true;
						}else{
							userOk = false;
							showBackMsg(data.message);
							$('#user_num').next().next().removeClass('amBlock').parent().append(waring2);
						}
					}
				});			
			 //不存在则，可以使用		
			}else{
				if($('#user_num').attr('wr')!=1){//wr 设置标志，表示有误
					$('#user_num').attr('wr',1).next().addClass('amBlock').css({'right':'0.18rem'}).html('账号由6-12位字母/数字组成').parent().append(waring2);
				}
				userOk = false;
			}
		}
	})
	
//手机号  ajax
	$('#phone_num').keyup(function(){//键盘弹起事件
		$('#pwd_repeat').next().removeClass('amBlock');
		if($(this).val().length>0){
			$(this).attr('wr',0).next().removeClass('amBlock').next().addClass('amBlock').css({'right':'0.4rem'});
			$(this).siblings('.waring2').remove();
		}else{
			$(this).next().removeClass('amBlock');
			$(this).siblings('.waring2').remove();
		}
	}).focus(function(){ //获取焦点
//		$(this).parent().siblings().children('.login_hide').removeClass('amBlock').next().removeClass('amBlock');
		if($(this).val().length>0){//判断存在输入值
			if($(this).attr('wr') == 1){ //表示有错误
				$(this).next().next().addClass('amBlock').css({'right':'1.03rem'});
			}
		}
		if($('#phone_pwd').val()){ //密码格式
				if(!rePass.test($('#phone_pwd').val())){ //正则匹配密码
					$('#phone_pwd').next().addClass('amBlock').html('密码格式不对').next().addClass('waring');
				}
			}
	}).blur(function(){
	})
	
//验证码
	$('#phone_yzm').keyup(function(){
		resetBlock($('#phone_yzm'));
	}).focus(function(){
		hadPhone();
		resetBlock($('#phone_yzm'));
	}).blur(function(){
		$(this).next().next().removeClass('amBlock');
	})
	
//密码
	$('#phone_pwd').keyup(function(){
//		$('#pwd_repeat').next().removeClass('amBlock');	
		resetBlock($('#phone_pwd'));
	}).focus(function(){	
		$(this).parent().siblings().children('.login_reset').removeClass('amBlock');
		resetBlock($('#phone_pwd'));
		if($(this).attr('rel')!=1){ //自定义属性，用来判断是否点击眼睛（查看密码）。
			if($(this).val().length<6 || $(this).val().length>16){//密码不在范围内则清除
				$(this).val('').next().removeClass('amBlock').next().removeClass('amBlock').removeClass('waring');
			}
		}
		hadPhone();
	}).blur(function(){ //失去焦点，
		$(this).attr('rel',0);
		isWhere = 1;
	})
	
//重复密码
	$('#pwd_repeat').keyup(function(){
		$(this).attr('nr',0).next().removeClass('amBlock').next().removeClass('waring');
		resetBlock($('#pwd_repeat'));
	}).focus(function(){
		$(this).parent().siblings().children('.login_reset').removeClass('amBlock');
		resetBlock($('#pwd_repeat'));
		
		if($(this).attr('rel')!=1){ //如果点击眼睛，则不会清除。
			if($(this).attr('nr')!=1){
				if($(this).val() != $('#phone_pwd').val()){//密码不一致则清除
					$(this).val('').next().removeClass('amBlock').next().removeClass('amBlock').removeClass('waring');
					$(this).siblings('.waring2').remove();
				}
			}		
		}
		hadPhone();//手机格式
		if($('#phone_pwd').val()){ //密码格式
				if(!rePass.test($('#phone_pwd').val())){ //正则匹配密码
					$('#phone_pwd').next().addClass('amBlock').html('密码格式不对').next().addClass('waring');
				}
			}
	}).blur(function(){
		$(this).attr('rel',0);
		isWhere = 2;	
		if($(this).attr('rel')!=1){
			if($(this).val()){ //失去焦点后，存在值才判断 是否一致
				if($(this).val() != $('#phone_pwd').val()){
					$(this).next().addClass('amBlock').html('两次密码输入不一致').css({'right':'0.18rem'}).parent().append(waring2);
				}
			}
		}
		
	})

/* 注册 表单提交
 * 
 */
$('.btn_next').click(function(){
	if($('#user_num').val()){//用户是否有输入
		if(reName.test($('#user_num').val())){
			if(userOk){ //用户是否可以注册 :userOk 
				if($('#phone_num').val()){//手机是否有输入
					if(phoneNum.test($('#phone_num').val())){ //匹配手机
						if(phoneOk){//手机号可以注册：phoneOk;
							if(rePass.test($('#phone_pwd').val())){ //匹配密码
								if($('#phone_pwd').val() == $('#pwd_repeat').val()){ //重复密码
										var username = $('#user_num').val();
										var phone = $('#phone_num').val();
										var pwd = $('#phone_pwd').val();
										$.ajax({
											type:"get",
											url:"/wap/auth/register",
											data:{
												username:username,
												phone:phone,
												password:pwd
											},
											async:false,
											success:function(data){
												if(data.success){ //注册成功
													showBackMsg(data.message);
													 var storage=window.localStorage;
													 storage.setItem("user",data.collection[0]);
													 window.location.href="/wap/index.html"
												}else{
													showBackMsg(data.message);
												}
											}
										});
									}else{//两次密码不同
										$('#pwd_repeat').attr('nr',1);
										$('#pwd_repeat').focus().next().addClass('amBlock').html('两次密码不同').next().addClass('waring');
										return false;
									}
								}else{//密码格式有误
									$('#phone_pwd').focus().next().addClass('amBlock').html('密码格式不对').next().addClass('waring');
									return false;
								}
						}
					}else{//手机号格式有误
						if($('#phone_num').attr('wr')!=1){
							$('#phone_num').attr('wr',1).focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('手机号码不正确').parent().append(waring2);
						}
						return false;
					}		
				}else{ //手机号未输入
					if($('#phone_num').attr('wr')!=1){
						$('#phone_num').attr('wr',1).focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('请输入手机号').parent().append(waring2);
					}
					return false;
				}
			}else{ //用户已存在
				$('#user_num').focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('用户名已存在').parent().append(waring2);;
				return false;
			}
		}else{//用户名格式
			$('#user_num').next().css({'right':'0.18rem'}).addClass('amBlock').html('请输入正确的用户名').parent().append(waring2);
			return false;
		}
	}else{//未输入用户名
		$('#user_num').focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('请输入用户名').html('请输入手机号').parent().append(waring2);
		return false;
	}
})


