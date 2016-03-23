<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>规则编辑表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <link rel="stylesheet" type="text/css" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
     <style>
         #steps {
             width: 800px !important;
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
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>

 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" layout="div" action="ruleTableController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${ruleTablePage.id }">
	<input id="ruleName" name="ruleName" type="hidden" value="${ruleTablePage.ruleName }">
	<h1 style="font-size:32px;text-align:center;">${ruleTablePage.ruleName}</h1>
	<fieldset class="steps" >
	<div class="form" >
	<label class="Validform_label"> 规则内容: </label>
	<textarea  name="ruleContent" style="width:600px;height:400px;" id="ruleContent" datatype="*" errormsg="内容不能为空！">${ruleTablePage.ruleContent}</textarea>
	<span class="Validform_checktip"></span>
	</div>

	</fieldset>
	</t:formvalid>
 </body>
