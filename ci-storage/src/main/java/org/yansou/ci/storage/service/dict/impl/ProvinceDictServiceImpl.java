package org.yansou.ci.storage.service.dict.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.dict.ProvinceDict;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.dict.ProvinceDictRepository;
import org.yansou.ci.storage.service.dict.ProvinceDictService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-06-24 12:29
 */
@Service("provinceDictService")
@Transactional
public class ProvinceDictServiceImpl extends GeneralServiceImpl<ProvinceDict, Long> implements ProvinceDictService {

	private ProvinceDictRepository provinceDictRepository;

	@Autowired
	@Qualifier("provinceDictRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<ProvinceDict, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.provinceDictRepository = (ProvinceDictRepository) generalRepository;
	}

	@Override
	public ProvinceDict findByCode(Integer code) throws DaoException {
		return provinceDictRepository.findProvinceDictByCode(code);
	}

	@Override
	public Map<Integer, String> findAllByMap() throws DaoException {
		List<ProvinceDict> provinceDictList = provinceDictRepository.findAll();
		if (CollectionUtils.isEmpty(provinceDictList)) {
			return null;
		}

		Map<Integer, String> codeMap = new HashMap<>();

		provinceDictList.forEach(provinceDict -> codeMap.put(provinceDict.getCode(), provinceDict.getName()));

		return codeMap;
	}
}
