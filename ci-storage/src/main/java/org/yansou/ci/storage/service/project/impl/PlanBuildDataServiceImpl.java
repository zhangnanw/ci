package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.AbstractModel;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.PlanBuildDataRepository;
import org.yansou.ci.storage.service.project.PlanBuildDataService;
import org.yansou.ci.storage.service.project.SnapshotInfoService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("planBuildDataService")
@Transactional
public class PlanBuildDataServiceImpl extends GeneralServiceImpl<PlanBuildData, Long> implements PlanBuildDataService {

	private PlanBuildDataRepository planBuildDataRepository;
	@Autowired
	private SnapshotInfoService snapshotInfoService;

	@Autowired
	@Qualifier("planBuildDataRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<PlanBuildData, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.planBuildDataRepository = (PlanBuildDataRepository) generalRepository;
	}

	@Override
	public void updateStatusUpdate(String statusUpdate, Long id) {
		planBuildDataRepository.updateStatusUpdate(statusUpdate, id);
	}

	@Override
	public PlanBuildData save(PlanBuildData entity) throws DaoException {
		entity.setStatus(AbstractModel.Status.NORMAL.getValue());

		return planBuildDataRepository.save(entity);
	}

	@Override
	public void saveDataAndSnapshotInfo(PlanBuildData data, SnapshotInfo snap) throws DaoException {
		snapshotInfoService.save(snap);
		this.save(data);
	}

	@Override
	public List<PlanBuildData> findByProjectIdentifie(String projectIdentifie) throws DaoException {
		return planBuildDataRepository.findByProjectIdentifie(projectIdentifie);
	}

	@Override
	public PlanBuildData findByProjectNumber(String projectNumber) throws DaoException {
		return planBuildDataRepository.findByProjectNumber(projectNumber);
	}
}
