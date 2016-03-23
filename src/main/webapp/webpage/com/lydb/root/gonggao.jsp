<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${ruleTablePage.ruleName}</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="background: #dadada">
    <div style="height:50px;">
        <div style="float:left;font-size:16px;line-height: 25px;">最后修改者:<span style="font-family:微软雅黑;font-size:16px;color:red;">${ruleTablePage.changeName} </span> <br>最后修改时间:<span style="font-family:微软雅黑;font-size:16px;color:red;">${ruleTablePage.createTime}</span></div>
        <button  id="btn_sub" style="float:right;width:150px;height:50px;background:#b0dcbd;font-family:微软雅黑;font-size:16px;color:red; ">发布${ruleTablePage.ruleName}</button>
    </div>
    <div id="mybody" style="width: 99%;background: #fdfdfe;border-radius: 6px;margin:10px auto;padding:10px 0 0 10px;">
        ${ruleTablePage.ruleContent}
    </div>
</body>
<script>
    var heigth;
    var width;
    window.onload = function () {
        heigth  =window.innerHeight-91;
        width = window.innerWidth ;
       /* $("#mybody").css({"height": heigth +"px "});*/
    }
    window.onresize = function () {
        heigth  =window.innerHeight-91;
        width = window.innerWidth ;
       /* $("#mybody").css({"height": heigth +"px "});*/
    }
    $("#btn_sub").on("click",function(){
        if(width<950){
            width=950;
        }else{
            width=width*0.9;
        }
        createwindow("修改公告", "ruleTableController.do?gonggao&id=${ruleTablePage.id}", width, heigth * 0.9);
    });
</script>
