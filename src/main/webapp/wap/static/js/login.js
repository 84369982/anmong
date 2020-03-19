//验证方式
	$('.login_style li').click(function(){
		var index_ = $('.login_style li').index($(this));
		$(this).addClass('on').siblings().removeClass('on');
		$('.login_check').each(function(){
			$(this).removeClass('amBlock').addClass('amNone');
		})
		$('.login_check').eq(index_).addClass('amBlock');
	
	})
  //用户名验证  5-9位 字母数字及下划线
  var reName = /^[\w]{5,9}$/;
  //密码验证  6-16位 字母数字及下划线
  var rePass = /^[a-zA-Z_0-9]{6,16}$/;
  //手机号验证
  var phoneNum =/^1(3|4|5|7|8)\d{9}$/;
  //验证码长度验证
  var reYzm =/^[0-9]{4}$/;

  var waring2 = '<div class="waring2"></div>'; 
  // ajax返回错误提示
  var errorMsg = '<div class="errorMsg"></div>';
  var errorTime = 0; //记录登录错误。
  var code="" ; //在全局 定义验证码
//		createCode(); //首次生成
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
	/*2 发送验证码，接收验证码
	 *  ajax
	 */
	sendYzm(60);
	function sendYzm(tm){
		var canClick = true; //是否点击
		var allOk = false; //所有信息是否匹配
		$('.send_yzm').click(function(){
			//判断发送条件
			if($('#phone_num').val()){
				if(phoneNum.test($('#phone_num').val())){ //匹配手机,验证手机号是否注册
					var phone_ = $('#phone_num').val();
					$.ajax({
						type:"get",
						url:"/public/sms/send",
						data:{
							type:2,
							phone:phone_
						},
						async:false,
						success:function(data){
							if(data.success){ //手机号可以发送
								allok = true;
							}else{//手机号未注册
								allok = false;
								showBackMsg(data.message);
							}
						}
					});								
				}else{
					if($('#phone_num').attr('wr')!=1){ //判断是否存在，存在则不再添加
						$('#phone_num').attr('wr',1).next().addClass('amBlock').html('请输入有效的手机号码').parent().append(waring2);
					}				
					allok = false;
				}
			}else{
				if($('#phone_num').attr('wr')!=1){
						$('#phone_num').attr('wr',1).focus().next().addClass('amBlock').html('手机号不能为空').parent().append(waring2);
				}			
				allok = false;
			}
			//发送
			if(canClick==true && allok == true){//发送之后,tm时间内不能再发送
				canClick = false;
				dosomething(); // ajax 验证
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
			}	
		})
	}
	/*3 防止表单重复提交
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
	//未测试
	$(document).ready(function() {
	  $('form').submit(function() {
	    if(typeof jQuery.data(this, "disabledOnSubmit") == 'undefined') {
	      jQuery.data(this, "disabledOnSubmit", { submited: true });
	      $('input[type=submit], input[type=button]', this).each(function() {
	        $(this).attr("disabled", "disabled");
	      });
	      return true;
	    }else {
	      return false;
	    }
	  });
	});
	/*4  ajax 进行验证 
	 * 
	 */
	function dosomething(){
		var phone_ = $('#phone_num').val();
		var code = $('#phone_yzm').val();
			$.get(
				"/wap/auth/login_by_phone",
				{
				 authcode:code,
				 phone:phone_
			    },
				function(data){
					if(data.success){
						 var storage=window.localStorage;
						 storage.setItem("user",data.collection[0]);
						 window.location.href="/wap/index.html"
					}
					else{
						showBackMsg(data.message);
						
						}
				}
			)

		
	}
	

	/*5 .返回错误信息弹出
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
	/*1.
	 * e 当前元素
	 * 键盘弹起事件，焦点事件 使用 判断 input 长度大于0就出现 重置按钮  并移除警告，和警告框
	 */
	function resetBlock(e){
		if(e.val().length>0){
			e.next().removeClass('amBlock').next().addClass('amBlock').removeClass('waring');
		}
	}
	//点击切换验证码
		$('.changTwm').click(function(){
			$('#twm').focus().val("").next().removeClass('amBlock').next().removeClass('waring').removeClass('amBlock');
			createCode();
		})
/*使用 
 *  wr=1 ,rel=1,nr=1 用来标识某个元素 已经存在,用来作判断条件。
 */
//账号登录	
//账号 手机号
	$('#name').keyup(function(){//键盘弹起事件
		resetBlock($('#name'));
	})
	$('#name').focus(function(){ //获取焦点
		$('#password').next().next().removeClass('amBlock');
		resetBlock($('#name'));
	})
//密码
	$('#password').keyup(function(){
		resetBlock($('#password'));
	})
	$('#password').focus(function(){
		$('#name').next().next().removeClass('amBlock');
		resetBlock($('#password'));
	})

//密码可见
	$('.eye').click(function(){
		if($(this).attr('rel')!=1){
			$(this).attr('rel',1).addClass('eye_open').parent().children('input').attr('type','text');
		}else{
			$(this).attr('rel',0).removeClass('eye_open').parent().children('input').attr('type','password');
		}
		$('#password').focus();
	})

//重置
	$('.login_reset').click(function(){
		$(this).parent().children('input').val('').focus().next().removeClass('amBlock');
		$(this).removeClass('amBlock');
	})
//重置2
	$('.login_reset2').click(function(){
		$(this).parent().children('input').val('').focus().attr('wr',0).next().removeClass('amBlock');
		$('.waring2').remove();
		$(this).removeClass('amBlock');
	})
//短信登录
//手机号 、wr
	$('#phone_num').keyup(function(){//键盘弹起事件
		if($(this).val().length>0){
			$(this).attr('wr',0).next().removeClass('amBlock').next().addClass('amBlock').css({'right':'0.4rem'});
			$('.waring2').remove();
		}
	})
	$('#phone_num').focus(function(){ //获取焦点
		$('#phone_yzm').next().next().removeClass('amBlock');		
		if($(this).val().length>0){//判断存在输入值
			if($(this).attr('wr') == 1){ //表示有错误
				$(this).next().next().addClass('amBlock').css({'right':'1.03rem'});
			}
		}
	}).blur(function(){	
		//dont do 
	})
//验证码
	$('#phone_yzm').keyup(function(){
		resetBlock($('#phone_yzm'));
	})
	$('#phone_yzm').focus(function(){
		if($('#phone_num').val()){
			if(!phoneNum.test($('#phone_num').val())){
				if($('#phone_num').attr('wr')!=1){
					$('#phone_num').attr('wr',1);
					$('#phone_num').next().addClass('amBlock').html('请输入有效的手机号').parent().append(waring2);
				}				
			}
		}
		$('#phone_num').next().next().removeClass('amBlock');
		resetBlock($('#phone_yzm'));
	})
	
//提交1

$('.btn').click(function(){
	if(errorTime >=2){
				$('.btn').addClass('btn2').removeClass('btn');
				createCode();
				$('.login_twm').show();
			}
	if($('#name').val()){
		if($('#password').val()){
			loginYz();
			return false;
		}else{
			$('#password').focus().next().addClass('amBlock').html('请输入密码').next().addClass('waring');
			return false;
		}
	}else{
		$('#name').focus().next().addClass('amBlock').html('请输入账号').next().addClass('waring');
		return false;
	}
})

//提交2 多一个图文验证
$('.btn2').click(function(){
	if($('#name').val()){
		if($('#password').val()){
			loginYz(); //提交
			return false;
		}else{
			$('#password').focus().next().addClass('amBlock').html('请输入密码').next().addClass('waring');
			return false;
		}
	}else{
		$('#name').focus().next().addClass('amBlock').html('请输入账号').next().addClass('waring');
		return false;
	}
})


function loginYz(){
	var phone_ = $('#name').val();
	var pwd = $('#password').val();
	
	$.ajax({
		type:"get",
		url:"/wap/auth/login_by_account",
		async:true,
		data:{
			account:phone_,
			password:pwd
		},
		success:function(data){
			if(data.success){
				showBackMsg(data.message);
				 var storage=window.localStorage;
				 storage.setItem("user",JSON.stringify(data.collection[0]));
				 success = true;
				 window.location.href="/wap/my/myHomepage.html";
			}
			else{
				errorTime++;
				showBackMsg(data.message);
				 }
			}
	});
}