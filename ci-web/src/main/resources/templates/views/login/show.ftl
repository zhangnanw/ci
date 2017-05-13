<#assign base = request.contextPath />
<!DOCTYPE html>
<html>

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>用户登录</title>

	<link href="${base}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${base}/font-awesome/css/font-awesome.css" rel="stylesheet">

	<link href="${base}/css/animate.css" rel="stylesheet">
	<link href="${base}/css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen animated fadeInDown">
	<div>
		<div>
			<h3 class="logo-name">YanSou</h3>
		</div>

		<h3>竞争情报系统</h3>
		<p>Login in. To see it in action.</p>
		<form class="m-t" role="form" action="${base}/login/show" method="post">
			<input type="hidden" name="${(_csrf.parameterName)!''}" value="${(_csrf.token)!''}"/>
			<div class="form-group">
				<input type="text" name="username" class="form-control" placeholder="用户名" required="">
			</div>
			<div class="form-group">
				<input type="password" name="password" class="form-control" placeholder="密码" required="">
			</div>

			<button type="submit" class="btn btn-primary block full-width m-b">登录</button>
		</form>

		<p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small> </p>
	</div>
</div>

<!-- Mainly scripts -->
<script src="${base}/js/jquery-2.1.1.js"></script>
<script src="${base}/js/bootstrap.min.js"></script>

</body>

</html>
