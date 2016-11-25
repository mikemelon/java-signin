<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>领取任务</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/table.css">
  	<script>
  		function selectTask(id){
  			if(confirm("你确定要选择该课程吗？(点击确定后不可更改!)")){
  				document.form1.taskId.value=id;
  				document.form1.submit();
  			}
  		}
  	</script>
  </head>
  
  <body>
  	<form name="form1" action="confirmtask.action" method="post">
  		<input type="hidden" name="taskId"/>
  	    <table class="mytable">
    	<caption>从列表中选择一个任务</caption>
    	<tr>
    		<th width="10">编号</th>
    		<th width="50">题目</th>
    		<th width="80">内容</th>
    		<th width="100"><!-- 教程网址 -->题目下载地址</th>
    		<th width="20">选择该任务的学生</th>
    	</tr>
    	<s:iterator value="taskList" status="st" var="taskitem">
    		<tr <s:if test="#st.even">bgcolor="#EEEEEE"</s:if>>
    			<td><s:property value="#st.index+1"/></td>
    			<td><s:property value="title"/></td>
    			<td><s:property value="content"/></td>
    			<td>
    				<a href="<s:property value='webaddress'/>">
    				<s:property value="webaddress"/>
    				</a>
    			</td>
    			<td style="text-align:center;">
    				<s:if test="student==null">
    					<input type="button" value="选择" 
    					       onclick="selectTask(<s:property value='id'/>)"  
    					       <s:if test="taskAllocatedFlag">disabled</s:if>/>
    				</s:if>
    				<s:else>
    					<s:property value="student.name"/>
    				</s:else>
    			</td>
    		</tr>
    	</s:iterator>
    </table>
    </form>
  </body>
</html>
