<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>已提交作业列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/table.css">

  </head>
  
  <body>
    <form name="form1" enctype="multipart/form-data"  action="assignment.action" method="post">
    	本次课程已提交作业列表
    	<table width="80%" class="mytable">
    		<thead>
    			<tr>
    				<th>编号</th><th>提交人</th><th>说明</th><th>文件路径</th><th>提交时间</th>
    			</tr>
    		</thead>
	    	<s:iterator value="#ASSIGNMENT_LIST" var="item" status="st">
	    		<tr <s:if test="#st.even">bgcolor="#CCCCFF"</s:if>>
	    			<td style="width:30px;table-layout:fixed;"><s:property value="#st.getIndex()+1"/></td>
	    			<td style="width:50px;table-layout:fixed;"><s:property value="#item.student.name"/></td>
	    			<td style="width:90px;table-layout:fixed;"><s:property value="#item.comments"/></td>
	    			<td style="width:150px;table-layout:fixed;">
	    				<a href="<s:property value="#item.filepath"/>">
	    				<s:property value="#item.filepath"/></a>
	    			</td>
	    			<td style="width:50px;table-layout:fixed;"><s:date name="#item.submitdate" format="yyyy-MM-dd HH:mm:ss"/></td>
	    		</tr>
	    	</s:iterator>
    	</table>
    </form>
  </body>
</html>
