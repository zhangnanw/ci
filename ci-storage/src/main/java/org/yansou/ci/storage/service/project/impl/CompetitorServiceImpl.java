package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.core.db.model.project.Competitor;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.CompetitorRepository;
import org.yansou.ci.storage.service.project.CompetitorService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("competitorService")
@Transactional
public class CompetitorServiceImpl extends GeneralServiceImpl<Competitor, Long> implements CompetitorService {

	private CompetitorRepository competitorRepository;

	@Autowired
	@Qualifier("competitorRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<Competitor, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.competitorRepository = (CompetitorRepository) generalRepository;
	}

}
