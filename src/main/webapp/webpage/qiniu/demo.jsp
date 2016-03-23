<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath()+"/webpage/com/motu/qiniu";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>demo</title>
<link rel="stylesheet" href="<%=path %>/static/qiniu/css/main.css"/>  
<link rel="stylesheet" href="<%=path %>/static/qiniu/css/highlight.css"/> 
<script type="text/javascript" src="<%=path %>/static/qiniu/js/plupload.full.min.js"></script>  
<script type="text/javascript" src="<%=path %>/static/qiniu/js/zh_CN.js"></script>  
<script type="text/javascript" src="<%=path %>/static/qiniu/js/ui.js"></script>  
<script type="text/javascript" src="<%=path %>/static/qiniu/js/qiniu.js"></script>  
 
<script type="text/javascript" src="<%=path %>/static/qiniu/js/jquery-1.9.1.min.js"></script>  
</head>
<body>
<span style="white-space:pre">        </span><%--start新增七牛上传--%>  
                    <div class="">  
                        <div class="text-left col-md-12 wrapper">  
                            <input type="hidden" id="domain" value="http://tripbon.qiniudn.com/">  
                            <input type="hidden" id="uptoken_url" value="">  
                        </div>  
                        <div class="body">  
                            <div>  
                                <div id="container">  
                                    <a class="btn btn-default btn-md " id="pickfiles">  
                                        <i class="glyphicon glyphicon-plus"></i>  
                                        <sapn>上传版本文件</sapn>  
                                    </a>  
  
                                    <div id="html5_197igj75aami1c13vhi9rf1n9v3_container"  
                                         class="moxie-shim moxie-shim-html5"  
                                         style="position: absolute; top: 0px; left: 0px; width: 167px; height: 45px; overflow: hidden; z-index: 0;">  
                                        <input id="html5_197igj75aami1c13vhi9rf1n9v3" type="file"  
                                               style="font-size: 999px; opacity: 0; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;"  
                                               multiple="" accept="">  
                                    </div>  
                                </div>  
                            </div>  
  
                            <div style="display:none" id="success" class="col-md-12">  
                                <div class="alert-success">  
                                    队列全部文件处理完毕  
                                </div>  
                            </div>  
                            <div class="col-md-12 ">  
                                <table class="table table-striped table-hover text-left"  
                                       style="margin-top:40px;display:none">  
                                    <thead>  
                                    <tr>  
                                        <th class="col-md-4">文件名</th>  
                                        <th class="col-md-2">大小</th>  
                                        <th class="col-md-6">详情</th>  
                                    </tr>  
                                    </thead>  
                                    <tbody id="fsUploadProgress">  
                                    </tbody>  
                                </table>  
                            </div>  
                        </div>  
                    </div>  
<span style="white-space:pre">        </span><%--end--%>  
</body>
<script>
window.onload=function(){
	$.get(url="http://localhost:8080/jeecg/systemController.do?uptoken",
		date={},
	function(obj){
				alert(obj.uptoken);
				$("#uptoken_url").attr('value',obj.uptoken);
	},
		"json"
			);
}
</script>
<script type="text/javascript" src="<%=path %>/static/qiniu/js/main.js"></script> 
</html>