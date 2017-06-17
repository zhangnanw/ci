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
					<h2>竞争对手</h2>
					<ol class="breadcrumb">
						<li>
							<a href="/welcome">首页</a>
						</li>
						<li>
							<a href="/competitor/list">竞争对手</a>
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
						<form role="form" id="form" action="/competitor/update" method="post">
							<input type="hidden" name="id" value="${competitor.id}">

							<div class="ibox ">
								<div class="ibox-content">
									<div>
										<a href="/snapshotInfo/detail/${(competitor.snapshotId)!''}" target="_blank">查看原文</a>
									</div>
								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-title">
									<h5>竞争对手</h5>
								</div>
								<div class="ibox-content">
									<div class="form-group"><label>公司名称</label>
										<input type="text" class="form-control" placeholder=""
											   name="companyName" value="${(competitor.companyName)!''}">
									</div>

									<div class="form-group"><label>项目名称（工程名称）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectName" value="${(competitor.projectName)!''}">
									</div>
									<div class="form-group"><label>项目规模（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectScale" value="${(competitor.projectScale)!''}">
									</div>
									<div class="form-group"><label>项目总投资，单位：万元</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectTotalInvestment"
											   value="${(competitor.projectTotalInvestment)!''}">
									</div>
									<div class="form-group"><label>项目描述</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectDescription"
											   value="${(competitor.projectDescription)!''}">
									</div>
									<div class="form-group"><label>项目详细地址</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectAddress" value="${(competitor.projectAddress)!''}">
									</div>
									<div class="form-group"><label>项目地址（省）</label>
										<select class="select2_projectProvince form-control" name="projectProvince">
											<option></option>
											<option value="1"
                                                    <#if ((competitor.projectProvince)!-1)==1>selected="selected"</#if>>
												北京市
											</option>
											<option value="2"
                                                    <#if ((competitor.projectProvince)!-1)==2>selected="selected"</#if>>
												天津市
											</option>
											<option value="3"
                                                    <#if ((competitor.projectProvince)!-1)==3>selected="selected"</#if>>
												上海市
											</option>
											<option value="4"
                                                    <#if ((competitor.projectProvince)!-1)==4>selected="selected"</#if>>
												重庆市
											</option>
											<option value="5"
                                                    <#if ((competitor.projectProvince)!-1)==5>selected="selected"</#if>>
												安徽省
											</option>
											<option value="6"
                                                    <#if ((competitor.projectProvince)!-1)==6>selected="selected"</#if>>
												福建省
											</option>
											<option value="7"
                                                    <#if ((competitor.projectProvince)!-1)==7>selected="selected"</#if>>
												甘肃省
											</option>
											<option value="8"
                                                    <#if ((competitor.projectProvince)!-1)==8>selected="selected"</#if>>
												广东省
											</option>
											<option value="9"
                                                    <#if ((competitor.projectProvince)!-1)==9>selected="selected"</#if>>
												贵州省
											</option>
											<option value="10"
                                                    <#if ((competitor.projectProvince)!-1)==10>selected="selected"</#if>>
												海南省
											</option>
											<option value="11"
                                                    <#if ((competitor.projectProvince)!-1)==11>selected="selected"</#if>>
												河北省
											</option>
											<option value="12"
                                                    <#if ((competitor.projectProvince)!-1)==12>selected="selected"</#if>>
												河南省
											</option>
											<option value="13"
                                                    <#if ((competitor.projectProvince)!-1)==13>selected="selected"</#if>>
												湖北省
											</option>
											<option value="14"
                                                    <#if ((competitor.projectProvince)!-1)==14>selected="selected"</#if>>
												湖南省
											</option>
											<option value="15"
                                                    <#if ((competitor.projectProvince)!-1)==15>selected="selected"</#if>>
												吉林省
											</option>
											<option value="16"
                                                    <#if ((competitor.projectProvince)!-1)==16>selected="selected"</#if>>
												江苏省
											</option>
											<option value="17"
                                                    <#if ((competitor.projectProvince)!-1)==17>selected="selected"</#if>>
												江西省
											</option>
											<option value="18"
                                                    <#if ((competitor.projectProvince)!-1)==18>selected="selected"</#if>>
												辽宁省
											</option>
											<option value="19"
                                                    <#if ((competitor.projectProvince)!-1)==19>selected="selected"</#if>>
												青海省
											</option>
											<option value="20"
                                                    <#if ((competitor.projectProvince)!-1)==20>selected="selected"</#if>>
												山东省
											</option>
											<option value="21"
                                                    <#if ((competitor.projectProvince)!-1)==21>selected="selected"</#if>>
												山西省
											</option>
											<option value="22"
                                                    <#if ((competitor.projectProvince)!-1)==22>selected="selected"</#if>>
												陕西省
											</option>
											<option value="23"
                                                    <#if ((competitor.projectProvince)!-1)==23>selected="selected"</#if>>
												四川省
											</option>
											<option value="24"
                                                    <#if ((competitor.projectProvince)!-1)==24>selected="selected"</#if>>
												云南省
											</option>
											<option value="25"
                                                    <#if ((competitor.projectProvince)!-1)==25>selected="selected"</#if>>
												浙江省
											</option>
											<option value="26"
                                                    <#if ((competitor.projectProvince)!-1)==26>selected="selected"</#if>>
												台湾省
											</option>
											<option value="27"
                                                    <#if ((competitor.projectProvince)!-1)==27>selected="selected"</#if>>
												黑龙江省
											</option>
											<option value="28"
                                                    <#if ((competitor.projectProvince)!-1)==28>selected="selected"</#if>>
												西藏自治区
											</option>
											<option value="29"
                                                    <#if ((competitor.projectProvince)!-1)==29>selected="selected"</#if>>
												内蒙古自治区
											</option>
											<option value="30"
                                                    <#if ((competitor.projectProvince)!-1)==30>selected="selected"</#if>>
												宁夏回族自治区
											</option>
											<option value="31"
                                                    <#if ((competitor.projectProvince)!-1)==31>selected="selected"</#if>>
												广西壮族自治区
											</option>
											<option value="32"
                                                    <#if ((competitor.projectProvince)!-1)==32>selected="selected"</#if>>
												新疆维吾尔自治区
											</option>
											<option value="33"
                                                    <#if ((competitor.projectProvince)!-1)==33>selected="selected"</#if>>
												香港特别行政区
											</option>
											<option value="34"
                                                    <#if ((competitor.projectProvince)!-1)==34>selected="selected"</#if>>
												澳门特别行政区
											</option>
										</select>
									</div>
									<div class="form-group"><label>采购人</label>
										<textarea class="form-control" placeholder="" name="projcetOwner"
												  rows="10">${(competitor.projcetOwner)!''}</textarea>
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" class="form-control" placeholder="" name="parentCompany"
											   value="${(competitor.parentCompany)!''}">
									</div>

									<div class="form-group"><label>产品类型</label>
										<select class="select2_deploymentType form-control" name="productType">
											<option></option>
											<option value="1"
                                                    <#if ((competitor.productType)!-1)==1>selected="selected"</#if>>单晶硅
											</option>
											<option value="2"
                                                    <#if ((competitor.productType)!-1)==2>selected="selected"</#if>>多晶硅
											</option>
											<option value="3"
                                                    <#if ((competitor.productType)!-1)==3>selected="selected"</#if>>
												单晶硅、多晶硅
											</option>
											<option value="4"
                                                    <#if ((competitor.productType)!-1)==4>selected="selected"</#if>>未知
											</option>
										</select>
									</div>
									<div class="form-group"><label>单晶硅规格</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(competitor.monocrystallineSpecification[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(competitor.monocrystallineSpecification[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(competitor.monocrystallineSpecification[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(competitor.monocrystallineSpecification[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(competitor.monocrystallineSpecification[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(competitor.monocrystallineSpecification[5])!''}">
											</div>
										</div>
									</div>
									<div class="form-group"><label>单晶硅的采购容量，单位：MW（兆瓦）</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(competitor.monocrystallineCapacity[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(competitor.monocrystallineCapacity[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(competitor.monocrystallineCapacity[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(competitor.monocrystallineCapacity[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(competitor.monocrystallineCapacity[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(competitor.monocrystallineCapacity[5])!''}">
											</div>
										</div>

									</div>
									<div class="form-group"><label>单晶硅的采购总容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="monocrystallineTotalCapacity"
											   value="${(competitor.monocrystallineTotalCapacity)!''}">
									</div>
									<div class="form-group"><label>多晶硅规格</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(competitor.polysiliconSpecification[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(competitor.polysiliconSpecification[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(competitor.polysiliconSpecification[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(competitor.polysiliconSpecification[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(competitor.polysiliconSpecification[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(competitor.polysiliconSpecification[5])!''}">
											</div>
										</div>


									</div>
									<div class="form-group"><label>多晶硅的采购容量，单位：MW（兆瓦）</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(competitor.polysiliconCapacity[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(competitor.polysiliconCapacity[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(competitor.polysiliconCapacity[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(competitor.polysiliconCapacity[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(competitor.polysiliconCapacity[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(competitor.polysiliconCapacity[5])!''}">
											</div>
										</div>

									</div>
									<div class="form-group"><label>多晶硅的采购总容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="polysiliconTotalCapacity"
											   value="${(competitor.polysiliconTotalCapacity)!''}">
									</div>

									<div class="form-group"><label>备注</label>
										<input type="text" class="form-control" placeholder="" name="remarks"
											   value="${(competitor.remarks)!''}">
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
		$(".select2_projectProvince").select2({
			placeholder: "--请选择--",
			allowClear: true
		});

		$('#btn_back').click(function () {
			window.location.href = "/competitor/list";
		});

		FormValidation.init();

	});
</script>
</body>
</html>
