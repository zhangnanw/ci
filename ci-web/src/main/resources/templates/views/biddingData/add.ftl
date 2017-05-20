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
							<a>招中标数据</a>
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
									<h5>项目信息</h5>
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
										<input type="text" placeholder="项目详细地址" class="form-control"
											   name="projectAddress">
									</div>
									<div class="form-group"><label>采购人</label>
										<input type="text" placeholder="采购人" class="form-control" name="projcetOwner">
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" placeholder="母公司" class="form-control" name="parentCompany">
									</div>


								</div>
							</div>
							<div class="ibox ">
								<div class="ibox-title">
									<h5>招标信息</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>招标预算</label>
										<input type="text" placeholder="招标预算" class="form-control" name="biddingBudget">
									</div>

								</div>
							</div>
							<div class="ibox ">
								<div class="ibox-title">
									<h5>中标信息</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>中标总金额</label>
										<input type="text" placeholder="中标总金额" class="form-control"
											   name="winTotalAmount">
									</div>
									<div class="form-group"><label>中标单位信息</label>
										<input type="text" placeholder="中标单位信息" class="form-control" name="winCompany">
									</div>

								</div>
							</div>
							<div class="ibox ">
								<div class="ibox-content">
									<div>
										<button class="btn btn-sm btn-primary m-t-n-xs"
												type="submit"><strong>&nbsp;&nbsp;保存&nbsp;&nbsp;</strong></button>
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
									updateForPath("${base}" + data.result.url);
								} else {
									swal({
										title: "错误信息",
										text: "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
									});
								}
							},
							"error": function (jqXHR, textStatus, errorThrown) {
								swal({
									title: "错误信息",
									text: "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
								});
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
