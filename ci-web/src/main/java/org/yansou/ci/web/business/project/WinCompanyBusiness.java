package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.model.project.WinCompany;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.web.business.GeneralBusiness;

import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-05-14 0:41
 */
public interface WinCompanyBusiness extends GeneralBusiness<WinCompany, Long> {

	IdResponse save(Long biddingDataId, WinCompany entity);

	IdResponse update(Long biddingDataId, WinCompany entity);

	/**
	 * 企业中标总容量排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param limit
	 *
	 * @return
	 */
	ReportRo statisticsByWinCapacity(Date startTime, Date endTime, int limit);

	/**
	 * 企业中标数量排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param limit
	 *
	 * @return
	 */
	ReportRo statisticsByWinCount(Date startTime, Date endTime, int limit);

}
