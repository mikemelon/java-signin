<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<decorator:head/>
<title><decorator:title default="第一个装饰器页面"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style type="text/css">
	body {
		font-family: "Microsoft YaHei", "Verdana", "Arial", "Helvetica","sans-serif";
		font-size: 12pt;
		margin: 0px;
		padding: 0px;
		border: 0px;
	}
	p {
		font-family: "Microsoft YaHei", "Verdana", "Arial", "Helvetica","sans-serif";
		font-size: 12pt;
	}
	a:link {
		color: #0000FF;
		text-decoration: none;
	}
	a:visited {
		text-decoration: none;
	}
	a:hover {
		color: #FF0033;
		text-decoration: none;
	}
	td {
		font-family: "Microsoft YaHei", "Verdana", "Arial", "Helvetica","sans-serif";
		font-size: 10pt;
	}
	.CompanyName {
		font-family: "Microsoft YaHei", "Verdana", "Arial", "Helvetica","sans-serif";
		font-size: 20pt;
		color:#8d6e63;
	}
</style>
</head>
<body bgColor=#ffffff>

<DIV align=center>

<!-- 
<TABLE align=center border=0 cellPadding=0 cellSpacing=0 width="80%">
  <TR>
    <TD width="2%">&nbsp;</TD>
    <TD width="2%">&nbsp;</TD>
    <TD width="3%">&nbsp;</TD>
    <TD bgColor=#006500 width="2%">&nbsp;</TD>
    <TD width="5%">&nbsp;</TD>
    <TD width="2%">&nbsp;</TD>
    <TD width="2%">&nbsp;</TD>
    <TD bgColor=#ce3000 width="2%">&nbsp;</TD>
    <TD width="11%">&nbsp;</TD>
    <TD width="2%">&nbsp;</TD>
    <TD width="5%">&nbsp;</TD>
    <TD bgColor=#ff9a00 width="2%">&nbsp;</TD>
    <TD width="7%">&nbsp;</TD>
    <TD width="2%">&nbsp;</TD>
    <TD width="16%">&nbsp;</TD>
    <TD bgColor=#00a200 width="2%">&nbsp;</TD>
    <TD width="12%">&nbsp;</TD>
    <TD width="2%">&nbsp;</TD>
    <TD width="16%">&nbsp;</TD>
    <TD bgColor=#006500 width="3%">&nbsp;</TD>
  </TR>
</TABLE>
<TABLE border=0 cellPadding=0 cellSpacing=0 width="80%">
  <TR>
    <TD bgColor=#006600 height=13 rowSpan=2 width="19%">&nbsp;</TD>
    <TD bgColor=#ce3000 rowSpan=2 width="8%">&nbsp;</TD>
    <TD bgColor=#ff9900 height=13 rowSpan=2 width="15%">&nbsp;</TD>
    <TD bgColor=#949231 height=13 rowSpan=2 width="14%">&nbsp;</TD>
    <TD bgColor=#00a200 height=13 rowSpan=2 width="44%">&nbsp;</TD>
  </TR>
  <TR></TR>
</TABLE> 
-->

<TABLE style="border-bottom:1px solid #e6ee9c;width:80%;padding:0;border-collapse:collapse;border-spacing:0;">
  <TR>
    <TD height=50><DIV align=center class="CompanyName"><B>计算机实验室Web签到系统</B></DIV></TD>
    <TD vAlign=bottom><DIV align=right><B><FONT size=2 color="#f45">请先在“课堂点名”中点击头像签到</FONT></B></DIV></TD>
  </TR>
</TABLE>
</DIV>
<TABLE style="width:80%;border:none;padding:0;margin:0 auto;border-collapse:collapse;border-spacing:0;">
  <TR>
    <TD colSpan=2 vAlign=top style="padding-top:20px; padding-bottom:20px;">
<!-- 主页面内容 BEIN -->
		<decorator:body/>
<!-- 主页面内容 END -->
	</TD>
    <TD bgColor=#e6ee9c vAlign=top width=1 style="padding:0;"></TD>
    <TD bgColor=#ffffff vAlign=top width=135>
      <DIV align=right>
      <P align=center><B><FONT color=#ff0000 size=3><BR>Welcome!</FONT></B></P>
      <P align=center><B><FONT color=#ff0000><IMG height=100 src="./image/logo.jpg" width=100></FONT></B> </P>
      <TABLE style="width:100%;border:none;margin:0 auto;border-collapse:collapse;border-spacing:0;">
        <TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav1"><B><A href='signin.action'><FONT color=#ff9c00>课堂点名</FONT></A></B></DIV></TD>
		</TR>
        <TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav1"><B><A href='stats.action'><FONT color=#ff9c00>考勤统计</FONT></A></B></DIV></TD>
		</TR>
        <TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav1"><B><A href='setseat.action'><FONT color=#ffad00>座位管理</FONT></A></B></DIV></TD>
		</TR>
        <TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav1"><B><A href='manage.action'><FONT color=#ffad00>签到管理</FONT></A></B></DIV></TD>
		</TR>				
        <TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav1"><B><A href="assignment.action"><FONT color=#ff9c00>提交作业</FONT></A></B></DIV></TD>
		</TR>
        <!--TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav0 nav1"><B><A href=""><FONT color=#ff9c00>作业统计</FONT></A></B></DIV></TD>
		</TR>
        <TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav0 nav1"><B><A href=""><FONT color=#ff9c00>平时成绩</FONT></A></B></DIV></TD>
		</TR-->
        <TR>
          <TD height=25 width="20%"></TD>
          <TD><DIV align=center class="nav1"><B><A href="selecttask.action"><FONT color=#ff5500>领取任务</FONT></A></B></DIV></TD>
		</TR>

	  </TABLE>
	  </DIV>
	</TD>
  </TR>
  <TR vAlign=top>
    <TD colSpan=4 >
      <DIV align=right style="border-top:1px solid #e6ee9c;">

	  </DIV>
      <DIV align=right>
		  <DIV align=center style="height:40px;line-height:40px;">
			<FONT color=#78909c face="Consolas,Arial, Helvetica, sans-serif" size=2>&copy;Copyright 2012-2017 Luoyang Normal University. Author:lyq. All rights reserved.</FONT><FONT color=#3169a5> </FONT><BR>
		  </DIV>
	  </DIV>
	</TD>
  </TR>
</TABLE>
</body>
</html>

