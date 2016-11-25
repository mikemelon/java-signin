<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'taskselect.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  	<style>
  		ul{
  			font-size: 1.2em;
  			color: blue;
  			list-style:square;
  			width: 800px;
  			margin: 10px auto;
  			text-align: left;
  		}
  		ul li{
  			margin-top: 10px;
  		}
  		li>a{
  			color: red;
  		}
  	</style>
  </head>
  
  <body>
  	<span>选择任务出错！可能原因是：</span>
	<ul>
		<li>
			还未在签到页面登录 
			(当前登录为：学号：<s:property value="#session['CURRENT_USER_REGID']"/>，
			姓名：<s:property value="#session['CURRENT_USER_NAME']"/>)--&gt;
			<a href="signin.action">返回进行签到</a>
		</li>
		<li>
			当前任务已经被其他人选择，请返回任务选择页面重新确认选择。--&gt;
			<a href="selecttask.action">返回任务选择</a>
		</li>
		<li>
			当前学号已经分配过了一个任务了（一个学号只能分配一个任务）
			(当前登录为：学号：<s:property value="#session['CURRENT_USER_REGID']"/>，
			姓名：<s:property value="#session['CURRENT_USER_NAME']"/>)--&gt;
			<a href="selecttask.action">返回任务查看</a>
		</li>
		<li>
			其他未知原因？请联系指导老师。
		</li>
	</ul>
  </body>
</html>
