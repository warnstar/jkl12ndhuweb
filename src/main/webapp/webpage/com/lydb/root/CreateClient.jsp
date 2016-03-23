<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加新用户</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>

 </head>


<body style="background: #dadada">
<t:formvalid formid="formobj" dialog="true" layout="div"  action="" beforeSubmit="callbac()" usePlugin="password"  tiptype="4">
	<input type="hidden" id="id" />
	
	<h1 style="font-size:32px;text-align:center;">新建用户 </h1>
	<fieldset class="steps" >
	<div class="form" >
	<label class="Validform_label"> 手机号： </label> <input class="inputxt" name="mobile" id="mobile" maxlength="11" datatype="m" errormsg="手机号非法!">
	<span class="Validform_checktip"></span>
	</div>
	<div class="form">
	<label class="Validform_label"> 密码： </label> 
	<input class="inputxt"    type="password" value="" name="password" plugin="passwordStrength" maxlength="15" datatype="*6-15" errormsg="密码范围在6~15位之间！">
	<span class="Validform_checktip" >密码范围在6~15位之间！</span>

	<span class="passwordStrength" style="display: none;"> 
	<b>密码强度：</b> <span>弱</span><span>中</span><span class="last">强</span> 
	</span>
	</div>
	<div class="form">
	<label class="Validform_label"> 确认密码： </label>
	<input class="inputxt" type="password"  id="password" datatype="*" recheck="password"  maxlength="15"  errormsg="您两次输入的帐号密码不一致！">
	<span class="Validform_checktip"></span>
	
	</div>
	<div class="form">
	<label class="Validform_label"> 用户昵称： </label> <input class="inputxt" name="clientName" id="clientName" datatype="*" maxlength="15" errormsg="用户昵称不能为空！">
	<span class="Validform_checktip"></span>
	</div>
	<div class="form">
	<label class="Validform_label"> 用户QQ： </label> <input class="inputxt" name="clientQq" id="clientQq" datatype="*6-15" maxlength="15" errormsg="QQ号有误！">
	<span class="Validform_checktip"></span>
	</div>
		
	</fieldset>
		<button  id="btn_sub" >创建用户</button>

		<button  id="btn_reset" >取消</button>

	
</t:formvalid>
	 </body>
	 
	 <script>
	 function callbac(){
	
		
		$.post(

								url="<%=basePath%>/AppClientManagement.do?doAdd",
								data={
										mobile : $("#mobile").val(),
										password : $("#password").val(),
										clientName : $("#clientName").val(),
										clientQq : $("#clientQq").val()
									  },
								function(result){
									alertTip(result.msg);
									$("#btn_reset").click();
								},'json');
		
			
		 }
	
	 </script>