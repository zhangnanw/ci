package org.yansou.ci.storage.service.project.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.MergeDataRepository;
import org.yansou.ci.storage.service.project.MergeDataService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("mergeDataService")
@Transactional
public class MergeDataServiceImpl extends GeneralServiceImpl<MergeData, Long> implements MergeDataService {

	private MergeDataRepository mergeDataRepository;

	@Autowired
	@Qualifier("mergeDataRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<MergeData, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.mergeDataRepository = (MergeDataRepository) generalRepository;
	}

	@Override
	public List<MergeData> findByProjectIdentifie(String projectIdentifie) throws DaoException {
		return mergeDataRepository.findByProjectIdentifie(projectIdentifie);
	}

	@Override
	public int updateChecked(Long[] ids, Integer checked) throws DaoException {
		if (ArrayUtils.isEmpty(ids) || checked == null) {
			return -1;
		}

		return mergeDataRepository.updateChecked(ids, checked);
	}
}
