<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="dbWebBusinessList" fitColumns="false" title="(带*进行模糊搜索)" actionUrl="BusManagement.do?datagrid&status=1" sortName="theNumber"  sortOrder="desc" pageSize="30" idField="id" fit="true" queryMode="group" >
   <t:dgCol title="主键"  field="id"  hidden="true"  ></t:dgCol>
   <t:dgCol title="商家编号"  field="theNumber"  align="center"  query="true" width="120" ></t:dgCol>

   <t:dgCol title="商家帐号"  field="mobile"  align="center"  query="true" width="120" ></t:dgCol>
   
   <t:dgCol title="商家昵称"  field="name"  align="center"   query="true" width="120" ></t:dgCol>
   
   <t:dgCol title="商家店名"  field="shopname"   align="center"  query="true" width="180"></t:dgCol>
   
 	<t:dgCol title="商家QQ"  field="businessQq"   align="center"  query="true" width="120"></t:dgCol>

   <t:dgCol title="操作" field="opt" align="center" width="280"></t:dgCol>

   <t:dgFunOpt  funname="LookGoodsList(id)" title="查看普通商品列表"></t:dgFunOpt>
   <t:dgFunOpt  funname="LookZeroGoodsList(id)" title="查看零元商品列表"></t:dgFunOpt>
    <t:dgFunOpt  funname="delbusiness(id)"  title="删除"  />


  </t:datagrid>
  </div>
 </div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
 <script type="text/javascript">



 	function LookGoodsList(id){
 		 $.dialog.setting.zIndex = 1580;
 		openwindow("普通商品列表","BusManagement.do?BusinessGoodsList&id="+id,"",width ,heigth );
 	}
	function LookZeroGoodsList(id){
		$.dialog.setting.zIndex = 1580;
		openwindow("零元商品列表","BusManagement.do?BusinessZeroGoodsList&id="+id,"",width ,heigth );
	}
	function delbusiness(id){
		 $.dialog.confirm("注意该操作会删除跟商家有关的所有信息（包括上架的商品，已经开奖的商品，正在开奖的商品等一切信息），请谨慎操作！", function(){
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					data : {},
					url : "BusManagement.do?deleteBusAllinfo&busid="+id,// 请求的action路径
					error : function() {
					},
					success : function(data) {

						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
						}
                        reloadTable();
					}
				});

			}, function(){
			}).zindex();
	}
 </script>

