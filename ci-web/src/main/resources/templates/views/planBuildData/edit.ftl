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
					<h2>拟在建数据</h2>
					<ol class="breadcrumb">
						<li>
							<a href="/welcome">首页</a>
						</li>
						<li>
							<a href="/planBuildData/list">拟在建数据</a>
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
						<form role="form" id="form" action="/planBuildData/update" method="post">
							<input type="hidden" name="id" value="${(planBuildData.id)!''}">
							<input type="hidden" name="projectIdentifie" value="${(planBuildData.projectIdentifie)!''}">

							<div class="ibox ">
								<div class="ibox-content">
									<div>
										<a href="/snapshotInfo/detail/${(planBuildData.snapshotId)!''}" target="_blank">查看原文</a>
									</div>
								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-title">
									<h5>拟在建数据</h5>
								</div>
								<div class="ibox-content">

									<div class="form-group"><label>项目名称（工程名称）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectName" value="${(planBuildData.projectName)!''}">
									</div>
									<div class="form-group"><label>项目规模（兆瓦）</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectScale" value="${(planBuildData.projectScale)!''}">
									</div>
									<div class="form-group"><label>项目总投资，单位：万元</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectTotalInvestment"
											   value="${(planBuildData.projectTotalInvestment)!''}">
									</div>
									<div class="form-group"><label>项目描述</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectDescription"
											   value="${(planBuildData.projectDescription)!''}">
									</div>
									<div class="form-group"><label>项目详细地址</label>
										<input type="text" class="form-control" placeholder=""
											   name="projectAddress" value="${(planBuildData.projectAddress)!''}">
									</div>
									<div class="form-group"><label>项目地址（省）</label>
										<select class="select2_projectProvince form-control" name="projectProvince">
											<option></option>
											<option value="1"
													<#if ((planBuildData.projectProvince)!-1)==1>selected="selected"</#if>>
												北京市
											</option>
											<option value="2"
													<#if ((planBuildData.projectProvince)!-1)==2>selected="selected"</#if>>
												天津市
											</option>
											<option value="3"
													<#if ((planBuildData.projectProvince)!-1)==3>selected="selected"</#if>>
												上海市
											</option>
											<option value="4"
													<#if ((planBuildData.projectProvince)!-1)==4>selected="selected"</#if>>
												重庆市
											</option>
											<option value="5"
													<#if ((planBuildData.projectProvince)!-1)==5>selected="selected"</#if>>
												安徽省
											</option>
											<option value="6"
													<#if ((planBuildData.projectProvince)!-1)==6>selected="selected"</#if>>
												福建省
											</option>
											<option value="7"
													<#if ((planBuildData.projectProvince)!-1)==7>selected="selected"</#if>>
												甘肃省
											</option>
											<option value="8"
													<#if ((planBuildData.projectProvince)!-1)==8>selected="selected"</#if>>
												广东省
											</option>
											<option value="9"
													<#if ((planBuildData.projectProvince)!-1)==9>selected="selected"</#if>>
												贵州省
											</option>
											<option value="10"
													<#if ((planBuildData.projectProvince)!-1)==10>selected="selected"</#if>>
												海南省
											</option>
											<option value="11"
													<#if ((planBuildData.projectProvince)!-1)==11>selected="selected"</#if>>
												河北省
											</option>
											<option value="12"
													<#if ((planBuildData.projectProvince)!-1)==12>selected="selected"</#if>>
												河南省
											</option>
											<option value="13"
													<#if ((planBuildData.projectProvince)!-1)==13>selected="selected"</#if>>
												湖北省
											</option>
											<option value="14"
													<#if ((planBuildData.projectProvince)!-1)==14>selected="selected"</#if>>
												湖南省
											</option>
											<option value="15"
													<#if ((planBuildData.projectProvince)!-1)==15>selected="selected"</#if>>
												吉林省
											</option>
											<option value="16"
													<#if ((planBuildData.projectProvince)!-1)==16>selected="selected"</#if>>
												江苏省
											</option>
											<option value="17"
													<#if ((planBuildData.projectProvince)!-1)==17>selected="selected"</#if>>
												江西省
											</option>
											<option value="18"
													<#if ((planBuildData.projectProvince)!-1)==18>selected="selected"</#if>>
												辽宁省
											</option>
											<option value="19"
													<#if ((planBuildData.projectProvince)!-1)==19>selected="selected"</#if>>
												青海省
											</option>
											<option value="20"
													<#if ((planBuildData.projectProvince)!-1)==20>selected="selected"</#if>>
												山东省
											</option>
											<option value="21"
													<#if ((planBuildData.projectProvince)!-1)==21>selected="selected"</#if>>
												山西省
											</option>
											<option value="22"
													<#if ((planBuildData.projectProvince)!-1)==22>selected="selected"</#if>>
												陕西省
											</option>
											<option value="23"
													<#if ((planBuildData.projectProvince)!-1)==23>selected="selected"</#if>>
												四川省
											</option>
											<option value="24"
													<#if ((planBuildData.projectProvince)!-1)==24>selected="selected"</#if>>
												云南省
											</option>
											<option value="25"
													<#if ((planBuildData.projectProvince)!-1)==25>selected="selected"</#if>>
												浙江省
											</option>
											<option value="26"
													<#if ((planBuildData.projectProvince)!-1)==26>selected="selected"</#if>>
												台湾省
											</option>
											<option value="27"
													<#if ((planBuildData.projectProvince)!-1)==27>selected="selected"</#if>>
												黑龙江省
											</option>
											<option value="28"
													<#if ((planBuildData.projectProvince)!-1)==28>selected="selected"</#if>>
												西藏自治区
											</option>
											<option value="29"
													<#if ((planBuildData.projectProvince)!-1)==29>selected="selected"</#if>>
												内蒙古自治区
											</option>
											<option value="30"
													<#if ((planBuildData.projectProvince)!-1)==30>selected="selected"</#if>>
												宁夏回族自治区
											</option>
											<option value="31"
													<#if ((planBuildData.projectProvince)!-1)==31>selected="selected"</#if>>
												广西壮族自治区
											</option>
											<option value="32"
													<#if ((planBuildData.projectProvince)!-1)==32>selected="selected"</#if>>
												新疆维吾尔自治区
											</option>
											<option value="33"
													<#if ((planBuildData.projectProvince)!-1)==33>selected="selected"</#if>>
												香港特别行政区
											</option>
											<option value="34"
													<#if ((planBuildData.projectProvince)!-1)==34>selected="selected"</#if>>
												澳门特别行政区
											</option>
										</select>
									</div>
									<div class="form-group"><label>采购人</label>
										<textarea class="form-control" placeholder="" name="projcetOwner"
												  rows="10">${(planBuildData.projcetOwner)!''}</textarea>
									</div>
									<div class="form-group"><label>母公司</label>
										<input type="text" class="form-control" placeholder="" name="parentCompany"
											   value="${(planBuildData.parentCompany)!''}">
									</div>
									<div class="form-group"><label>项目阶段</label>
										<input type="text" class="form-control" placeholder="" name="planBuildStatus"
											   value="${(planBuildData.planBuildStatus)!''}">
									</div>
									<div class="form-group"><label>设备购置情况</label>
										<input type="text" class="form-control" placeholder=""
											   name="purchaseSituation" value="${(planBuildData.purchaseSituation)!''}">
									</div>
									<div class="form-group"><label>设计师</label>
										<textarea class="form-control" placeholder="" name="designer"
												  rows="10">${(planBuildData.designer)!''}</textarea>
									</div>
									<div class="form-group"><label>状态更新</label>
										<input type="text" class="form-control" placeholder="" name="statusUpdate"
											   value="${(planBuildData.statusUpdate)!''}">
									</div>

									<div class="form-group"><label>发布时间</label>
										<div class="input-group date">
											<input name="publishTime" class="form-control" type="text"
												   value="${(planBuildData.publishTime?string('yyyy-MM-dd'))!''}"
												   readonly>
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</div>
									<div class="form-group"><label>备注</label>
										<input type="text" class="form-control" placeholder="" name="remarks"
											   value="${(planBuildData.remarks)!''}">
									</div>
									<div class="form-group"><label>数据的原始链接</label>
										<input type="text" class="form-control" placeholder="" name="url"
											   value="${(planBuildData.url)!''}">
									</div>
									<div class="form-group"><label>网页源码</label>
										<textarea class="form-control" placeholder="" name="htmlSource"
												  rows="10">${(planBuildData.htmlSource)!''}</textarea>
									</div>

								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-title">
									<h5>备案数据</h5>
								</div>
								<div class="ibox-content">
                                <#if (recordDatas??)> <#if recordDatas?size gt 0> <#list recordDatas as rd>
									<div class="i-checks"><label>
										<input type="checkbox" name="recordDataIds" value="${rd.id}"
                                               <#if ((rd.checked)!-1)==1>checked=""</#if>> <i></i>
                                    ${rd.projectName}
									</label></div>
                                </#list></#if></#if>
								</div>
							</div>

							<div class="ibox ">
								<div class="ibox-title">
									<h5>招中标数据</h5>
								</div>
								<div class="ibox-content">

                                <#if (biddingDatas??)><#if biddingDatas?size gt 0><#list biddingDatas as bd>
									<div class="i-checks"><label>
										<input type="checkbox" name="biddingDataIds" value="${bd.id}"
                                               <#if ((rd.checked)!-1)==1>checked=""</#if>> <i></i>
                                    ${pbd.projectName}
									</label></div>
                                </#list></#if></#if>
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

		$('#btn_back').click(function () {
			window.location.href = "/planBuildData/list";
		});

		FormValidation.init();

	});
</script>
</body>
</html>
