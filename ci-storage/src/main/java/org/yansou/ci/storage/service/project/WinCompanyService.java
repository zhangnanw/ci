package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.WinCompany;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-05-21 12:28
 */
public interface WinCompanyService extends GeneralService<WinCompany, Long> {

	/**
	 * 企业中标总容量排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param limit
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByWinCapacity(Date startTime, Date endTime, int limit) throws DaoException;

	/**
	 * 企业中标数量排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param limit
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByWinCount(Date startTime, Date endTime, int limit) throws DaoException;

}
