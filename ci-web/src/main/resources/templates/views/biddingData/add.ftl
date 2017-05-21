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
<#include "/views/left.ftl"/>

	<div id="page-wrapper" class="gray-bg">
	<#include "/views/header.ftl"/>

		<!-- BEGIN PAGE CONTAINER-->
		<div id="page-container">
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>招中标数据</h2>
					<ol class="breadcrumb">
						<li>
							<a href="/welcome/index">首页</a>
						</li>
						<li>
							<a href="/biddingData/list">招中标数据</a>
						</li>
						<li class="active">
							<strong>新增</strong>
						</li>
					</ol>
				</div>
			</div>

			<div class="wrapper wrapper-content  animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="form" action="/biddingData/save" method="post">
							<div class="ibox ">
								<div class="ibox-title">
									<h5>招中标数据</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>项目名称（工程名称）</label>
										<input type="text" class="form-control" placeholder="项目名称（工程名称）"
											   name="projectName">
									</div>
									<div class="form-group"><label>项目规模（兆瓦）</label>
										<input type="text" class="form-control" placeholder="1000000"
											   name="projectScale">
									</div>
									<div class="form-group"><label>项目描述</label>
										<input type="text" class="form-control" placeholder="项目描述"
											   name="projectDescription">
									</div>
									<div class="form-group"><label>项目详细地址</label>
										<input type="text" class="form-control" placeholder="北京市海淀区"
											   name="projectAddress">
									</div>
									<div class="form-group"><label>采购人</label>
										<input type="text" class="form-control" placeholder="采购人" name="projcetOwner">
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" class="form-control" placeholder="母公司" name="parentCompany">
									</div>
									<div class="form-group"><label>招标预算</label>
										<input type="text" class="form-control" placeholder="100" name="biddingBudget">
									</div>
									<div class="form-group"><label>中标总金额</label>
										<input type="text" class="form-control" placeholder="100" name="winTotalAmount">
									</div>

								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-content">
									<div>
										<button class="btn btn-w-m btn-primary"
												type="submit"><strong>保存</strong></button>
									</div>
								</div>
							</div>

						</form>
					</div>

				</div>

			</div>

		</div>
		<!-- END PAGE CONTAINER-->

	<#include "/views/footer.ftl"/>
	</div>

</div>

<#include "/views/plugins.ftl"/>
<script>
	var FormValidation = function () {

		return {
			// main function to initiate the module
			init: function () {

				var form1 = $('#form');

				form1.validate({
					rules: {
						projectName: {
							required: true,
							minlength: 3
						},
						projectDescription: {
							required: true,
							minlength: 3
						}
					},

					submitHandler: function (form) {
						$(form).ajaxSubmit({
							"success": function (data) {
								if (data.status === 200) {
									$.alertable.alert("新增数据成功").always(function () {
										updateForPath(data.result.url);
									});
								} else {
									$.alertable.alert(data.errors);
								}
							},
							"error": function (jqXHR, textStatus, errorThrown) {
								$.alertable.alert(jqXHR.responseText);
							},
							"dataType": "json"
						});
					}

				});

			}

		};

	}();

	$(document).ready(function () {

		FormValidation.init();

	});
</script>
</body>
</html>
