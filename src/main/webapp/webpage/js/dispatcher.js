/**
 * 常用js函数封装在里面，以后继续添加
 * 方宇
 */

var g_prePath = "";
var g_tecPath = "";
var g_helpTelNo = "";
var g_pageSize = 200;
//网络状态
var networkStatus = "";
$(function() {
	jQuery.ajaxSetup({
		traditional : true,
		timeout: 300000,
		error:function(jqXHR, textStatus, errorThrown){
			if(textStatus.toLowerCase( )=='timeout'){
				alert("请求超时，请稍后重试");
			}else{
				console.log( "jqXHR.status" + jqXHR.status);
				if(jqXHR.status!=200 && jqXHR.status != 0) {
					/*alert("网络有异常：" + jqXHR.status);*/
					console.log("网络有异常：" + jqXHR.status);
				}
			}
	    }
	});
});
/**
 * 页面跳转分发 
**/
function dispatch(uri,type){
	if(uri == '' || uri == undefined || uri == null){
		alert("错误的连接");
		return;
	}
	if(type != ''){
		if( type==15){
			var result = checkLogin();
			if(!result) {
				uri = "";
				type="24";
			}
			/*$(".inner .on").removeClass("on");
			$(".item item4").addClass("on");*/
		}
		try{
			recordPageClick(type);
		}catch(e){
		}
	}
	
	window.location=uri;
};
//获得微信的网络状态
function getNetworkState(){
	/**获取网络状态**/
	/*network_type:wifi wifi网络
	network_type:edge 非wifi,包含3G/2G
	network_type:fail 网络断开连接
	network_type:wwan（2g或者3g*/
	WeixinJSBridge.invoke('getNetworkType',{},
		function(e){
	    	WeixinJSBridge.log(e.err_msg);
	    	return e.err_msg;
	    });
}

function commonPost(url, data, callbck, type){
	
	$.post(url, data, callbck, type);
}
function commonGet(url, data, callbck, type){
	$.get(url, data, callbck, type);
}

function checkLogin2(msg) {
	if(msg.msgCode=="-1") {
		   window.location = "/wx_user/login.html"
	   }
}

function checkLoginOutTime(msg) {
	if(msg.msgCode=="-1") {
		alert("登录超时,请重新登录.");
		clearCookie();
		window.location = "/wx_user/index.html";
	}
}

//format :　KEY＝VALUE；．．．．
function addCookies(key, value, expire){
	var str = key + "=" + escape(value);
	if(expire > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失 
		var date = new Date(); 
		var ms = expire*3600*1000; 
		date.setTime(date.getTime() + ms); 
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str +"; path=/"; 
}

//format :　KEY＝VALUE；．．．．
function addCookie(key, value, expire){
	var str = key + "=" + encodeURIComponent(value);
	if(expire > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失 
		var date = new Date(); 
		var ms = expire*3600*1000; 
		date.setTime(date.getTime() + ms); 
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str +"; path=/"; 
}

//获取Cookie
function getCookie(key){
	var strCookie=document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie=strCookie.split("; "); 
	if(arrCookie == ''){
		arrCookie = strCookie.split(";");
	}
	var val = ""; 
	//遍历cookie数组，处理每个cookie对 
	for(var i=0;i<arrCookie.length;i++){ 
		var arr=arrCookie[i].split("="); 
		//找到名称为key的cookie，并返回它的值 
		
		if(key==arr[0]){
			if(key == 'name' || key == 'carlicence_cookie' || key == 'series_cookie' || key == 'brand_cookie' || key == 'model_cookie'
				|| key=='engineno_cookie' || key=='carvin_cookie'){
				val = decodeURIComponent(arr[1]);
				val = val.replace("\"","");
				val = val.replace("\"","");
				val = val.replace(new RegExp(/\+/g)," ");
			} else {
				val=unescape(arr[1]);
			}
			break; 
		} 
	} 
	return val;
};

//去掉日期格式,后面多余的".0",只保留YYYY-MM-dd HH:mm:ss
function formatDate(dd){
	if(dd.lastIndexOf(".")>-1){
		return dd.substring(0,dd.lastIndexOf("."));
	}else{
		return dd;
	}
}

function logout() {
	//addCookies("token", "", 0);
	//addCookies("name", "", 0);
	//addCookies("phoneNum", "", 0);
	clearCookie();
	console.log(document.cookie);
	window.location = "";
}

function clearCookie(){
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
    	for (var i = keys.length; i--;)
    		document.cookie=keys[i]+'="";path=/;expires=' + new Date(0).toUTCString();
    }
    document.cookie='token="";path=wx_user;expires=' + new Date(0).toUTCString();
}
//移除cookie
function removeCookie(key){
	document.cookie=key+'="";path=/;expires=' + new Date(0).toUTCString();
}

//去除字符串空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
};

//验证手机号码格式
function isMobile(val){
	var regMobile=/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/;
	if(trim(val) == "" || !regMobile.test(val)) {
		return false;
	} else {
		return true;
	}
}

/**
 * 验证码设置倒计时demo
 *  myyzm_btn.on('click',function(){
		   var num=60;
		   var _this = $(this);
		   mobile = $("#mobile").val();
			if(_this.hasClass('disabled')){
				return;	
			}
			
			url='/cmm/v1/getValidCode.json';
	      if(checkMobile()){	
		     	alert('请输入正确的手机号码');
		   }else{
			   data = {
						"account" : mobile,
						"clientType" : '1',
						//"sign" : '123456'
						};
			  
				callbck = function(msg){
					if(msg != null && msg != undefined){
						if(msg.code == 0){
							//alert(msg.message);
							alert("短信发送成功，请查收。");
							
								_this.addClass('disabled');
								_this.val(num+'s后重新获取');
								
								//发送成功后才设置定时
								var timer = window.setInterval(function(){
									num--;
									_this.val(num+'s后重新获取');
									if(num == 0){
										window.clearInterval(timer);
										_this.removeClass('disabled');	
										_this.val('获取验证码');
										num = 60;
									}	
								},1000);
							
							
						} else{
							alert(msg.message);
							_this.removeClass('disabled');	
						}
					}
				};
				_this.addClass('disabled');
				commonPost(url, data, callbck, type);
		   }
		});
 * 
 * 
 */


/**
 * 检查是否登录，未登录跳到登录页面
 * @returns {Boolean}
 */
function checkLogin() {
	var token = getCookie("token");
	if(token=='') {
		alert("请先登录。")
		return false;
	} else {
		return true;
	}
}

//跳回首页
function backToIndex(){
	window.location="";
}

/**
 * 日期转换
 * 用法：
 * var time1 = new Date().Format("yyyy-MM-dd");
 * var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
 */
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


//修改弹出窗口的样式
function onAlert(msg){
	var html="<div id='dialog'>" 
		+ "<div class='title'>提示</div>"
		+ "<div class='content'>"+msg+"</div>"
		+ "<div class='close' onclick='javascript:closeBg();'>确定</div>"
		+ "</div>";
	$("body").append(html);
	var bh = $("body").height(); 
	var bw = $("body").width(); 
	$("body").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		});
	$("#dialog").show(); 
};

