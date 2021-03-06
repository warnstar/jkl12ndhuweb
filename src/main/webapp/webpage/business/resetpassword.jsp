<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath()+"/webpage/business/";
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<%@include file="/context/conext.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>注册</title>
    <link rel="stylesheet" href="<%=path%>css/all.css">
    <link rel="stylesheet" href="<%=path%>css/denglu.css">
    <style type="text/css">
        html{
            height: 100%;
            position: relative;
            background: url(http://7xp1b8.com1.z0.glb.clouddn.com/649d844e-33d9-45cc-b76e-9e566afd5724) center center no-repeat;
        }

        body{background: rgba(0,0,0,0.4);width: 100%;height: 100%;position: absolute;top:0;left:0;z-index: 1;}
        @media (max-width: 1910px){
            body{height:auto;}
        }
        .register_all{
            background: rgba(0,0,0,0.4);
            border-radius: 10px;
            color: #fff !important;
        }
        .re_btn{height: 40px;line-height: 40px;}
        .zc{margin-left: 41.5%;}
        .re_input,.re_text{background: none;height:40px;color: #fff;border-radius: 4px;border:1px solid #fff;padding-left: 5px;}
        .re_text{height: 70px;padding-top: 5px;font-family: "微软雅黑";}
        .h_register_all h2,.register_all ul li{color: #fff !important;}
        .h_register_all h2{padding-top: 10px;}
        .register_all ul li{height: 40px;line-height: 40px;}
        .register_all ul li:nth-child(odd){
            width:40%;
        }
        .register_all ul li:nth-child(even){
            width:60%;
        }
    </style>
</head>
<body>

<div class="hei"></div>
<div class="h_register_all">

    <div class="register_all">
        <h2>修改密码</h2>
        <ul>
            <li>手机号：</li>
            <li><input type="text" class="re_input" id="phone"></li>
            <li>输入验证码：</li>
            <li>
                <input type="text" class="re_input" id="code" style="width:150px;">
                <input type="button" id="btn" class="re_btn sendcode" value="发送验证码">
            </li>
            <li>密码：</li>
            <li><input type="password" class="re_input" id="pass"></li>
            <li style="width:100%;height:40px;"><input type="button" class="re_btn zc resetpassword" value="确定"></li>
        </ul>
    </div>
</div>
<div class="theme-popover-mask"></div><!-- 所有弹窗共用这一个黑屏，不需要复制 -->
<!-- 修改成功 调用的类是 tan1-->
<div class="theme-popover theme1"><!-- 点击按钮，调用theme1事件， -->
    <div class="theme-poptit">
        <!--  <h3 class="fl" >修改提醒</h3> -->
        <a href="javascript:;" title="关闭" class="close fr">×</a>
    </div>
    <!-- 弹窗内容模块 开始-->
    <div class="popbod">
        <div class="h_sure_cx">
            <p class="sure_del" style="text-align:center;">修改成功！！！</p>
        </div>
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
    <div class="popbod_bottom"><!-- 此处放按钮 -->

        <div class="buttom_del buttom_del_bg2 del returnto" id="sure_dele" style="float:right;">确认</div>
        <!-- 关闭，调用del这个类，公用的 -->
    </div><!-- popbod_bottom -->

</div><!-- theme-popover -->
</body>
<script src="<%=path %>js/jquery-1.11.1.min.js"></script>
<script src="<%=path %>js/menu.js"></script>
<script src="<%=path %>js/tan.js"></script>
<script>
    $("#phone").blur(function () {

        if (!/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i.test($("#phone").val())) {
            alert("手机号输入错误！");
        }
    })

    $("#pass").blur(function () {
        var password = $("#pass").val();
        if (password == "") {
            alert("不能为空！");
        }else if(password.length<6){
            alert("密码长度不够");
            return;
        }
    })
    $(".resetpassword").bind("click",function(){
        close();
        var mobile = $("#phone").val();
        if (mobile == "") {
            alert("手机号不能为空");
            return;
        }
        var code = $("#code").val();
        var password = $("#pass").val();
        if (password == "") {
            alert("密码不能为空");
            return;
        }else if(password.length<6){
            alert("密码长度不够");
            return;
        }
        $.post(
                url="<%=basePath%>/storeLoginBusiness.do?resetpass",
                data={
                    mobile: mobile,
                    code: code,
                    password: password
                },
                function(result){
                  if(result.obj=1){
                        pop_show1();
                  }else{
                      alert(result.message)
                  }
                }
        )
    })
    $(".sendcode").bind('click', function () {
        var mobile = $("#phone").val();
        if (mobile == "") {
            alert("手机号不能为空");
            return;
        }
        $(this).removeClass("sendcode");
        time(this);
        $.post(
                url = "<%=basePath%>/storeLoginBusiness.do?sendcode",
                data = {
                    mobile: mobile
                },
                function (result) {
                    if(result.obj){
                        alert("获取验证码成功");
                    }
                }, 'json');
    });
    $(".returnto").bind('click', function () {
        window.location.href="<%=basePath%>/storeLoginBusiness.do?login_page";
    })


    var wait=60;
    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value = "发送验证码";
            wait = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value = "还剩余(" + wait + "秒)";
            wait--;
            setTimeout(function () {
                        time(o)
                    },
                    1000)
        }
        $("#btn").addClass("sendcode");
    }
</script>
</html>
