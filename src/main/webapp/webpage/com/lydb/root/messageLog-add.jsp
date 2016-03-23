<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>推送消息</title>
    <t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
    <link rel="stylesheet" type="text/css" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
    <style>
        #steps {
            width: 890px !important;
            overflow: hidden;
        }

        #wrapper {
            -moz-box-shadow: 0px 0px 3px #aaa;
            -webkit-box-shadow: 0px 0px 3px #aaa;
            box-shadow: 0px 0px 3px #aaa;
            -moz-border-radius: 10px;
            -webkit-border-radius: 10px;
            border-radius: 10px;
            border: 2px solid #fff;
            background-color: #f9f9f9;
            width: 892px !important;
            margin: 0 auto;
            padding-bottom: 20px;
            overflow: hidden;
        }
    </style>
</head>
<body  style="background: #dadada">

<t:formvalid formid="formobj" dialog="false" layout="div" action="" beforeSubmit="callbac()" tiptype="4">
    <input id="id" name="id" type="hidden" value="${messageLogPage.id }">
    <h1 style="font-size:32px;text-align:center;">新建推送消息 </h1>
    <fieldset class="steps">
        <div class="form">
            <label class="Validform_label"> 请输入标题： </label>
            <br>
            <input class="inputxt" type="text" name="messageTitle" id="messageTitle" datatype="*" errormsg="请输入标题！">
            <span class="Validform_checktip"></span>
        </div>
        <div class="form">
            <label class="Validform_label"> 请输入消息简介： </label>
            <br>
            <input class="inputxt" type="text" id="messageBrief" name="messageBrief" datatype="*" errormsg="请输入消息简介!">
            <span class="Validform_checktip"></span>
        </div>
        <div class="form">
            <label> 请输入消息内容详情（只可输入文字）： </label>
            <br>
	  <textarea name="messageContent" style="width:800px;height:400px;" id="messageContent" datatype="*"
                errormsg="内容不能为空！">${ruleTablePage.ruleContent}</textarea>
            <span class="Validform_checktip"></span>
        </div>

    </fieldset>
    <button id="btn_sub">推送消息</button>

    <button id="btn_reset">取消</button>

</t:formvalid>
</body>
<script>
    function callbac() {


        $.post(
                url = "<%=basePath%>/messageLogController.do?doAdd",
                data = {
                    id: $("#id").val(),
                    messageTitle: $("#messageTitle").val(),
                    messageBrief: $("#messageBrief").val(),
                    messageContent: $("#messageContent").val()
                },
                function (result) {
                    alertTip(result.msg);
                    $("#btn_reset").click();
                }, 'json');


    }

</script>