	
		 //用户名验证  5-9位 字母数字及下划线
		  var reName = /^[\w]{5,9}$/;
		  //密码验证  6-16位 字母数字下划线
		  var rePass = /^[a-zA-Z_0-9]{6,16}$/;
		  //手机号验证
 		 var phoneNum =/^1(3|4|5|7|8)\d{9}$/;	
 		 //隐藏 手机中间4位
 		 var regPwd = /^(\d{3})\d{4}(\d{4})$/;
		// example:  tel = tel.replace(regPwd,"$1****$2");
 		 var reYzm =/^[0-9]{4}$/;
 		 var waring2 = '<div class="waring2"></div>';
 		 // ajax返回错误提示
  		var errorMsg = '<div class="errorMsg"></div>';
 		 //用来判断 当前焦点框 （密码和重复密码） 全局
 		var isWhere = 0;
 		var code="" ; //在全局 定义验证码
		createCode(); //首次生成
		/*1创建图文码
		 * 
		 */
		function createCode(){ 
			code = "";
			var codeLength = 4;//验证码的长度
			var checkCode = $('.changTwm');
			checkCode.value = "";
			var selectChar = new Array('A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');	
			for(var i=0;i<codeLength;i++) {
			   var charIndex = Math.floor(Math.random()*27);
			   code +=selectChar[charIndex];
			}
			if(code.length != codeLength){
			   createCode();	
			}
			$('.changTwm').html(code);
		}
		
		var canClick = true;//发送验证码，接收验证码 定义条件
		$('.send_yzm').click(function(){//重新发送
			sendYzm(30);
		})
		/*2发送和接收验证码
		 *   ajax 判断 是否发送 接收验证码
		 */
		function sendYzm(tm){
			if(canClick){
				canClick = false;
				var djs = '<span class="djs">'+tm+'</span>';
				$(".send_yzm").children('p').html('秒').css({'color':'#999'}).prepend(djs);
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
			// 发送验证，返回验证
				var phone_num = $('#foundPwd_num').val();
				$.ajax({
					type:"get",
					url:"/public/sms/send",
					data:{
						type:3,
						phone:phone_num
					},
					async:false,
					success:function(data){
						if(data.success){  
							//
							showBackMsg('请注意查收验证码');
						}else{ 
							showBackMsg(data.message);
						}
					}
				});	
		}
		}	
		/*3 设置 事件，
		 * e 当前元素
		 * 键盘弹起事件，焦点事件  判断 input 长度大于0就出现 重置按钮  并移除警告，和警告框
		 */
		function resetBlock(e){
			if(e.val().length>0){
				e.next().removeClass('amBlock').next().addClass('amBlock').removeClass('waring');
			}
		}
		
		/*4防止表单重复提交
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
		
		/*5 ajax判断用户是否存在
		 *   存在返回1，不存在返回0
		 */
		function hadUser(){
			var user = $('#foundPwd_num').val();
			var type = 3;
			var msgs=false;//ajax执行完如果要改变全局变量值，async:改为同步。
			//判断用户是否存在，手机号由后台验证是否注册。
			$.ajax({
				url:"/public/sms/send",
				type:"get",
				data:{
					type:3,
					phone:user
				},
				async:false,
				success:function(data){					
					if(data.success){//下一步
						// showBackMsg(data.success);
						msgs = true;
					}else{
						msgs = false;
						showBackMsg(data.message);
					}
				}
			});
			return msgs;
		}
	/*6 .返回信息弹出
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



/* 主要使用3 设置 事件resetBlock();
 * 包含键盘弹起事件， 焦点事件 ，初步对信息进行 判断
 */
	//图文码
		$('#twm').keyup(function(){//键盘弹起事件
			resetBlock($('#twm'));
			$(this).next().removeClass('amBlock').next().removeClass('waring');
		})
		$('#twm').focus(function(){ // 图文码 
			if($('#foundPwd_num').val()){
				if(!phoneNum.test($('#foundPwd_num').val())){
					if($('#foundPwd_num').attr('wr')!=1){
						$('#foundPwd_num').attr('wr',1);
						$('#foundPwd_num').next().addClass('amBlock').css({'right':'0.18rem'}).html('请输入有效的手机号').parent().append(waring2);
					}				
				}
			}
			$(this).parent().siblings().children('.login_reset').removeClass('amBlock');
			resetBlock($('#twm'));
		}).blur(function(){
			$(this).next().removeClass('amBlock').next().removeClass('waring').removeClass('amBlock');
		})
	//账号 手机号
		$('#foundPwd_num').keyup(function(){//键盘弹起事件
			$(this).next().removeClass('amBlock').next().removeClass('waring');
			if($(this).val().length>0){
				$(this).attr('wr',0).next().removeClass('amBlock').next().addClass('amBlock').css({'right':'0.4rem'});
				$('.waring2').remove();
			}
		})
		$('#foundPwd_num').focus(function(){ //获取焦点
			$(this).parent().siblings().children('.login_reset').removeClass('amBlock');
//			resetBlock($('#foundPwd_num'));
			if($(this).val().length>0){//判断存在输入值
				if($(this).attr('wr') == 1){ //表示有错误
					$(this).next().next().addClass('amBlock').css({'right':'1.03rem'});
				}
			}
		}).blur(function(){
		})
	//验证码
		$('#foundPwd_yzm').keyup(function(){//键盘弹起事件
			$(this).next().removeClass('amBlock').next().removeClass('waring');
			resetBlock($('#foundPwd_yzm'));
		})
		$('#foundPwd_yzm').focus(function(){ //获取焦点
			
			$(this).parent().parent().siblings().children('.login_hide').removeClass('amBlock').next().removeClass('amBlock');
			resetBlock($('#foundPwd_yzm'));
			if($('#new_pwd').val()){
				if(!rePass.test($('#new_pwd').val())){ //正则匹配密码
					$('#new_pwd').next().addClass('amBlock').html('密码格式不对').next().addClass('waring');
				}
			}
		}).blur(function(){
			if(!$(this).val()){
				$(this).next().addClass('amBlock').html('请输入验证码').next().addClass('waring');
			}
		})
	//新密码
		$('#new_pwd').keyup(function(){//键盘弹起事件			
			$('#new_repeatpwd').next().removeClass('amBlock');	
			resetBlock($('#new_pwd'));
		})
		$('#new_pwd').focus(function(){ //获取焦点
		//	$(this).parent().siblings().children('.login_reset').removeClass('amBlock');
			resetBlock($('#new_pwd'));
			if($(this).attr('rel')!=1){ //如过点击眼睛，则不会清除。
				if($(this).val().length<6 || $(this).val().length>16){		
					$(this).val('').next().removeClass('amBlock').next().removeClass('amBlock').removeClass('waring');
				}	
			}
		}).blur(function(){
			$(this).attr('rel',0);
			isWhere = 1;
		})
	//重复密码
		$('#new_repeatpwd').keyup(function(){//键盘弹起事件
			$(this).attr('nr',0).next().removeClass('amBlock').next().removeClass('waring');
			resetBlock($('#new_repeatpwd'));
		})
		$('#new_repeatpwd').focus(function(){ //获取焦点
			$(this).parent().siblings().children('.login_reset').removeClass('amBlock');
			resetBlock($('#new_repeatpwd'));
			if($(this).attr('rel')!=1){ //如过点击眼睛，则不会清除。
				if($(this).attr('nr') ==1 ){ //提交时提示失败，则点击重复密码会清空
					if($(this).val() != $('#new_pwd').val()){ //密码不同，清空
						$(this).val('').next().removeClass('amBlock').next().removeClass('amBlock').removeClass('waring');
					}
				}
			}	
			if($('#new_pwd').val()){
				if(!rePass.test($('#new_pwd').val())){ //正则匹配密码
					$('#new_pwd').next().addClass('amBlock').html('密码格式不对').next().addClass('waring');
				}
			}
		}).blur(function(){
			$(this).attr('rel',0);
			isWhere =2;
		})
		
		
	//重置
		$('.login_reset').click(function(){
//			e.stopPropagation();
			$(this).parent().children('input').val('').focus().next().removeClass('amBlock');
			$(this).removeClass('amBlock').removeClass('waring');
		})
	//重置2
		$('.login_reset2').click(function(){
			$(this).parent().children('input').val('').focus().attr('wr',0).next().removeClass('amBlock');
			$('.waring2').remove();
			$(this).removeClass('amBlock');
		})
	//点击切换验证码
		$('.changTwm').click(function(){
			$('#twm').focus().val("").next().removeClass('amBlock').next().removeClass('waring').removeClass('amBlock');
			createCode();
		})
		
	//密码可见
		$('.eye').click(function(){
			if($(this).attr('rel')!=1){
				$(this).attr('rel',1).addClass('eye_open').parent().children('input').attr('type','text');
				$('#new_repeatpwd').attr('type','text');
			}else{
				$(this).attr('rel',0).removeClass('eye_open').parent().children('input').attr('type','password');
				$('#new_repeatpwd').attr('type','password');
			}
			//用来判断 focus 在哪里
			if(isWhere == 1){
				$('#new_pwd').attr('rel',1);
				$('#new_pwd').focus();
			}else if(isWhere ==2){
				$('#new_repeatpwd').attr('rel',1);
				$('#new_repeatpwd').focus();
			}else{
				$('#new_pwd').attr('rel',1);
				$('#new_repeatpwd').attr('rel',1);
			}
			
		})
		


/*使用  1创建图文码createCode()，2发送验证码sendYzm()，4防止表单重复提交checkSubmit(),5 ajax判断用户是否存在hadUser()，
 * 下一步
 */
	$('.found_next').click(function(){
			var inputCode = $("#twm").val().toUpperCase();
			
			if($('#foundPwd_num').val()){ //手机号
				if(phoneNum.test($('#foundPwd_num').val())){
					if(inputCode){ //是否存在图文码
						if(inputCode == code){ //图文码是否正确						
							if(hadUser()){//判断是否存在用户 ajax 
								var phone_num = $('#foundPwd_num').val();
								var telHide = phone_num.replace(regPwd,"$1****$2");
								$('.send_phone_num').html("已发送短信验证到"+telHide);
								$('.found_pwd').addClass('amNone').next().addClass('amBlock');
								sendYzm(20);//发送验证码：
							}else{
								//初始化设置：
								createCode();
								$('#twm').val('');
								$('#foundPwd_num').focus();
								return false;
							}
						}else{
							createCode(); //不正确需要换验证码
							$('#twm').focus().val('').focus().next().addClass('amBlock').html('验证码不正确').next().addClass('waring');
							return false;
						}
					}else{
						$('#twm').focus().next().addClass('amBlock').html('请输入验证码').next().addClass('waring');
						return false;
					}
				}else{
					if($('#foundPwd_num').attr('wr')!=1){
						$('#foundPwd_num').attr('wr',1).focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('请填写有效的手机号码').parent().append(waring2);
					}
					return false;
				}
			}else{
				if($('#foundPwd_num').attr('wr')!=1){
					$('#foundPwd_num').attr('wr',1).focus().next().css({'right':'0.18rem'}).addClass('amBlock').html('请输入手机号').parent().append(waring2);
				}
				return false;
			}
		})

/*主要使用 2 sendYzm()接收验证码返回的参数 判断 验证码。
 * 确认
 */

	$('.foundPwd_qd').click(function(){
	 	if($('#foundPwd_yzm').val()){
			if(rePass.test($('#new_pwd').val())){ //匹配密码
				if($('#new_pwd').val() == $('#new_repeatpwd').val()){ //repeat pwd					
					if(reYzm.test($('#foundPwd_yzm').val())){
						var yzm = $('#foundPwd_yzm').val();
						var user = $('#foundPwd_num').val();
						var password = $('#new_pwd').val();
						$.ajax({
							url:"/wap/auth/found_password",
							type:"get",
							data:{
								password:password,
								phone:user,
								authcode:yzm
							},
							async:false,
							success:function(data){								
								if(data.success){
									showBackMsg(data.mesage);
									window.location.href="/wap/index.html"
								}else{
									showBackMsg(data.message);
								}
							}
						});
					}else{
						$('#foundPwd_yzm').focus().next().addClass('amBlock').html('验证码格式错误').next().addClass('waring');
						return false;
					}
				}else{
					$('#new_repeatpwd').attr('nr',1); //设置 标识，只有提交提示密码不同，点击重复密码才会清空.
					$('#new_repeatpwd').next().addClass('amBlock').html('两次密码不同').next().addClass('waring');
					return false;
				}
			
			}else{
				$('#new_pwd').next().addClass('amBlock').html('密码格式不对').next().addClass('waring');
				return false;
			}
		}else{
			$('#foundPwd_yzm').focus().next().addClass('amBlock').html('请输入验证码').next().addClass('waring');
			return false;
		}
	})
