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
							<strong>编辑</strong>
						</li>
					</ol>
				</div>
			</div>

			<div class="wrapper wrapper-content  animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" id="form" action="/biddingData/update" method="post">
							<input type="hidden" name="id" value="${biddingData.id}">

							<div class="ibox ">
								<div class="ibox-title">
									<h5>项目信息</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>项目名称（工程名称）</label>
										<input type="text" class="form-control" placeholder="项目名称（工程名称）"
											   name="projectName" value="${(biddingData.projectName)!''}">
									</div>
									<div class="form-group"><label>项目规模（兆瓦）</label>
										<input type="text" class="form-control" placeholder="1000000"
											   name="projectScale" value="${(biddingData.projectScale)!''}">
									</div>
									<div class="form-group"><label>项目描述</label>
										<input type="text" class="form-control" placeholder="项目描述"
											   name="projectDescription" value="${(biddingData.projectDescription)!''}">
									</div>
									<div class="form-group"><label>项目详细地址</label>
										<input type="text" class="form-control" placeholder="北京市海淀区"
											   name="projectAddress" value="${(biddingData.projectAddress)!''}">
									</div>
									<div class="form-group"><label>采购人</label>
										<input type="text" class="form-control" placeholder="采购人" name="projcetOwner"
											   value="${(biddingData.projcetOwner)!''}">
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" class="form-control" placeholder="母公司" name="parentCompany"
											   value="${(biddingData.parentCompany)!''}">
									</div>
									<div class="form-group"><label>单晶硅规格</label>
										<input type="text" class="form-control" placeholder="单晶硅规格"
											   name="monocrystallineSpecification"
											   value="${(biddingData.monocrystallineSpecification)!''}">
									</div>
									<div class="form-group"><label>单晶硅的采购容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder="单晶硅的采购容量，单位：MW（兆瓦）"
											   name="monocrystallineCapacity"
											   value="${(biddingData.monocrystallineCapacity)!''}">
									</div>
									<div class="form-group"><label>多晶硅规格</label>
										<input type="text" class="form-control" placeholder="多晶硅规格"
											   name="polysiliconSpecification"
											   value="${(biddingData.polysiliconSpecification)!''}">
									</div>
									<div class="form-group"><label>多晶硅的采购容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder="多晶硅的采购容量，单位：MW（兆瓦）"
											   name="polysiliconCapacity"
											   value="${(biddingData.polysiliconCapacity)!''}">
									</div>
									<div class="form-group"><label>招标预算</label>
										<input type="text" class="form-control" placeholder="100" name="biddingBudget"
											   value="${(biddingData.biddingBudget)!''}">
									</div>
									<div class="form-group"><label>中标总金额</label>
										<input type="text" class="form-control" placeholder="100"
											   name="winTotalAmount" value="${(biddingData.winTotalAmount)!''}">
									</div>

								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-content">
									<div>
										<a href="" target="_blank">查看原文</a>
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
