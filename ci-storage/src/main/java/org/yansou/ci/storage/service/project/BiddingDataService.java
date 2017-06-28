package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.Date;
import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:21
 */
public interface BiddingDataService extends GeneralService<BiddingData, Long> {

	void saveDataAndSnapshotInfo(BiddingData data, SnapshotInfo snap) throws DaoException;

	List<BiddingData> findByProjectIdentifie(String projectIdentifie) throws DaoException;

	int updateChecked(Long[] ids, Integer checked) throws DaoException;

	/**
	 * 招标总量分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByProjectScaleAndPublishTime(Date startTime, Date endTime, Integer dataType, Integer reportType) throws DaoException;

	/**
	 * 招标总量（MW）公司排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByProjectScaleAndParentCompany(Date startTime, Date endTime, Integer dataType, Integer
			reportType, Integer limit) throws DaoException;

	/**
	 * 招标总量（MW）区域排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByProjectScaleAndProjectProvince(Date startTime, Date endTime, Integer dataType, Integer
			reportType, Integer limit) throws DaoException;

	/**
	 * 招标数量区域排名
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByCountAndProjectProvince(Date startTime, Date endTime, Integer dataType, Integer reportType) throws DaoException;

	/**
	 * 招标产品部署类型分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByCountAndDeploymentType(Date startTime, Date endTime, Integer dataType, Integer reportType) throws DaoException;

	/**
	 * 招标产品类型分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByCountAndProductType(Date startTime, Date endTime, Integer dataType, Integer reportType) throws DaoException;

	/**
	 * 招标客户类型分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByCountAndCustomerType(Date startTime, Date endTime, Integer dataType, Integer reportType) throws DaoException;

	/**
	 * 中标总量分析
	 *
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param reportType
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByWinTotalAmount(Date startTime, Date endTime, Integer dataType, Integer reportType) throws DaoException;

}
