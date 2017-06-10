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
									<div class="form-group"><label>公告类型</label>
										<select class="select2_dataType form-control" name="dataType">
											<option></option>
											<option value="1">招标公告</option>
											<option value="2">中标公告</option>
											<option value="3">更正公告</option>
											<option value="4">废标公告</option>
											<option value="5">流标公告</option>
										</select>
									</div>
									<div class="form-group"><label>项目名称（工程名称）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectName">
									</div>
									<div class="form-group"><label>项目规模（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectScale">
									</div>
									<div class="form-group"><label>项目总投资，单位：万元</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectTotalInvestment">
									</div>
									<div class="form-group"><label>项目描述</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectDescription">
									</div>
									<div class="form-group"><label>项目详细地址</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectAddress">
									</div>
									<div class="form-group"><label>项目地址（省）</label>
										<select class="select2_projectProvince form-control" name="projectProvince">
											<option></option>
											<option value="1">北京市</option>
											<option value="2">天津市</option>
											<option value="3">上海市</option>
											<option value="4">重庆市</option>
											<option value="5">安徽省</option>
											<option value="6">福建省</option>
											<option value="7">甘肃省</option>
											<option value="8">广东省</option>
											<option value="9">贵州省</option>
											<option value="10">海南省</option>
											<option value="11">河北省</option>
											<option value="12">河南省</option>
											<option value="13">湖北省</option>
											<option value="14">湖南省</option>
											<option value="15">吉林省</option>
											<option value="16">江苏省</option>
											<option value="17">江西省</option>
											<option value="18">辽宁省</option>
											<option value="19">青海省</option>
											<option value="20">山东省</option>
											<option value="21">山西省</option>
											<option value="22">陕西省</option>
											<option value="23">四川省</option>
											<option value="24">云南省</option>
											<option value="25">浙江省</option>
											<option value="26">台湾省</option>
											<option value="27">黑龙江省</option>
											<option value="28">西藏自治区</option>
											<option value="29">内蒙古自治区</option>
											<option value="30">宁夏回族自治区</option>
											<option value="31">广西壮族自治区</option>
											<option value="32">新疆维吾尔自治区</option>
											<option value="33">香港特别行政区</option>
											<option value="34">澳门特别行政区</option>
										</select>
									</div>
									<div class="form-group"><label>采购人</label>
										<textarea class="form-control" placeholder="" name="projcetOwner"></textarea>
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" class="form-control" placeholder="" name="parentCompany">
									</div>
									<div class="form-group"><label>采购方式</label>
										<select class="select2_purchasingMethod form-control" name="purchasingMethod">
											<option></option>
											<option value="1">公开招标</option>
											<option value="2">竞争性谈判</option>
											<option value="3">单一来源</option>
											<option value="4">市场询价</option>
											<option value="5">邀请招标</option>
											<option value="6">其他</option>
										</select>
									</div>
									<div class="form-group"><label>产品部署类型</label>
										<select class="select2_deploymentType form-control" name="deploymentType">
											<option></option>
											<option value="1">分布式</option>
											<option value="2">地面电站</option>
											<option value="3">未知</option>
										</select>
									</div>
									<div class="form-group"><label>产品类型</label>
										<select class="select2_deploymentType form-control" name="productType">
											<option></option>
											<option value="1">单晶硅</option>
											<option value="2">多晶硅</option>
											<option value="3">单晶硅、多晶硅</option>
											<option value="4">未知</option>
										</select>
									</div>
									<div class="form-group"><label>单晶硅规格</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineSpecification"></div>
										</div>
									</div>
									<div class="form-group"><label>单晶硅的采购容量，单位：MW（兆瓦）</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="monocrystallineCapacity"></div>
										</div>
									</div>
									<div class="form-group"><label>单晶硅的采购总容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="polysiliconTotalCapacity">
									</div>
									<div class="form-group"><label>多晶硅规格</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconSpecification"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconSpecification"></div>
										</div>
									</div>
									<div class="form-group"><label>多晶硅的采购容量，单位：MW（兆瓦）</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconCapacity"></div>
											<div class="col-md-2"><input type="text" placeholder=""
																		 class="form-control"
																		 name="polysiliconCapacity"></div>
										</div>
									</div>
									<div class="form-group"><label>多晶硅的采购总容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="polysiliconTotalCapacity">
									</div>
									<div class="form-group"><label>招标预算，单位：万元</label>
										<input type="text" class="form-control" placeholder="" name="biddingBudget">
									</div>
									<div class="form-group"><label>中标总金额，单位：万元</label>
										<input type="text" class="form-control" placeholder="" name="winTotalAmount">
									</div>
									<div class="form-group"><label>代理机构</label>
										<input type="text" class="form-control" placeholder="" name="agency">
									</div>
									<div class="form-group"><label>发布时间</label>
										<div class="input-group date">
											<input name="publishTime" class="form-control" type="text"
												   value="" readonly>
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</div>
									<div class="form-group"><label>备注</label>
										<input type="text" class="form-control" placeholder="" name="remarks">
									</div>

								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-content">
									<div>
										<button class="btn btn-primary" type="submit"><i class="fa fa-check"></i>&nbsp;
											保存
										</button>
										<button class="btn btn-success" type="button" id="btn_back"><i class="fa
										fa-level-up"></i>&nbsp;
											返回
										</button>
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
		$(".select2_dataType").select2({
			placeholder: "--请选择--",
			allowClear: true
		});

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

		$('.input-group.date').datepicker({
			language: "zh-CN",
			format: "yyyy-mm-dd",
			todayBtn: "linked",
			keyboardNavigation: false,
			forceParse: false,
			calendarWeeks: true,
			autoclose: true
		});

		$('#btn_back').click(function () {
			window.location.href = "/biddingData/list";
		});

		FormValidation.init();

	});
</script>
</body>
</html>
