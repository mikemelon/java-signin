<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
  <head>
  	<link rel="stylesheet" href="css/style.css" type="text/css"/>
  	<link rel="stylesheet" href="css/table.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <title>考勤管理页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
	<form name="form1" action="manage.action" method="post">
		<input type="hidden" name="mycommand" value=""><!-- 1表示“根据考勤日期设置在线学生列表” -->
													<!-- 2表示“设置当前班级” -->
													<!-- 3表示“请假” -->
		<input type="hidden" name="isPost" value="1"/>
		<input type="hidden" name="stu_id">
	    未到学生：
	  	<table class="mytable">
	  		<tr><th>No.</th><th>姓名</th><th>学号</th><th>&nbsp;</th></tr>
		    <s:iterator value="offlineStudentList" status="st">
	  		<tr	<s:if test="#st.even">bgcolor="#EEEEEE"</s:if>>
	  				<td><s:property value="#st.index+1"/></td>	    
			    	<td><s:property value="name"/></td>
			    	<td><s:property value="regNo"/></td>
			    	<td onmouseover="document.getElementById('absentrequest<s:property value="#st.index"/>').style.visibility='visible';" 
			    	onmouseout="document.getElementById('absentrequest<s:property value="#st.index"/>').style.visibility='hidden';">
			    		<span id="absentrequest<s:property value="#st.index"/>" style="visibility:hidden;">
			    			<a href=# onclick="form1.stu_id.value='<s:property value="id"/>';
			    						   form1.mycommand.value='3';
			    						   form1.submit();">
			    			请假</a>
			    		</span>
			    	</td>
			</tr>
		    </s:iterator>
		</table>
		
		<br/><hr/>
		根据考勤日期设置在线学生列表：
		<input class="Wdate" type="text" name="signDate" onClick="WdatePicker()" value="<s:property value='signDate'/>"> 
		<input type="button" value="设置" onclick="form1.mycommand.value='1';form1.submit();"/>
		
		<br/><hr/>
		设置当前班级和上课地点(同时会根据上课地点自动推断座位行列数）:
		<s:select name="currentClassName" list="classNameList"></s:select> &nbsp;
		<s:select name="currentLocation" list="#{'1号楼1号机房':'1号楼1号机房',
												 '1号楼7号机房':'1号楼7号机房',
												 '1号楼8号机房':'1号楼8号机房',
												 '1号楼9号机房':'1号楼9号机房',
												 'G5教学楼110机房':'G5教学楼110机房'}"></s:select>
		<input type="button" value="设置" onclick="form1.mycommand.value='2';form1.submit();"/>
	</form>
  </body>
</html>
