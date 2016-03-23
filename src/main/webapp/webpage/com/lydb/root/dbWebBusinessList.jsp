<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="dbWebBusinessList" fitColumns="false" title="(带*进行模糊搜索)" actionUrl="BusManagement.do?datagrid" sortName="createTime"  sortOrder="desc" pageSize="30" idField="id" fit="true" queryMode="group" >
   <t:dgCol title="主键"  field="id"  hidden="true"  ></t:dgCol>
   <t:dgCol title="商家编号"  field="theNumber"  align="center"  query="true"  ></t:dgCol>
   <t:dgCol title="申请时间"  field="createTime" align="center"   formatter="yyyy-MM-dd"  ></t:dgCol>
   <t:dgCol title="商家帐号"  field="mobile"  align="center"  query="true"  ></t:dgCol>
   <t:dgCol title="商家昵称"  field="name"  align="center" ></t:dgCol>
   <t:dgCol title="商家店名"  field="shopname"   align="center" width="150"></t:dgCol>
   <t:dgCol title="申请信息"  field="information" align="center"  width="400"  ></t:dgCol>
   
   <t:dgCol title="状态"  field="status" align="center"  replace="未审核_0,已通过_1,已拒绝_2"  query="true" width="120" ></t:dgCol>
   <t:dgCol title="操作" field="opt" align="center" width="120"></t:dgCol>

   <t:dgFunOpt  funname="LookDetail(status,id)" title="查看详情"></t:dgFunOpt>
   <t:dgDelOpt title="删除" url="BusManagement.do?doDel&id={id}" />

 
  </t:datagrid>
  </div>
 </div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 //查看详情
  function LookDetail(status,id){

	  	if (status=="0"){
				  	$.dialog({
						content: "url:BusManagement.do?BusinessDetail&id="+id,
						title:'商家',
						cache:false,
					
						lock : true,
						button: [
					        {
					            name: "审核通过",
					            callback: function(){
					            	$.post("<%=basePath%>/BusManagement.do?ChangeBusinessStatus",
											 {"id": id,"status":"1"},
									 		function(result) {
									 			if(result.success){
									 				tip("审核操作成功!");
													reloadTable();
														}
												else{
													tip("审核操作失败!");
												}
											},
											"json");
								
					            },
					            focus: true
					        },
					        {
					            name: "审核拒绝",
					            callback: function(){
					            	$.post("<%=basePath%>/BusManagement.do?ChangeBusinessStatus",
											 {"id": id,"status":"2"},
									 		function(result) {
									 			if(result.success){
									 				tip("审核操作成功!");
													reloadTable();
														}
												else{
													tip("审核操作失败!");
												}
											},
											"json");
					            }
					        }
				    ],
						width:  width * 0.6,
						height: heigth * 0.73
					}).zindex();
		}else{
			$.dialog({
				content: "url:BusManagement.do?BusinessDetail&id="+id,
				title:'商家',
				cache:false,
				lock : true,

				width:  width * 0.6,
				height: heigth * 0.73
			}).zindex();
		}
}

	
		
			
		
 </script>

