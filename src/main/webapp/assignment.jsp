<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>提交作业</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form name="form1" enctype="multipart/form-data"  action="assignment.action" method="post">
    	
    	<label for="comments" style="vertical-align: middle"><b>作业内容说明：</b></label>
    	<textarea name="comments" style="vertical-align: middle" cols="80"  rows="10">${comments }</textarea>
    	<br/>
    	<label for="attachFile" style="vertical-align: middle"><b>作业附件：</b></label>
    	<input type="file" name="attachFile"/><br/><br/>
    	
    	<input value="提交作业" type="submit"/>
    </form>
  </body>
</html>
