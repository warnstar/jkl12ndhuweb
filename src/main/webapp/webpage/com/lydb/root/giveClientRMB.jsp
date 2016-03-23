<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
<body>
<t:formvalid formid="formobj" dialog="true"  layout="div"  action="AppClientManagement.do?giveClientRMB">
   <input type="hidden"  name="id" id="id" value="${id}">
	<fieldset class="steps" >
	
	<div class="form" >
			请输入赠送的抢币个数(一次赠送不可多余1000个)：
	</div>
	<div class="form" >
	<label class="Validform_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <input class="inputxt" name="rmb" id=""rmb"" datatype="n1-4" errormsg="请输入正确的数字！"> 
	<span class="Validform_checktip"></span>
	</div>
	<div class="form" >
		注：此操作不可逆，请谨慎处理！
	</div>
	</fieldset>
</t:formvalid>
 </body>