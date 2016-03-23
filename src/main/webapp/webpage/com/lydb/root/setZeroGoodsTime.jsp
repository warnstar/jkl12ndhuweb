<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
<body>
<t:formvalid formid="formobj" dialog="true"  layout="div"  action="ZeroGoodsManagement.do?DosetZeroGoodsTime">
   <input type="hidden"  name="id" id="id" value="${id}">
	<fieldset class="steps" >
	<t:dictSelect field="time" typeGroupCode="settime" defaultVal="default" title="开奖时间间隔"></t:dictSelect> 
	</fieldset>
</t:formvalid>
	