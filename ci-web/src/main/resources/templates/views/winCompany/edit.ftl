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
							<a href="/winCompany/list?biddingDataId=${biddingData.id}">中标单位信息</a>
						</li>
						<li class="active">
							<strong>编辑</strong>
						</li>
					</ol>
				</div>
			</div>

			<div class="wrapper wrapper-content  animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="form" action="/winCompany/update" method="post">
							<input type="hidden" name="biddingDataId" value="${biddingData.id}">
							<input type="hidden" name="id" value="${winCompany.id}">

							<div class="ibox ">
								<div class="ibox-title">
									<h5>${biddingData.projectName} - 中标单位信息</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>中标单位</label>
										<input type="text" class="form-control" placeholder="中标单位"
											   name="companyName" value="${winCompany.companyName}">
									</div>
									<div class="form-group"><label>中标金额</label>
										<input type="text" class="form-control" placeholder="1000000"
											   name="winAmount" value="${winCompany.winAmount}">
									</div>
									<div class="form-group"><label>中标单价</label>
										<input type="text" class="form-control" placeholder="1000000"
											   name="winPrice" value="${winCompany.winPrice}">
									</div>
									<div class="form-group"><label>中标容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder="100"
											   name="winCapacity" value="${winCompany.winCapacity}">
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
						companyName: {
							required: true,
							minlength: 3
						}
					},

					submitHandler: function (form) {
						$(form).ajaxSubmit({
							"success": function (data) {
								if (data.status === 200) {
									$.alertable.alert("更新数据成功").always(function () {
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
