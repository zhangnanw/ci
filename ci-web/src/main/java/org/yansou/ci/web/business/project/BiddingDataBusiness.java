package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.constant.DataType;
import org.yansou.ci.core.db.constant.ReportType;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.web.business.GeneralBusiness;

import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-05-14 0:41
 */
public interface BiddingDataBusiness extends GeneralBusiness<BiddingData, Long> {

	BiddingData[] findByProjectIdentifie(String projectIdentifie);

	CountResponse updateChecked(String projectIdentifie, Long[] ids);

	/**
	 * 招标总量分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 */
	ReportRo statisticsByProjectScaleAndPublishTime(Date startTime, Date endTime, DataType dataType, ReportType reportType);

	/**
	 * 招标总量（MW）公司排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 */
	ReportRo statisticsByProjectScaleAndParentCompany(Date startTime, Date endTime, DataType dataType, ReportType reportType, Integer limit);

	/**
	 * 招标总量（MW）区域排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 */
	ReportRo statisticsByProjectScaleAndProjectProvince(Date startTime, Date endTime, DataType dataType, ReportType reportType, Integer limit);

	/**
	 * 招标数量区域排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 */
	ReportRo statisticsByCountAndProjectProvince(Date startTime, Date endTime, DataType dataType, ReportType reportType);

	/**
	 * 招标产品部署类型分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 */
	ReportRo statisticsByCountAndDeploymentType(Date startTime, Date endTime, DataType dataType, ReportType reportType);

	/**
	 * 招标产品类型分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 */
	ReportRo statisticsByCountAndProductType(Date startTime, Date endTime, DataType dataType, ReportType reportType);

	/**
	 * 招标客户类型分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 */
	ReportRo statisticsByCountAndCustomerType(Date startTime, Date endTime, DataType dataType, ReportType reportType);

}
