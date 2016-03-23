<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="dbversionList" fitColumns="false" title="一圆梦版本检测" actionUrl="dbversionController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  align="center" ></t:dgCol>
   <t:dgCol title="版本号"  field="version"    align="center"  width="150"></t:dgCol>
   <t:dgCol title="下载路径"  field="url"     align="center" width="400"></t:dgCol>
   <t:dgCol title="版本说明"  field="content"     align="center" width="400"></t:dgCol>
   <t:dgCol title="创建时间"  field="createTime"  formatter="yyyy-MM-dd hh:mm:ss"  align="center" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="dbversionController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="dbversionController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="dbversionController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="dbversionController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 </script>