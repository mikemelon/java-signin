<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<style type="text/css">
input.big_input{
	font-family:"Microsoft YaHei","sans-serif";
	width:600px;
	height:95px;
	font-size:40px;
}
input.big_btn{
	width:200px;
	margin-left:20px;
}
</style>
<title>请求权限页面</title>
</head>
<body>
	<h1>你不在教师机，没有权限</h1>
	<p>请输入管理权限密码</p>
	<form action="" method="post">
		<input type="text" name="password" id="password" 
		onkeyup="document.getElementById('password').value=this.value.replace(/./g,'★');" 
		placeholder="输啥都不对，只留下星号，信不？" class="big_input">
		<input type="submit" value="确定" class="big_input big_btn">
	</form>
</body>
</html>