<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<title>Java系列课程签到网站   >> 签到页面</title>
<script type="text/javascript" src="js/lhgcore.lhgdialog.min.js"></script>
<script type="text/javascript">
	function openSigninDialog( rowIndex, colIndex ){
//		alert(document.body.offsetWidth+"/"+document.body.offsetHeight);
		document.getElementById('sbox_overlay').style.width=document.body.scrollWidth+"px";
		document.getElementById('sbox_overlay').style.height=document.body.scrollHeight+"px";
		document.getElementById('sbox_overlay').style.display='block';
		
		document.getElementById('sbox_window').style.display='block';
		
        form1.rowIndex.value=rowIndex;
        form1.columnIndex.value=colIndex;	
//$.dialog({title:'请登录：',
//		  content: 'url:signin.jsp'});
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/style.css" type="text/css"/>
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css"/>

</head>
<body bgColor=#ffffff>

<form name="form1" action="signin.action" method="post">
<input type="hidden" name="rowIndex"/>
<input type="hidden" name="columnIndex"/>
<input type="hidden" name="isPost" value="1"/>

<!-- 主页面内容 BEIN -->
       	<table style="margin:0 auto;width:100%;">
       		<tr>
       			<td colspan="${colNum*2+1}" style="text-align:center;position:relative;">
       				<a href="<s:url action="view"/>">
						<img border="0" src="./image/teacher.jpg" style="opacity:.8;"/>
					</a>
				</td>
			</tr>
       		<s:iterator value="@cn.lynu.lyq.signin.actions.SeatSelectAction@getRowIndexList(rowNum)" var="row" status="row_st">
        		<tr>
        			<s:iterator value="@cn.lynu.lyq.signin.actions.SeatSelectAction@getColumnIndexList(colNum,true)" var="col_left" status="col_st">
        				<td>
        					<s:if test="%{availableArray[#row-1][#col_left-1]==false}">
        						<img border=0 src="./image/ban.png" width="50" height="50" style="opacity:.6">
        						<br>
        						<label>&nbsp;</label>
        					</s:if>
       						<s:elseif test="online[#row-1][#col_left-1]">
       							<!-- img border=0 src="./image/online.jpg" width="50" height="50"-->
       							<i class="icon-user icon-4x" style="color: #64de54;"></i>
       							<br>
       							<label><s:property value="name[#row-1][#col_left-1]"/></label>
       						</s:elseif>
       						<s:else>
       							<a href=# onclick="openSigninDialog(${row},${col_left})">	        						
       							<!-- img border=0 src="./image/offline.jpg" width="50" height="50"-->
       							<i class="icon-user icon-4x" style="color: #dedede;"></i>
       							<br>
       							<label>&nbsp;</label>
       							</a>
       						</s:else>
        				</td>
        			</s:iterator>
        			<td width="130">&nbsp;&nbsp;</td>	
        			<s:iterator value="@cn.lynu.lyq.signin.actions.SeatSelectAction@getColumnIndexList(colNum,false)" var="col_right" status="col_st">
        				<td>
        					<s:if test="%{availableArray[#row-1][#col_right-1]==false}">
        						<img border=0 src="./image/ban.png" width="50" height="50"  style="opacity:.6">
        					</s:if>	        				
       						<s:elseif test="online[#row-1][#col_right-1]">
       							<!-- img border=0 src="./image/online.jpg" width="50" height="50"-->
       							<i class="icon-user icon-4x" style="color: #64de54;"></i>
       							<br>
       							<label><s:property value="name[#row-1][#col_right-1]"/></label>
       						</s:elseif>
       						<s:else>
       							<a href=# onclick="openSigninDialog(${row},${col_right})">	        						
       							<!-- img border=0 src="./image/offline.jpg" width="50" height="50"-->
       							<i class="icon-user icon-4x" style="color: #dedede;"></i>
       							<br>
       							<label>&nbsp;</label>
       							</a>
       						</s:else>
        				</td>
        			</s:iterator>	        			
        			
        		</tr>
       		</s:iterator>
       		
       		<tr>
       			<td colspan="${colNum*2+1}" style="text-align:center;">
       				<table style="width:10px;margin:0 auto;">
       					<tr>
	       				<s:iterator value="absentRequestStudentList" var="absentStudent" status="st">
	       					<td>
	       					<table style="width:60px;margin:0 auto;">
	       						<tr><td>
		       						<!--img border="0" src="./image/absent.gif" width="50" height="50"-->
		       						<i class="icon-edit icon-4x" style="color: #dbdb42;"></i>
		       					</td></tr>
		       					<tr><td>
		       						<s:property value="#absentStudent.name"/>
		       					</td></tr>
	       					</table>
	       					</td>
	       				</s:iterator>
       					</tr>
       				</table>
       			</td>
       		</tr>
       	</table>
<!-- 主页面内容 END -->

<!-- 输入姓名、学号对话框 BEIN -->
<div id="sbox_overlay"></div>
<div id="sbox_window">
	<table style="width:100%;margin:0 auto;text-align:center;">
		<tr><td height="20"></td></tr>
		<tr height="100"  valign="middle">
			<td valign="middle">
				<font size="20">姓名：</font><s:combobox name="name1" list="nameList" cssClass="big_input"/>
			</td>
		</tr>
		<tr><td height="60"></td></tr>
		<tr height="100" valign="middle">
			<td  valign="middle">
				<font size="20">学号：</font><s:combobox name="studentNo1" list="regNoList" cssClass="big_input"/>
			</td>
		</tr>
		<tr><td height="20"></td></tr>
		<tr height="100" align="center">
			<td>
				<input class="big_input" style="letter-spacing: 1em;padding: 30px auto;width: 300px;font-size:30px;" type="submit" value="确定"/>
			</td>
		</tr>
	</table>		
	<a id="sbox_btn_close" href="#" onclick="document.getElementById('sbox_overlay').style.display='none';
											 document.getElementById('sbox_window').style.display='none';">
	</a>
</div>
<!-- 输入姓名、学号对话框 END -->

<!-- 输入错误对话框 BEIN -->
<s:if test="errorMsg!=null && !errorMsg.equals('')">
<script type="text/javascript">
	$.dialog({title:'错误信息',
			  icon: 'error.gif',
			  content: '${errorMsg}',
			  width:'300px',
			  height:'150px',
			  min:false,
			  max:false,
			  background: '#888888',
			  opacity:0.5,
			  lock:true,
			  cancel:true});
</script>
</s:if>
<!-- 输入错误对话框 END -->

</form>
</body>
</html>

