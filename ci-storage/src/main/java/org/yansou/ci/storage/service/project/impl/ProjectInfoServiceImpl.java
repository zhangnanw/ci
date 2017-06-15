package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.ProjectInfoRepository;
import org.yansou.ci.storage.service.project.ProjectInfoService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("projectInfoService")
@Transactional
public class ProjectInfoServiceImpl extends GeneralServiceImpl<ProjectInfo, Long> implements ProjectInfoService {

	private ProjectInfoRepository projectInfoRepository;

	@Autowired
	@Qualifier("projectInfoRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<ProjectInfo, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.projectInfoRepository = (ProjectInfoRepository) generalRepository;
	}

	@Override
	public List<ProjectInfo> findByProjectIdentifie(String projectIdentifie) throws DaoException {
		return projectInfoRepository.findByProjectIdentifie(projectIdentifie);
	}
}
