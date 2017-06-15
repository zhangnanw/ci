package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:21
 */
public interface BiddingDataService extends GeneralService<BiddingData, Long> {

	void saveDataAndSnapshotInfo(BiddingData data, SnapshotInfo snap) throws DaoException;

	List<BiddingData> findByProjectIdentifie(String projectIdentifie) throws DaoException;
}
