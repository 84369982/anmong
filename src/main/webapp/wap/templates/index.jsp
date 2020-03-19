<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<!--解决uc更改默认字体大小-->
	<meta name="wap-font-scale" content="no"/>
	<title>web</title>
	<link rel="stylesheet" type="text/css" href="css/public.css"/>
	<link rel="stylesheet" href="css/index.css" />
	<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="js/common.js"></script>
	
	<script src="js/iscroll.js" type="text/javascript" charset="utf-8"></script>
	<!--<script src="js/scroll.js" type="text/javascript" charset="utf-8"></script>-->
	<!--angularJs-->
	<!--<script src="js/angular.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/app.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/index.js" type="text/javascript" charset="utf-8"></script>-->
<style type="text/css">
	html{display: none;}
	body iframe{
		width: 100%;border: 0;position: fixed;left: 0;top:0;
	}
</style>
<script type="text/javascript">
	if(top == self){
		document. documentElement. style. display='block';
	}else{
		top.location = selef.location;
	}
</script>
</head>
<body>
	
	<div class="viewport">
		<div style="height: 1.12rem;">&nbsp;</div>
		<!--搜索-->
		<div class="header_search">
			<i class="icon_search"></i>
			<input type="text" placeholder="搜索：今日新闻" class='header_ipt'/>
			<i class="icon_message"></i>
		</div>
		
		<!--新闻 下拉刷新-->
		<div id="wrapper">
	        <div class="news scroller">
				<ul class="clearfix" id="thelist">
					<div id="pullDown">
						<span class="pullDownIcon"></span><span class="pullDownLabel">Pull down to refresh...</span>
					</div>
					
					<!--类型为1 的新闻 左图，右标题-->
					<li class="news1 clearfix">
						<a href="index_article1.html">
							<div class="img fl"><img src="images/news1_1.png" alt="图片出处" /></div>
							<div class="news1_title fl">
								<h4 class="title">目前大部分有关iScroll的例子和教程都是基于iScroll4的目前大部分有关iScroll的例子和教程都是基于iScroll4的</h4>
								<p><span>来源</span><i class="this_news_close"></i></p>							
							</div>
						</a>
					</li>
					
					<!--类型2的新闻，图集上标题，下图-->
					<li class="news2">
						<a href="index_article2.html">
							<h4 class="title">目前大部分有关iScroll的例子和教程都是基于iScroll4的目前大部分有关iScroll的例子和教程都是基于iScroll4的</h4>
							<ul class="clearfix news_imgs">
								<li><img src="images/news2_03.png" alt="" /></li>
								<li><img src="images/news2_03.png" alt="" /></li>
								<li><img src="images/news2_03.png" alt="" /></li>
							</ul>
							<p class="clearfix"><span>新闻要事</span><font>图集</font><i class="this_news_close"></i></p>
						</a>
					</li>
					
					<!--类型3 的新闻，视频-->
					<li class="news3">
						<a href="javascript:;">
							<h4 class="title">美国白天出现月食</h4>
							<div class="news3_content">
								<a href="javascript:"><img src="images/video1_03.jpg" alt="" /></a>
								<i class="this_news_time">03:20</i>
							</div>
							<p class="clearfix"><span>发布者</span><i class="this_news_close"></i></p>
						</a>
					</li>
					
					<!--类型4 广告-->
					<li class="news4">
						<a href="javascript:;">
							<h4 class="title">传奇霸业</h4>
							<div class="news4_content">
								<img src="images/ad1_03.jpg" alt="" />
							</div>
							<p class="clearfix"><span>发布者</span><font>广告</font><i class="this_news_close"></i></p>
						</a>
					</li>
				
					<div id="pullUp">
						<span class="pullUpIcon"></span><span class="pullUpLabel"></span>
					</div>
				</ul>
			</div>
		</div>
		
		<!--适用于滚动内容只包含文字、图片，并且所有的图片都有固定的尺寸-->
		 <!--<script src="js/iscroll.js"></script>-->
        <!--<script>
                var myscroll;
                function loaded(){
                  myscroll=new iScroll("wrapper");
                }
               window.addEventListener("DOMContentLoaded",loaded,false);
               
//             function
         </script>-->
			<iframe src=""></iframe>
		
	</div>
	
	<!--尾部-->
		<div class="footer">
			<ul class="clearfix">
				<li class="on">
					<a href="index.html">
						<i class="footer_home"></i>
						<p>首页</p>
					</a>
				</li>
				<li>
					<a href="javascript:;">
						<i class="footer_home"></i>
						<p>贴吧</p>
					</a>
				</li>
				<li>
					<a href="game.html">
						<i class="footer_home"></i>
						<p>游戏</p>
					</a>
				</li>
				<li>
					<a href="javascript:;">
						<i class="footer_home"></i>
						<p>直播</p>
					</a>
				</li>
				<li>
					<a href="javascript:;">
						<i class="footer_home"></i>
						<p>好友</p>
					</a>
				</li>
				<li>
					<a href="javascript:;">
						<i class="footer_home"></i>
						<p>我的</p>
					</a>
				</li>
			</ul>
		</div>
		<!--angulaJs 尾部-->
		<!--<nav-footer></nav-footer>-->
	<script type="text/javascript">
		//footer 
		function footerPage(){
			$('.footer li a').click(function(){
					$(this).parent().addClass('on').siblings().removeClass('on');
					
					//iframe
					$('.viewport').addClass('amNone');		
					$('body').children('#ifm').remove();
					var index = $('.footer li a').index($(this));
					switch (index){
						case 0:
							$('body').children('#ifm').remove();
							$('.viewport').removeClass('amNone');
							break;
						case 1:
							var iframe_= '<iframe src="comeba.html" id="ifm"></iframe>';
							break;
						case 2:
							var iframe_= '<iframe src="comeba.html" id="ifm"></iframe>';
							break;
						case 3:
							var iframe_= '<iframe src="game.html" id="ifm"></iframe>';
							break;
						case 4:
							var iframe_= '<iframe src="game.html" id="ifm"></iframe>';
							break;
						case 5:
							var iframe_= '<iframe src="game.html" id="ifm"></iframe>';
							break;
						default:
							break;
					}
					$('body').prepend(iframe_);
					$('#ifm').height($(window).height());
				})
		}
	
		
	</script>
</html>