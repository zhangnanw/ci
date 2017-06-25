package org.yansou.ci.storage.service.dict.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.dict.DeploymentTypeDict;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.dict.DeploymentTypeDictRepository;
import org.yansou.ci.storage.service.dict.DeploymentTypeDictService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-06-24 12:29
 */
@Service("deploymentTypeDictService")
@Transactional
public class DeploymentTypeDictServiceImpl extends GeneralServiceImpl<DeploymentTypeDict, Long> implements DeploymentTypeDictService {

	private DeploymentTypeDictRepository deploymentTypeDictRepository;

	@Autowired
	@Qualifier("deploymentTypeDictRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<DeploymentTypeDict, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.deploymentTypeDictRepository = (DeploymentTypeDictRepository) generalRepository;
	}

	@Override
	public DeploymentTypeDict findByCode(Integer code) throws DaoException {
		return deploymentTypeDictRepository.findDeploymentTypeDictByCode(code);
	}

	@Override
	public Map<Integer, String> findAllByMap() throws DaoException {
		List<DeploymentTypeDict> deploymentTypeDictList = deploymentTypeDictRepository.findAll();
		if (CollectionUtils.isEmpty(deploymentTypeDictList)) {
			return null;
		}

		Map<Integer, String> codeMap = new HashMap<>();

		deploymentTypeDictList.forEach(deploymentTypeDict -> codeMap.put(deploymentTypeDict.getCode(), deploymentTypeDict.getName()));

		return codeMap;
	}
}
