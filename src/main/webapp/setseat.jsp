<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>Java EE课程网站</TITLE>
<script language="JavaScript">
	function setSeat( rowIndex, colIndex ){
        form1.rowIndex.value=rowIndex;
        form1.columnIndex.value=colIndex;
        form1.action='setseat.action';	
        form1.submit();
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="./css/style.css" type="text/css"/>

</HEAD>
<BODY bgColor=#ffffff>

<form name="form1" action="setseat.action" method="post">
<input type="hidden" name="rowIndex"/>
<input type="hidden" name="columnIndex"/>
<input type="hidden" name="isPost" value="1"/>


<!-- 主页面内容 BEIN -->
        	<table style="margin:0 auto;width:100%;">
        		<tr>
        			<td colspan="${colNum*2+1}" style="text-align:center;position:relative;">
        				<a href="<s:url action="view"/>" style="margin:0 auto;text-align:center;">
							<img border=0 src="./image/teacher.jpg" style="vertical-align:middle;"/>
						</a>
						<span style="position:absolute;right:10px;top:5px;text-align:right;line-height:50px;">
							<a href="deleteallseats.action">清空所有座位</a><br/>
							<a href=# onclick="form1.action='setmultipleseats.action';form1.submit();">设置</a>
							为<input type="text" name="rowNum" value="${rowNum }" size="2">行 *
							<input type="text" name="colNum" value="${colNum }" size="2">列
						</span>
					</td>
				</tr>
        		<s:iterator value="@cn.lynu.lyq.signin.actions.SeatSelectAction@getRowIndexList(rowNum)" var="row" status="row_st">
	        		<tr>
	        			<s:iterator value="@cn.lynu.lyq.signin.actions.SeatSelectAction@getColumnIndexList(colNum,true)" 
	        			            var="col_left" status="col_left_st">
	        				<td>
	        					<a href="javascript:void(0)" onclick="setSeat(${row},${col_left})">	
	        						<s:if test="availableArray[#row-1][#col_left-1]">
	        							<img border=0 src="./image/offline.jpg" width="50" height="50"><br>
	        						</s:if>
	        						<s:else>
	        							<img border=0 src="./image/ban.png" width="50" height="50">
	        						</s:else>
        						</a>
	        				</td>
	        			</s:iterator>
	        			<td width="130">&nbsp;&nbsp;</td>	
	        			<s:iterator value="@cn.lynu.lyq.signin.actions.SeatSelectAction@getColumnIndexList(colNum,false)"  
	        				var="col_right" status="col_right_st">
	        				<td>
	        					<a href="javascript:void(0)" onclick="setSeat(${row},${col_right})">
	        						<s:if test="availableArray[#row-1][#col_right-1]">
	        							<img border=0 src="./image/offline.jpg" width="50" height="50"><br>
	        						</s:if>
	        						<s:else>
	        							<img border=0 src="./image/ban.png" width="50" height="50">
	        						</s:else>
        						</a>
	        				</td>
	        			</s:iterator>	        			
	        			
	        		</tr>
        		</s:iterator>
        	</table>
<!-- 主页面内容 END -->

</form>
</BODY>
</HTML>

