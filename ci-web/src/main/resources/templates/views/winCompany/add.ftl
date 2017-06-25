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
							<strong>新增</strong>
						</li>
					</ol>
				</div>
			</div>

			<div class="wrapper wrapper-content  animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="form" action="/winCompany/save" method="post">
							<input type="hidden" name="biddingDataId" value="${biddingData.id}">

							<div class="ibox ">
								<div class="ibox-title">
									<h5>${(biddingData.projectName)!'招中标数据'} - 中标单位信息</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>中标单位</label>
										<input type="text" class="form-control" placeholder=""
											   name="companyName">
									</div>
									<div class="form-group"><label>中标金额，单位：万元</label>
										<input type="text" class="form-control" placeholder=""
											   name="winAmount">
									</div>
									<div class="form-group"><label>中标单价，单位：元/瓦</label>
										<input type="text" class="form-control" placeholder=""
											   name="winPrice">
									</div>
									<div class="form-group"><label>中标容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="winCapacity">
									</div>
									<div class="form-group"><label>中标时间</label>
										<div class="input-group date">
											<input name="winTime" class="form-control" type="text"
												   value="" readonly>
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
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
		$('.input-group.date').datepicker({
			language: "zh-CN",
			format: "yyyy-mm-dd",
			todayBtn: "linked",
			todayHighlight: true,
			endDate: "today",
			keyboardNavigation: false,
			forceParse: false,
			calendarWeeks: true,
			autoclose: true
		});

		FormValidation.init();
	});
</script>
</body>
</html>
