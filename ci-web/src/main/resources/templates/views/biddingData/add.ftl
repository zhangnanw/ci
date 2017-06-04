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
									<div class="form-group"><label>项目地址（省）</label>
										<select class="select2_projectProvince form-control" name="projectProvince">
											<option></option>
											<option value="北京市">北京市</option>
											<option value="天津市">天津市</option>
											<option value="上海市">上海市</option>
											<option value="重庆市">重庆市</option>
											<option value="安徽省">安徽省</option>
											<option value="福建省">福建省</option>
											<option value="甘肃省">甘肃省</option>
											<option value="广东省">广东省</option>
											<option value="贵州省">贵州省</option>
											<option value="海南省">海南省</option>
											<option value="河北省">河北省</option>
											<option value="河南省">河南省</option>
											<option value="湖北省">湖北省</option>
											<option value="湖南省">湖南省</option>
											<option value="吉林省">吉林省</option>
											<option value="江苏省">江苏省</option>
											<option value="江西省">江西省</option>
											<option value="辽宁省">辽宁省</option>
											<option value="青海省">青海省</option>
											<option value="山东省">山东省</option>
											<option value="山西省">山西省</option>
											<option value="陕西省">陕西省</option>
											<option value="四川省">四川省</option>
											<option value="云南省">云南省</option>
											<option value="浙江省">浙江省</option>
											<option value="台湾省">台湾省</option>
											<option value="黑龙江省">黑龙江省</option>
											<option value="西藏自治区">西藏自治区</option>
											<option value="内蒙古自治区">内蒙古自治区</option>
											<option value="宁夏回族自治区">宁夏回族自治区</option>
											<option value="广西壮族自治区">广西壮族自治区</option>
											<option value="新疆维吾尔自治区">新疆维吾尔自治区</option>
											<option value="香港特别行政区">香港特别行政区</option>
											<option value="澳门特别行政区">澳门特别行政区</option>
										</select>
									</div>
									<div class="form-group"><label>采购人</label>
										<textarea class="form-control" placeholder="采购人" name="projcetOwner"
												  rows="10"></textarea>
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" class="form-control" placeholder="母公司" name="parentCompany">
									</div>
									<div class="form-group"><label>采购方式</label>
										<select class="select2_purchasingMethod form-control" name="purchasingMethod">
											<option></option>
											<option value="1">邀标公告</option>
											<option value="2">询价公告</option>
											<option value="3">招标公告</option>
											<option value="4">中标公告</option>
											<option value="5">成交公告</option>
											<option value="6">更正公告</option>
											<option value="7">其他公告</option>
											<option value="8">单一来源</option>
											<option value="9">资格预审</option>
											<option value="10">废标流标</option>
											<option value="11">竞争性谈判</option>
											<option value="12">竞争性磋商</option>
										</select>
									</div>
									<div class="form-group"><label>产品部署类型</label>
										<select class="select2_deploymentType form-control" name="deploymentType">
											<option></option>
											<option value="1">分布式</option>
											<option value="2">集中式</option>
											<option value="3">渔光</option>
											<option value="4">农光</option>
										</select>
									</div>
									<div class="form-group"><label>单晶硅规格</label>
										<input type="text" class="form-control" placeholder="单晶硅规格"
											   name="monocrystallineSpecification">
									</div>
									<div class="form-group"><label>单晶硅的采购容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder="单晶硅的采购容量，单位：MW（兆瓦）"
											   name="monocrystallineCapacity">
									</div>
									<div class="form-group"><label>多晶硅规格</label>
										<input type="text" class="form-control" placeholder="多晶硅规格"
											   name="polysiliconSpecification">
									</div>
									<div class="form-group"><label>多晶硅的采购容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder="多晶硅的采购容量，单位：MW（兆瓦）"
											   name="polysiliconCapacity">
									</div>
									<div class="form-group"><label>招标预算</label>
										<input type="text" class="form-control" placeholder="100" name="biddingBudget">
									</div>
									<div class="form-group"><label>中标总金额</label>
										<input type="text" class="form-control" placeholder="100" name="winTotalAmount">
									</div>
									<div class="form-group"><label>代理机构</label>
										<input type="text" class="form-control" placeholder="代理机构" name="agency">
									</div>
									<div class="form-group"><label>发布时间</label>
										<div class="input-group date form_datetime">
											<input name="publishTime" class="form-control" size="16" type="text"
												   value="" readonly>
											<span class="input-group-addon"><span
													class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
										</div>
										<input type="hidden" id="dtp_input1" value=""/><br/>
									</div>
									<div class="form-group"><label>备注</label>
										<input type="text" class="form-control" placeholder="备注" name="remarks">
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
		$(".select2_projectProvince").select2({
			placeholder: "--请选择--",
			allowClear: true
		});

		$(".select2_purchasingMethod").select2({
			placeholder: "--请选择--",
			allowClear: true
		});

		$(".select2_deploymentType").select2({
			placeholder: "--请选择--",
			allowClear: true
		});

		$('.form_datetime').datetimepicker({
			language: "zh-CN",
			format: "yyyy-mm-dd hh:ii:ss",
			weekStart: 1,
			todayBtn: 1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: 0,
			showMeridian: 1
		});

		FormValidation.init();

	});
</script>
</body>
</html>
