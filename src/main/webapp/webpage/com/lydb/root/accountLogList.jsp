<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="accountLogList" fitColumns="false" title="(带*进行模糊搜索)" actionUrl="accountLogController.do?datagrid" sortName="createTime"  sortOrder="desc"  idField="id" pageSize="30"  fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"></t:dgCol>
   <t:dgCol title="商家id"  field="dbWebBusinessid" hidden="true" ></t:dgCol>
   <t:dgCol title="账单生成时间"  field="createTime" query="true"  formatter="yyyy-MM-dd hh:mm:ss"   width="140"></t:dgCol>
   <t:dgCol title="账单内容描述"  field="content"  width="300"></t:dgCol>
   <t:dgCol title="订单编号"  field="orderNum" query="true"   width="240"></t:dgCol>
   <t:dgCol title="商家手机号"  field="businessMobile"  query="true"   width="120"></t:dgCol>
   <t:dgCol title="金额 (元)"  field="rmb"   width="120"></t:dgCol>
   <t:dgCol title="类型(收/支)"  field="accountType" replace="支出_0,收入_1" query="true" width="120"></t:dgCol>
     
  </t:datagrid>
  </div>
 </div>

 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 

 </script>