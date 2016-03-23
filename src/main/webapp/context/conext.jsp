<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<style type="text/css">
	/*--停浮右边的客服，qq等图标--*/
	#aside_icon{
		position: fixed;
		top: 50%;
		right: 50px;
		width: 72px;
		height: 240px;
		margin-top: -120px;
		border: 3px solid #fff;
		border-radius: 3px;
	}
	#aside_icon a{
		display: block;
		width: 72px;
		height: 60px;
	}
	#aside_icon .kefu{
		background: url(<%=path%>images/aside_icon.png) no-repeat 0 0;
	}
	#aside_icon .kefu:hover{
		background-position: -72px 0;
	}
	#aside_icon .qq{
		background: url(<%=path%>images/aside_icon.png) no-repeat 0 -60px;
	}
	#aside_icon .qq:hover{
		background-position: -72px -60px;
	}
	#aside_icon .qr_code{
		background: url(<%=path%>images/aside_icon.png) no-repeat 0 -120px;
	}
	#aside_icon .qr_code:hover{
		background-position: -72px -120px;
	}
	#aside_icon .back_top{
		background: url(<%=path%>images/aside_icon.png) no-repeat 0 -180px;
	}
	#aside_icon .back_top:hover{
		background-position: -72px -180px;
	}
	.tel_area{
		position: absolute;
		top: -1px;
		right: 134%;
		width: 200px;
		padding: 10px;
		color: #fff;
		font-size: 18px;
		text-align: center;
		border-radius: 4px;
		background: rgba(0,0,0,0.8);
		filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#CC000000,endColorstr=#CC000000);
		display: none;
	}
	.tel_area:after{
		content: '';
		border: 10px solid transparent;
		border-left-color: rgba(0,0,0,0.8);
		position: absolute;
		left: 100%;
		bottom: 12px;
	}
	.qr_code_area{
		position: absolute;
		top: -143px;
		right: 134%;
		width: 270px;
		padding: 10px;
		text-align: center;
		border-radius: 4px;
		background: rgba(0,0,0,0.8);
		filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#CC000000,endColorstr=#CC000000);
		display: none;
	}
	.qr_code_area:after{
		content: '';
		border: 14px solid transparent;
		border-left-color: rgba(0,0,0,0.8);
		position: absolute;
		left: 100%;
		bottom: 20px;
	}
	.qr_code_area img{
		display: block;
	}
	.qr_code_area>div{
		padding-top: 5px;
		color: #fff;
		font-size: 20px;
	}
	.qr_code_area>div p{
		font-size: 26px;
		font-weight: 700;
		margin-bottom: 3px;
	}
</style>

<!--停浮右边的客服，qq等图标-->
<aside id="aside_icon">
	<a class="kefu" href="javascript:;" title="联系我们"></a>
	<!--qq提供一种是跳转到企业qq,一种是普通账号qq,（只要修改href的内容即可！！！。）。。。。。uin=480895202，数字是qq号码-->
	<!--跳转到企业qq-->
	<a class="qq" target="_blank" rel="nofollow" href="http://wpa.qq.com/msgrd?v=3&amp;uin=2926342287&amp;site=qq&amp;menu=yes" title="在线咨询"></a>
	<!--跳转到个人qq-->
	<!--<a class="qq" target="_blank" rel="nofollow" href="tencent://message/?uin=480895202&Site=121ask.com&Menu=yes" title="在线咨询"></a>-->
	<a class="qr_code" href="javascript:;" title="二维码"></a>
	<a class="back_top" href="javascript:;" title="回到顶部"></a>
	<div class="tel_area">
		电话：0316-2555213
	</div>
	<div class="qr_code_area">
		<img src="<%=path %>images/qr_code.png" alt="二维码" />
		<div>
			<p>扫一扫</p>
			关注一圆梦微信
		</div>
	</div>
</aside>
<script src="<%=path %>js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">

	//电话图标
	$('.kefu').hover(function(){
		$('.tel_area').show();
	},function(){
		$('.tel_area').hide();
	})

	//二维码
	$('.qr_code').hover(function(){
		$('.qr_code_area').show();
	},function(){
		$('.qr_code_area').hide();
	})

	//侧边栏回到顶部
	$('#aside_icon .back_top').on('click',function(){
		$('body,html').animate({scrollTop:0},500);
	});

</script>