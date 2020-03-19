$(function(){
	//返回上一页
	$('.icon_close').click(function(){
		history.go(-1);
	})
	function goBck(n){
		history.go(n);
	}
	//滚动条改变事件
	$(window).scroll(function(){
		//article评论 more
		showMore();
		comebaFix();
		
	})
	$('body').click(function(){
		//article 评论
		$('.more').css({'display':'none'});
	})
	//返回上一页
	$('.icon_back').click(function(){
		history.go(-1);
	})
	
//----------------------------article--------------------------------	
	//index_article1.html 收藏
	$('.icon_like').click(function(){
		if($(this).attr('rel') != 1){
			$(this).css({"background":"url(../static/images/icon/footer_star_on.png) center center no-repeat","background-size":'100%'});
			$(this).attr('rel',1);
		}else{
			$(this).css({"background":"url(../static/images/icon/footer_star.png) center center no-repeat","background-size":'100%'});
			$(this).attr('rel',0);
		}
	})
	//article 点赞				
	$('.article1_discuss_agree').click(function(e){
		e.stopPropagation();
		var index_ =  $('.article1_discuss_agree').index($(this));
		if($(this).attr('col')!=1){
			$(this).css({"background":"url(../static/images/icon/icon_red_zan.png) left center no-repeat","background-size":'contain','animation':"agree 0.7s forwards ease-in"});
			$(this).attr('col',1);
			$(this).siblings().css({'color':'#FF7475'}).html(Number($('.article1_discuss_num').eq(index_).html())+1);
		}else{
				var had_zan = '<span class="zan_mid">'+"你已赞过"+'</span>';
				$('body').append(had_zan);
				//只有第一次触发 清除指令。
				if($('.zan_mid').length == 1){
					var t1 = setTimeout(function(){
						$('.zan_mid').remove();
					},5000)
				}							
	}
	})
	
	//article1 给每个评论都添加 更多
	var more_ = '<div class="more">'+
				'<p class="jubao">'+"举报"+'</p>'+
				'<p class="fengxiang">'+"分享"+'</p>'+
				'<p class="copy">'+"复制"+'</p>'+'</div>'
	$('.article1_discuss li').each(function(){
		$(this).append(more_);
	})
	
	//article 让更多定位的位置，
	showMore();
	function showMore(){
		$('.article1_discuss .article1_discuss_more').click(function(e){
			//阻止冒泡
			e.stopPropagation();
			var index_ = $('.article1_discuss_more').index($(this));									
			//元素距离浏览器顶部的可视距离
			var moreTop = $('.article1_discuss_more').eq(index_).offset().top-$(document).scrollTop();					
			//浏览器高度-元素距浏览器顶部高度 = 元素距离浏览器第部的可视高度。
			var thisMoreDown = $(window).height()-moreTop;
			var thisLiHeight = $(this).parents('li').height()*1.7;
			
			$(this).parents('li').siblings().find('.more').css({'display':'none'});
			$(this).parent().siblings('.more').css({'display':'block'});
			//获取当前元素到底部的距离，小于 就触发
			if(thisMoreDown < thisLiHeight){
				$(this).parent().siblings('.more').css({'display':'block','top':'-2.2rem'});
			}else{
				$(this).parent().siblings('.more').css({'display':'block','top':'0.94rem'});
			}
		})
	}
//-----------------------------comeba--------------------------	
	//comeba 头部定位
	function comebaFix(){
		if($(document).scrollTop()>= $('.comeba_top').height()){
			$('body').css('padding-top','0.88rem');
			$('.comeba_nav').addClass('comeba_nav_fix');
		}else{
			$('body').css('padding-top','0');
			$('.comeba_nav').removeClass('comeba_nav_fix');
		}
	}
	//comeba 导航栏效果
	$('.comeba_nav li').click(function(){
				$(this).addClass('on').siblings().removeClass();
	})
			
	//comeba 我的bar
	$('.comeba_bj').click(function(){
		var close_ = '<i class="comeba_close"></i>';
		if($(this).attr('rel')!=1){
			$(this).attr('rel',1).html("完成编辑").siblings('.comeba_add').addClass('amBlock').parent().siblings().children().append(close_);
			$('.comeba_close').click(function(){
				$(this).parent().remove();
			})	
		}else{
			$(this).attr('rel',0).html('编辑').siblings('.comeba_add').removeClass('amBlock').parent().siblings().children().children('.comeba_close').remove();
		}
		
	})
	
	
})

/*------------------我的 ----------------------------*/
	// ajax返回错误提示 .两个只是用样式不同。 errorMsg 位于 top 30%,picMsg 位于bottom:20%
  	var errorMsg = '<div class="errorMsg"></div>';
	var picMsg = '<div class="picMsg"></div>';
	/* .返回错误信息弹出
	*
	* 	
	 */
	function showBackMsg(data){
		$('body').append(picMsg);
		$('.picMsg').html(data);
		if($('.picMsg').length == 1){
			var t1 = setTimeout(function(){
				$('.picMsg').remove();
			},2500)
		}
	}
	function showBackMsg2(data){
		$('body').append(errorMsg);
		$('.errorMsg').html(data);
		if($('.errorMsg').length == 1){
			var t1 = setTimeout(function(){
				$('.errorMsg').remove();
			},2500)
		}
	}
	/*
	获取页面参数
	 */
	function getQueryString(name) { //输入参数名称
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); //根据参数格式，正则表达式解析参数
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null; //返回参数值
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
	/*
	判断 localstorage;
	 */
	function hadLogin(){
		var hadUser=window.localStorage.getItem('user'); 
		   if (hadUser == undefined || hadUser == "") { 
		      return false;
			} else { 
		       return true;
		    } 
	}