package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.RecordDataRepository;
import org.yansou.ci.storage.service.project.RecordDataService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("recordDataService")
@Transactional
public class RecordDataServiceImpl extends GeneralServiceImpl<RecordData, Long> implements RecordDataService {

	private RecordDataRepository recordDataRepository;

	@Autowired
	@Qualifier("recordDataRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<RecordData, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.recordDataRepository = (RecordDataRepository) generalRepository;
	}

}
