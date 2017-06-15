package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-21 12:27
 */
public interface PlanBuildDataService extends GeneralService<PlanBuildData, Long> {

	List<PlanBuildData> findByProjectIdentifie(String projectIdentifie) throws DaoException;

	void updateStatusUpdate(String statusUpdate, Long id) throws DaoException;

	void saveDataAndSnapshotInfo(PlanBuildData data, SnapshotInfo snap) throws DaoException;
}
