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
	 * 中标产品分类
	 *
	 * @param startTime
	 * @param endTime
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByProductType(Date startTime, Date endTime) throws DaoException;

	/**
	 * 中标区域分布
	 *
	 * @param startTime
	 * @param endTime
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByProjectProvince(Date startTime, Date endTime) throws DaoException;

	/**
	 * 中标总量分析
	 *
	 * @param startTime
	 * @param endTime
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	ReportRo statisticsByWinTotalAmount(Date startTime, Date endTime) throws DaoException;

}
