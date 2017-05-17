<#assign base = request.contextPath />
<!DOCTYPE html>
<html>

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>企业竞争情报系统-欢迎您</title>

<#include "/views/styles.ftl"/>

</head>

<body>
<div id="wrapper">
	<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="sidebar-collapse">
			<!-- BEGIN SIDEBAR MENU -->
        <#include "/views/left.ftl"/>
			<!-- END SIDEBAR MENU -->

		</div>
	</nav>

	<div id="page-wrapper" class="gray-bg">
		<div class="row border-bottom">
			<nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
				<div class="navbar-header">
					&nbsp;
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li>
						<span class="m-r-sm text-muted welcome-message">欢迎您访问企业竞争情报系统</span>
					</li>

					<li>
						<a href="login.html">
							<i class="fa fa-sign-out"></i> 退出登录
						</a>
					</li>
					<li>
						&nbsp;
					</li>
				</ul>

			</nav>
		</div>

		<!-- BEGIN PAGE CONTAINER-->
		<div id="page-container">
        <#include "/views/right.ftl"/>
		</div>
		<!-- END PAGE CONTAINER-->

		<div class="footer">
			<div class="pull-right">
				&nbsp;
			</div>
			<div>
				<strong>Copyright</strong> Example Company &copy; 2014-2015
			</div>
		</div>
	</div>

</div>

<#include "/views/plugins.ftl"/>

</body>
</html>
