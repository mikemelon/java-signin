<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<s:head/>
  	<link rel="stylesheet" href="css/style.css" type="text/css"/>
  	<link rel="stylesheet" href="css/table.css" type="text/css"/>
  	<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"/>

    <base href="<%=basePath%>">
    
    <title>考勤统计页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
	<form name="form1" action="stats.action" method="post">
		<input type="hidden" name="command" value="">
		<input type="hidden" name="isPost" value="1"/>
		
		设置当前班级:
		<s:select name="currentClassName" list="classNameList"></s:select>
		<input type="button" value="设置" onclick="form1.submit();"/>	
		<br/><hr/>
		签到统计列表：
		<br/>												
	  	<table class="mytable">
	  		<tr height="40">
	  			<th>No.</th>
	  			<th width=50>姓名</th>
	  			<th>学号</th>
	  			<s:iterator value="dateList">
	  				<th><s:property/></th>
	  			</s:iterator>
	  		</tr>
	  		
		    <s:iterator value="studentList" status="st" var="stu1">
	  		<tr	<s:if test="#st.even">bgcolor="#EEEEEE"</s:if>>
	  				<td><s:property value="#st.index+1"/></td>	    
			    	<td><s:property value="studentList[#st.index].name"/></td>
			    	<td><s:property value="studentList[#st.index].regNo"/></td>
					<s:iterator value="dateList" status="st2">
	  					<td style="text-align:center;">
	  						<s:if test='studentList[#st.index][dateList[#st2.index]] == "REQUEST_FOR_ABSENT" '>
        						<!-- img src="./image/absent.gif" width="35" height="35"/-->
        						<i class="icon-edit icon-2x" style="color: #dbdb42;"></i>
        					</s:if>
	  						<s:elseif test="studentList[#st.index][dateList[#st2.index]]">
        						<!-- img src="./image/ok.jpg" width="35" height="35"/-->
        						<i class="icon-ok icon-2x" style="color: #64de54;"></i>
        					</s:elseif>
        					<s:else>
        						<!-- img src="./image/error.jpg" width="35" height="35"/-->
        						<span style="font-weight:bolder;font-size:23px;color:#de6050;margin:0;padding:0;">&times;</span>
        					</s:else>
	  					</td>
	  				</s:iterator>
			</tr>
		    </s:iterator>
		</table>
	</form>
  </body>
</html>
