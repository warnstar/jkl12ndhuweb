<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
<body>
<t:formvalid formid="formobj" dialog="true"  layout="div"  action="ZeroGoodsManagement.do?doChangeZeroLuckId">
   <input type="hidden"  name="id" id="id" value="${id}">
	<fieldset class="steps" >
	<div class="form" >
		修改条件：
		1.如果修改的值不正确，修改会失败。
	</div>
	<div class="form" >
		请输入您想要指定的中奖用户的抢夺码（注：请勿随意捏造数据，否则后果自负！）：
	</div>
	<div class="form" >
	<label class="Validform_label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <input class="inputxt" name="luckyid" id="luckyid" datatype="n8-8" errormsg="请输入正确的数字！"> 
	<span class="Validform_checktip"></span>
	</div>
	</fieldset>
</t:formvalid>
	