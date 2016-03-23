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
    <title>普通商品管理-发布商品</title>
    <link rel="stylesheet" href="<%=path %>css/all.css">
    <link rel="stylesheet" href="<%=path %>css/denglu.css">
    <link rel="stylesheet" href="<%=path %>css/h_style.css">
    <style>
        .h_register_all{width:60%;}
        .register_all{width: 100%;}
        .re_input,.re_text{width:100%;}
        .number{bottom: 36px;}
        .cov_img{width: 250px !important;height:250px !important;padding:0 !important;margin-left: 50%;}
        .cov_img img{width:100%;height: 100%;}
        .cov_img2{width: 320px !important;height:180px !important;padding:0 !important;margin-left: 50%;}
        .cov_img2 img{width:100%;height: 100%;}
        .yes{width:20px;height:20px;border-radius: 50%;line-height: 20px;text-align: center;color:#fff;font-size:16px;right:8px;bottom:8px;background: #4da801;}
        .gou{display: none;}

    </style>
    
</head>
<body>

<div class="l_right ">
  


    <div class="l_moban"><!-- 模板 style -->
        <div class="h_content_mian l_all">
           
            <div class="h_mian_bt  l_all">发布商品</div>       

            <div class=" h_content_all l_all dongtai" style="border:none;">
                <div class="h_register_all" style="margin-top:40px;">
                    <div class="register_all">
                        <ul>
                            <li  style="margin-bottom:5px;">商品名称</li>
                            <li  style="margin-bottom:5px;"><input type="text" class="re_input" id="goods_name" maxlength="50"></li>

                            <li></li>
                            <li style="text-align:left;color:red;">*注：若商品为鞋子或衣服，请标上码数,名字不能有空格!</li>

                            <li>商品单价（元）</li>
                            <li><input type="number" onkeyup="this.value=this.value.replace(/\D|^0$/g,'1')" onchange="this.value=this.value.replace(/\D|^0$/g,'1')" class="re_input" id="good_rmb" name="tt" ></li>

                            <li></li>
                            <li style="text-align:left;color:red;">*注：商品价格不得小于20元，并且为整数</li>

                            <li style="margin-bottom:5px;">参与上架的数量</li>
                            <li style="margin-bottom:5px;"><input type="number" onkeyup="this.value=this.value.replace(/\D|^0$/g,'1')" onchange="this.value=this.value.replace(/\D|^0$/g,'1')" class="re_input numsss" id="goods_num" name="tt"  ></li>

                            <li style="height:40px;"></li>
                            <li style="text-align:left;color:red;height:40px;line-height:20px;">*注：数量=期数。每一个商品期数的表达方式为n-m。表示该商品为该平台第n件公布的商品，本商品已被抢夺了（m-1）次。</li>

                           <%-- <li style="height:80px;">简介</li>
                            <li style="height:80px;"><textarea class="re_text" maxlength="50" id="goods_content"></textarea></li>--%>

						   <li>商品封面（建议像素640*640,不超过200k）</li>
                            <li><div class="cover">从照片库中选择<input type="file" class="re_input" id="chuan"></div></li>
                            <li  class="cov_img pr" >
                                <img src="" alt="" class="cover_img" width="640" height="640">
                            </li>

                            <li style="height:40px;"></li>
                            <li style="height:40px;"><a href="javascript:;" class="he_ok dk fenmian">确认上传</a></li>
                        <%--  <li id="container">
                           		 <div>
	                           	    <input type="hidden" id="uptoken_url" value="<%=basePath%>/qiniu.do?uptoken">
	                            </div>
                            <div class="cover" >从照片库中选择<input type="file" class="re_input"  id="pickfiles">
                            </div>
                            
                            </li>


 							<li id="preview" style="height:100px;" class="cov_img" width="640" height="640">
                            </li>--%>
                            <li>详情页面图片（建议像素640*320,不超过200k）</li>
                            <li><div class="cover">从照片库中选择<input type="file" class="re_input" id="chuan2"></div></li>
                            <li  class="cov_img2 pr" >
                                <img src="" alt="" class="cover_img2" width="640" height="360">
                                <span class="po dk yes gou">√</span><!-- gou就是让小圆消失的类 -->
                            </li>  

                            <li style="height:40px;"></li>
                            <li style="height:40px;"><a href="javascript:;" class="he_ok dk submit">确认上传</a></li> 					
                          <li>选择类别</li>
                            <li>
                                <select id="goods_type" class="choose">
                                    <option >服装</option>
                                    <option >鞋包</option>
                                    <option >配饰</option>
                                    <option >运动</option>
                                    <option >珠宝</option>
                                    <option >数码</option>
                                    <option >家电</option>
                                    <option >美妆</option>
                                    <option >母婴</option>
                                    <option >家居</option>
                                    <option >美食</option>
                                    <option >百货</option>
                                    <option >汽车</option>
                                    <option >文娱</option>
                                    <option >虚拟</option>
                                    <option >本地</option>
                                </select>
                            </li> 

                            <li class="he_shi"></li>
                            <li class="he_shi">
                                <input type="checkbox" name="radio" value="yes" class="radio" id="10goods">
                                是否设为十元专区商品<br>*设为10元专区后，该商品参与一次需要<b>10的倍数</b>的价格
                            </li>

                            <li style="width:100%;"><span class="dk pub_bt">商品优惠券信息</span></li>

                            <li>优惠券链接</li>
                            <li><input type="text" class="re_input" id="coupon_url" placeholder="http://www.baidu.com"></li>

                            <li>优惠券面值（元）</li>
                            <li><input type="number" class="re_input" id="coupon_value" onkeyup="this.value=this.value.replace(/\D|^0$/g,'1')" onchange="this.value=this.value.replace(/\D|^0$/g,'1')"  name="tt"  placeholder="10"></li>
                            <li style="height:90px;"></li>
                            <li style="text-align:left;color:red;height:90px;line-height:20px;">
                                注：若审核通过后需要付保证金和服务费。保证金=单价*数量（期数）*40%，服务费=单价*数量（期数）*10%。当该商品的晒单数量=该商品的期数时，保证金将如数退回。
                            </li> 
                            <li style="width:100%;height:40px;"><input type="button" id="publishnormal" class="re_btn zc" value="发布">

                            </li>
                        </ul>
                    </div>
                </div>        
                    
            </div>

								

        </div><!-- h_content_mian -->
        
    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->
<div class="theme-popover-mask"  ></div><!-- 所有弹窗共用这一个黑屏，不需要复制 -->
<!-- 修改成功 调用的类是 tan1-->

<div class="theme-popover theme1"  style="top:60%;margin-top:200px"><!-- 点击按钮，调用theme1事件， -->
    <div class="theme-poptit">
       <!--  <h3 class="fl" >修改提醒</h3> -->
        <a href="javascript:;" title="关闭" class="close fr returnto">×</a>
    </div>
    <!-- 弹窗内容模块 开始-->
    <div class="popbod">
        <div class="h_sure_cx">
            <p class="sure_del" style="text-align:center;">您的商品申请已成功，请等待管理员审核后缴费</p>
        </div>
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
    <div class="popbod_bottom"><!-- 此处放按钮 -->
            
            <div class="buttom_del buttom_del_bg2 del returnto" id="sure_dele" style="float:right;">确认</div><!-- 关闭，调用del这个类，公用的 -->
    </div><!-- popbod_bottom -->

</div><!-- theme-popover -->

<!-- 修改失败 调用的类是 tan2-->
<script src="<%=path %>js/jquery-1.11.2.min.js"></script>
<script src="<%=path %>js/menu.js"></script>
<script src="<%=path %>js/plupload/plupload.full.min.js"></script>
<script src="<%=path %>js/plupload/i18n/zh_CN.js"></script>
<script src="<%=path %>js/ui.js"></script>
<script src="<%=path %>js/qiniu.js"></script>
<script src="<%=path %>js/highlight.js"></script>
<script src="<%=path %>js/main.js"></script>
<script src="<%=path %>js/tan.js"></script>
<script>

/* 封面 */    
close();
id="";
headurl=""
$(function(){
    var formdata = new FormData();
    $("#chuan").on("change", function(){
        var files = !!this.files ? this.files : [];
        if (!files.length || !window.FileReader) return;
        for(var i=0;i<files.length;i++){
            if (/^image/.test( files[i].type)){
                var reader = new FileReader();
                reader.readAsDataURL(files[i]);
                formdata.append("img[]", files[i]);
                reader.onloadend = function(){

                    $(".cov_img img").attr({"src":this.result});//将克隆对象里的图片替换成上传的图像;

                }
            }
        }
    });
    $('.fenmian').click(function(){
        $.ajax({
            url:"<%=basePath %>/storeBusiness.do?addfirstjpg",
            type:'POST',
            data:formdata,
            dataType:'json',
            mimeType:"multipart/form-data",
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            success:function(data){
                if (data.success) {
                    alert(data.msg);
                    headurl=data.obj;

                }else{
                    alert(data.msg);
                }
            },
            error:function(XmlHttpRequest,textStatus,errorThrown){
                alert('添加失败!');
                console.log(XmlHttpRequest);
                console.log(textStatus);
                console.log(errorThrown);
            }
        });

    });
});
$(function(){

        var formdata = new FormData();
        $("#chuan2").on("change", function(){
            var files = !!this.files ? this.files : [];
            if (!files.length || !window.FileReader) return;
            for(var i=0;i<files.length;i++){
                if (/^image/.test( files[i].type)){
                    var reader = new FileReader();
                    reader.readAsDataURL(files[i]);
                    formdata.append("img[]", files[i]);                  
                    reader.onloadend = function(){
                          $(".cov_img2 img").attr({"src":this.result});//将克隆对象里的图片替换成上传的图像;
                        
                    }
                }
            }
        });
        $('.submit').click(function(){
            $.ajax({
                url:"<%=basePath %>/storeBusiness.do?publish_detail_jpg",
                type:'POST',
                data:formdata,
                dataType:'json',
                mimeType:"multipart/form-data",
                processData: false,  // 告诉jQuery不要去处理发送的数据
                contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
                success:function(data){
                            if (data.success) {
                                alert(data.msg);
                            id=data.obj;
                            }else{
                              alert(data.msg);
                            }
                        },
                error:function(XmlHttpRequest,textStatus,errorThrown){
                                alert('添加失败!');
                                console.log(XmlHttpRequest);
                                console.log(textStatus);
                                console.log(errorThrown);
                        }
            });
          
        });
});
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
};

$("#publishnormal").bind('click',function() {
    var goodsName = $("#goods_name").val();
    if (goodsName == "") {
        alert("商品名不能为空！");
        return;
    }
    //去掉包含的空格
    goodsName=trim(goodsName);
    var goodsRmb = $("#good_rmb").val();
    if (goodsRmb == "" || goodsRmb <= 0) {
        alert("商品价格输入错误！");
        return;
    }
    else if (goodsRmb < 20) {
        alert("商品价格不能小于20元");
        return;
    }
    else if (!checkRate(goodsRmb)) {
        alert("必须是正整数");
        return;
    }
    var goodsNum = $("#goods_num").val();
    if (goodsNum == "" || goodsNum <= 0) {
        alert("商品数量输入错误！");
        return;
    }
    var goodsType = $("#goods_type").val();
    /*  var goodsHeadurl=$(".cover_img1").attr("value");*/
    var couponUrl = $("#coupon_url").val();
    var couponValue = $("#coupon_value").val();
    if ($("input[type='checkbox']").is(':checked')) {
        var goods10 = 1;
        if (goodsRmb % 10 != 0) {
            alert("商品价格输入错误！");
            return;
        }
    }
    if (id == "") {
        alert("请上传详情图片！");
        return;
    }
    if (headurl == "") {
        alert("请上传封面图片");
        return;
    }

    if (couponUrl != "") {
        if (!checkURL(couponUrl)) {
            alert("请输入正确的连接地址！");
            return;
        }
    }
    $.post(
            url="<%=basePath%>/storeBusiness.do?publish_normal",
            data={
                id : id,
                goodsName : goodsName,
                goodsRmb : goodsRmb,
                goodsNum : goodsNum,
                goodsType : goodsType,
                goodsHeadurl : headurl,
                couponUrl : couponUrl,
                couponValue : couponValue,
                goods10 : goods10
            },
            function(result){
                if(result.obj==1){
                    pop_show1();
                }else{
                    alert("发布失败！");
                }
            },'json');

});

function checkURL(URL){
    var str=URL;
//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
//下面的代码中应用了转义字符"\"输出一个字符"/"
    var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
    var objExp=new RegExp(Expression);
    if(objExp.test(str)==true){
        return true;
    }else{
        return false;
    }
}

function checkRate(input)
{
    var re = /^[1-9]+[0-9]*]*$/;

    if (!re.test(input))
    {
        return false;
    }else{
        return true;
    }
}
$(".returnto").bind('click',function(){
	$.post(
			 url="<%=basePath%>/storeBusiness.do?publish_normal_page",
			 data={},
			 function(result){
				 $('body').empty().html(result);
			 },'text');	
})

/* 上传详情图片 */
 
</script>
</body>
</html>