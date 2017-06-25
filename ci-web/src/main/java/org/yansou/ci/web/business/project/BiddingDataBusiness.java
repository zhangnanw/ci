package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.constant.Checked;
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

	CountResponse updateChecked(Long[] ids, Checked checked);

	/**
	 * 中标产品分类
	 *
	 * @param startTime
	 * @param endTime
	 *
	 * @return
	 */
	ReportRo statisticsByProductType(Date startTime, Date endTime);

	/**
	 * 中标区域分布
	 *
	 * @param startTime
	 * @param endTime
	 *
	 * @return
	 */
	ReportRo statisticsByProjectProvince(Date startTime, Date endTime);

}
