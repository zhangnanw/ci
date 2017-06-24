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
					<h2>价格追踪</h2>
					<ol class="breadcrumb">
						<li>
							<a href="/welcome">首页</a>
						</li>
						<li>
							<a href="/priceTrackingInfo/list">价格追踪</a>
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
						<form role="form" id="form" action="/priceTrackingInfo/update" method="post">
							<input type="hidden" name="id" value="${priceTrackingInfo.id}">

							<div class="ibox ">
								<div class="ibox-content">
									<div>
										<a href="/snapshotInfo/detail/${(priceTrackingInfo.snapshotId)!''}"
										   target="_blank">查看原文</a>
									</div>
								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-title">
									<h5>价格追踪</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>项目名称（工程名称）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectName" value="${(priceTrackingInfo.projectName)!''}">
									</div>
									<div class="form-group"><label>项目规模（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectScale" value="${(priceTrackingInfo.projectScale)!''}">
									</div>
									<div class="form-group"><label>项目总投资，单位：万元</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectTotalInvestment"
											   value="${(priceTrackingInfo.projectTotalInvestment)!''}">
									</div>
									<div class="form-group"><label>项目描述</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectDescription"
											   value="${(priceTrackingInfo.projectDescription)!''}">
									</div>
									<div class="form-group"><label>项目详细地址</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectAddress" value="${(priceTrackingInfo.projectAddress)!''}">
									</div>
									<div class="form-group"><label>项目地址（省）</label>
										<select class="select2_projectProvince form-control" name="projectProvince">
											<option></option>
											<option value="1"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==1>selected="selected"</#if>>
												北京市
											</option>
											<option value="2"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==2>selected="selected"</#if>>
												天津市
											</option>
											<option value="3"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==3>selected="selected"</#if>>
												上海市
											</option>
											<option value="4"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==4>selected="selected"</#if>>
												重庆市
											</option>
											<option value="5"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==5>selected="selected"</#if>>
												安徽省
											</option>
											<option value="6"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==6>selected="selected"</#if>>
												福建省
											</option>
											<option value="7"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==7>selected="selected"</#if>>
												甘肃省
											</option>
											<option value="8"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==8>selected="selected"</#if>>
												广东省
											</option>
											<option value="9"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==9>selected="selected"</#if>>
												贵州省
											</option>
											<option value="10"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==10>selected="selected"</#if>>
												海南省
											</option>
											<option value="11"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==11>selected="selected"</#if>>
												河北省
											</option>
											<option value="12"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==12>selected="selected"</#if>>
												河南省
											</option>
											<option value="13"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==13>selected="selected"</#if>>
												湖北省
											</option>
											<option value="14"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==14>selected="selected"</#if>>
												湖南省
											</option>
											<option value="15"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==15>selected="selected"</#if>>
												吉林省
											</option>
											<option value="16"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==16>selected="selected"</#if>>
												江苏省
											</option>
											<option value="17"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==17>selected="selected"</#if>>
												江西省
											</option>
											<option value="18"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==18>selected="selected"</#if>>
												辽宁省
											</option>
											<option value="19"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==19>selected="selected"</#if>>
												青海省
											</option>
											<option value="20"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==20>selected="selected"</#if>>
												山东省
											</option>
											<option value="21"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==21>selected="selected"</#if>>
												山西省
											</option>
											<option value="22"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==22>selected="selected"</#if>>
												陕西省
											</option>
											<option value="23"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==23>selected="selected"</#if>>
												四川省
											</option>
											<option value="24"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==24>selected="selected"</#if>>
												云南省
											</option>
											<option value="25"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==25>selected="selected"</#if>>
												浙江省
											</option>
											<option value="26"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==26>selected="selected"</#if>>
												台湾省
											</option>
											<option value="27"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==27>selected="selected"</#if>>
												黑龙江省
											</option>
											<option value="28"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==28>selected="selected"</#if>>
												西藏自治区
											</option>
											<option value="29"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==29>selected="selected"</#if>>
												内蒙古自治区
											</option>
											<option value="30"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==30>selected="selected"</#if>>
												宁夏回族自治区
											</option>
											<option value="31"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==31>selected="selected"</#if>>
												广西壮族自治区
											</option>
											<option value="32"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==32>selected="selected"</#if>>
												新疆维吾尔自治区
											</option>
											<option value="33"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==33>selected="selected"</#if>>
												香港特别行政区
											</option>
											<option value="34"
                                                    <#if ((priceTrackingInfo.projectProvince)!-1)==34>selected="selected"</#if>>
												澳门特别行政区
											</option>
										</select>
									</div>
									<div class="form-group"><label>采购人</label>
										<textarea class="form-control" placeholder="" name="projcetOwner"
												  rows="10">${(priceTrackingInfo.projcetOwner)!''}</textarea>
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" class="form-control" placeholder="" name="parentCompany"
											   value="${(priceTrackingInfo.parentCompany)!''}">
									</div>

									<div class="form-group"><label>产品类型</label>
										<select class="select2_deploymentType form-control" name="productType">
											<option></option>
											<option value="1"
                                                    <#if ((priceTrackingInfo.productType)!-1)==1>selected="selected"</#if>>
												单晶硅
											</option>
											<option value="2"
                                                    <#if ((priceTrackingInfo.productType)!-1)==2>selected="selected"</#if>>
												多晶硅
											</option>
											<option value="3"
                                                    <#if ((priceTrackingInfo.productType)!-1)==3>selected="selected"</#if>>
												单晶硅、多晶硅
											</option>
											<option value="4"
                                                    <#if ((priceTrackingInfo.productType)!-1)==4>selected="selected"</#if>>
												未知
											</option>
										</select>
									</div>
									<div class="form-group"><label>单晶硅规格</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(priceTrackingInfo.monocrystallineSpecification[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(priceTrackingInfo.monocrystallineSpecification[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(priceTrackingInfo.monocrystallineSpecification[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(priceTrackingInfo.monocrystallineSpecification[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(priceTrackingInfo.monocrystallineSpecification[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineSpecification"
																		 value="${(priceTrackingInfo.monocrystallineSpecification[5])!''}">
											</div>
										</div>
									</div>
									<div class="form-group"><label>单晶硅的采购容量，单位：MW（兆瓦）</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(priceTrackingInfo.monocrystallineCapacity[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(priceTrackingInfo.monocrystallineCapacity[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(priceTrackingInfo.monocrystallineCapacity[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(priceTrackingInfo.monocrystallineCapacity[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(priceTrackingInfo.monocrystallineCapacity[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="monocrystallineCapacity"
																		 value="${(priceTrackingInfo.monocrystallineCapacity[5])!''}">
											</div>
										</div>

									</div>
									<div class="form-group"><label>单晶硅的采购总容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="monocrystallineTotalCapacity"
											   value="${(priceTrackingInfo.monocrystallineTotalCapacity)!''}">
									</div>
									<div class="form-group"><label>多晶硅规格</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(priceTrackingInfo.polysiliconSpecification[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(priceTrackingInfo.polysiliconSpecification[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(priceTrackingInfo.polysiliconSpecification[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(priceTrackingInfo.polysiliconSpecification[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(priceTrackingInfo.polysiliconSpecification[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconSpecification"
																		 value="${(priceTrackingInfo.polysiliconSpecification[5])!''}">
											</div>
										</div>


									</div>
									<div class="form-group"><label>多晶硅的采购容量，单位：MW（兆瓦）</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(priceTrackingInfo.polysiliconCapacity[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(priceTrackingInfo.polysiliconCapacity[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(priceTrackingInfo.polysiliconCapacity[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(priceTrackingInfo.polysiliconCapacity[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(priceTrackingInfo.polysiliconCapacity[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="polysiliconCapacity"
																		 value="${(priceTrackingInfo.polysiliconCapacity[5])!''}">
											</div>
										</div>

									</div>
									<div class="form-group"><label>多晶硅的采购总容量，单位：MW（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="polysiliconTotalCapacity"
											   value="${(priceTrackingInfo.polysiliconTotalCapacity)!''}">
									</div>
									<div class="form-group"><label>投标企业</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="bidCompanys"
																		 value="${(priceTrackingInfo.bidCompanys[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="bidCompanys"
																		 value="${(priceTrackingInfo.bidCompanys[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="bidCompanys"
																		 value="${(priceTrackingInfo.bidCompanys[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="bidCompanys"
																		 value="${(priceTrackingInfo.bidCompanys[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="bidCompanys"
																		 value="${(priceTrackingInfo.bidCompanys[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="bidCompanys"
																		 value="${(priceTrackingInfo.bidCompanys[5])!''}">
											</div>
										</div>
									</div>
									<div class="form-group"><label>投标报价，单位：万元</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="quotations"
																		 value="${(priceTrackingInfo.quotations[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="quotations"
																		 value="${(priceTrackingInfo.quotations[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="quotations"
																		 value="${(priceTrackingInfo.quotations[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="quotations"
																		 value="${(priceTrackingInfo.quotations[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="quotations"
																		 value="${(priceTrackingInfo.quotations[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="quotations"
																		 value="${(priceTrackingInfo.quotations[5])!''}">
											</div>
										</div>
									</div>
									<div class="form-group"><label>中标金额，单位：万元</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winAmounts"
																		 value="${(priceTrackingInfo.winAmounts[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winAmounts"
																		 value="${(priceTrackingInfo.winAmounts[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winAmounts"
																		 value="${(priceTrackingInfo.winAmounts[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winAmounts"
																		 value="${(priceTrackingInfo.winAmounts[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winAmounts"
																		 value="${(priceTrackingInfo.winAmounts[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winAmounts"
																		 value="${(priceTrackingInfo.winAmounts[5])!''}">
											</div>
										</div>
									</div>
									<div class="form-group"><label>中标单价，单位：元每瓦</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winPrices"
																		 value="${(priceTrackingInfo.winPrices[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winPrices"
																		 value="${(priceTrackingInfo.winPrices[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winPrices"
																		 value="${(priceTrackingInfo.winPrices[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winPrices"
																		 value="${(priceTrackingInfo.winPrices[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winPrices"
																		 value="${(priceTrackingInfo.winPrices[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winPrices"
																		 value="${(priceTrackingInfo.winPrices[5])!''}">
											</div>
										</div>
									</div>
									<div class="form-group"><label>中标容量，单位：MW（兆瓦）</label>
										<div class="row">
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winCapacitys"
																		 value="${(priceTrackingInfo.winCapacitys[0])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winCapacitys"
																		 value="${(priceTrackingInfo.winCapacitys[1])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winCapacitys"
																		 value="${(priceTrackingInfo.winCapacitys[2])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winCapacitys"
																		 value="${(priceTrackingInfo.winCapacitys[3])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winCapacitys"
																		 value="${(priceTrackingInfo.winCapacitys[4])!''}">
											</div>
											<div class="col-md-2"><input type="text" placeholder="" class="form-control"
																		 name="winCapacitys"
																		 value="${(priceTrackingInfo.winCapacitys[5])!''}">
											</div>
										</div>
									</div>

									<div class="form-group"><label>备注</label>
										<input type="text" class="form-control" placeholder="" name="remarks"
											   value="${(priceTrackingInfo.remarks)!''}">
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
			window.location.href = "/priceTrackingInfo/list";
		});

		FormValidation.init();

	});
</script>
</body>
</html>
