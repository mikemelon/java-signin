<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>Java系列课程签到网站   >> 查看教室签到情况</TITLE>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/style.css" type="text/css"/>
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"/>
<style type="text/css">
	a.reverseLink:link{
		text-decoration: none;
		color: #dcedc8;
	}
	a.reverseLink:hover{
		color:#9ccc65;
	}
</style>
</HEAD>

<BODY bgColor=#ffffff>
<form name="form1" action="reverseview.action" method="post">
<input type="hidden" name="rowIndex"/>
<input type="hidden" name="columnIndex"/>
<input type="hidden" name="rowNum" value="${rowNum}"/>
<input type="hidden" name="colNum" value="${colNum}"/>
<input type="hidden" name="inverseView" value="${inverseView}"/>
<input type="hidden" name="isPost" value="1"/>


<!-- 主页面内容 BEIN -->
        	<table style="margin:0 auto;width:100%;">
        		<s:if test="%{inverseView==false}">
        		<tr>
        			<td colspan=17 style="text-align:center;position:relative;">
						<a href="<s:url action="signin"/>">
							<img border=0 src="./image/teacher.jpg" style="opacity:.8;"/>
						</a>
						<span style="position:absolute;right:10px;top:5px;text-align:right;line-height:100px;">
							<a  class="reverseLink" href="javascript:void(0)" onclick="form1.submit()">翻转方向</a>
						</span>
					</td>
				</tr>
				</s:if>
        		<s:iterator value="@cn.lynu.lyq.signin.actions.SignInAction@getRowIndexList(rowNum,inverseView)" var="row" status="row_st">
	        		<tr>
	        			<s:iterator value="@cn.lynu.lyq.signin.actions.SignInAction@getColumnIndexList(colNum,true,inverseView)" var="col_left" status="col_left_st">
	        				<td>
        						<s:if test="online[#row-1][#col_left-1]">
        							<!-- img src="./image/online.jpg" width="50" height="50"-->
        							<i class="icon-user icon-4x" style="color: #64de54;"></i>
        							<br>
        							<label><s:property value="name[#row-1][#col_left-1]"/></label>
        						</s:if>
        						<s:else>
        							<!-- img src="./image/offline.jpg" width="50" height="50"-->
        							<i class="icon-user icon-4x" style="color: #dedede;"></i>
        							<br>
        							<label>&nbsp;</label>
        						</s:else>
	        				</td>
	        			</s:iterator>
	        			<td width="130">&nbsp;&nbsp;</td>	
	        			<s:iterator value="@cn.lynu.lyq.signin.actions.SignInAction@getColumnIndexList(colNum,false,inverseView)" var="col_right" status="col_right_st">
	        				<td>
        						<s:if test="online[#row-1][#col_right-1]">
        							<!-- img src="./image/online.jpg" width="50" height="50"-->
        							<i class="icon-user icon-4x" style="color: #64de54;"></i>
        							<br>
        							<label><s:property value="name[#row-1][#col_right-1]"/></label>
        						</s:if>
        						<s:else>
        							<!-- img src="./image/offline.jpg" width="50" height="50"-->
        							<i class="icon-user icon-4x" style="color: #dedede;"></i>
        							<br>
        							<label>&nbsp;</label>
        						</s:else>
	        				</td>
	        			</s:iterator>	        			
	        			
	        		</tr>
        		</s:iterator>
        		<s:if test="%{inverseView}">
        		<tr>
        			<td colspan=17 style="text-align:center;position:relative;">
						<a href="<s:url action="signin"/>">
							<img border=0 src="./image/teacher.jpg" style="opacity:.8;"/>
						</a>
						<span style="position:absolute;right:10px;top:5px;text-align:right;line-height:100px;">
							<a class="reverseLink" href="javascript:void(0)" onclick="form1.submit()">翻转方向</a>
						</span>
					</td>
				</tr>
				</s:if>        		
        	</table>
<!-- 主页面内容 END -->
</form>
</BODY>
</HTML>

