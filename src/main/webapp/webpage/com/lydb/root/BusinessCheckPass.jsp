<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>商家管理-商家申请审核</title>
    <link rel="stylesheet" href="webpage/js/all.css">
    
    <link rel="stylesheet" href="webpage/js/h_style.css">
    <style>
         @media (max-width: 1200px) and (min-width: 900px){
            .h_mx {margin: 20px 0 0 5px;}}

         @media (max-width: 1400px) and (min-width: 1201px){
            .h_mx {margin: 20px 0 0 45px;}
        }
          @media (max-width: 1500px) and (min-width: 1401px){
            .h_mx {margin: 20px 0 0 56px;}
        }
         @media (max-width: 2000px) and (min-width: 1501px){
            .h_mx {margin: 20px 0 0 64px;}
        }
    </style>
</head>


<!-- 已审核商家申请详情弹窗 要调用时按钮加上类名tan3即可 -->
<div class="theme-popover theme3"style="display:block;" ><!-- 点击按钮，调用theme3事件， -->
    <div class="theme-poptit">
        <h1 class="fl" style="font-size:32px;text-align:center;">商家信息
        <span class="status fr">处理：<b><c:choose>
		    <c:when test="${fn:contains(business.status , '1')}">
		     	  已通过
		    </c:when>
		    <c:when test="${fn:contains(business.status , '2')}">
		    	 已拒绝
		    </c:when>
		    <c:otherwise>
		      
		    </c:otherwise>
	</c:choose></b></span>
        </h1>

    </div>
    <!-- 弹窗内容模块 开始-->
    <div class="popbod" >
         <div class="h_sure_cx">
          <div class="all">
                <span class="h_sure_left dk fl mingc">商家编号：</span>
                <span class="h_sure_right dk fl" ><input type="text" id="theNumber" value="${business.theNumber}"  class="reson mingc"  style="width:260px;"></span>
            </div>
            <div class="all">
                <span class="h_sure_left dk fl mingc">用户名：</span>
                <span class="h_sure_right dk fl" ><input type="text" id="mobile" value="${business.mobile}" class="reson mingc"  style="width:260px;"></span>
            </div>
            <div class="all">
                <span class="h_sure_left dk fl mingc">昵称：</span>
                <span class="h_sure_right dk fl" ><input type="text" id="name" value="${business.name}" class="reson mingc" style="width:260px;"></span>
            </div>
            <div class="all">
                <span class="h_sure_left dk fl mingc">店名：</span>
                <span class="h_sure_right dk fl" ><input type="text" id="shopname" value="${business.shopname}" class="reson mingc"  style="width:260px;"></span>
            </div>
            <div class="all">
                <span class="h_sure_left dk fl mingc">QQ：</span>
                <span class="h_sure_right dk fl" ><input type="text" id="businessQq" value="${business.businessQq}"  class="reson mingc" style="width:260px;"></span>
            </div>
            <div class="all">
                <span class="h_sure_left dk fl mingc">验证信息：</span>
                <span class="h_sure_right dk fl" >
                   
                    <textarea class="information mingc" >${business.information}</textarea>
                </span>
            </div>
        </div>
       
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
  

</div><!-- theme-popover -->
</body>
</html>