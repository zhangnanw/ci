package org.yansou.ci.storage.service.dict.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.dict.ProductTypeDict;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.dict.ProductTypeDictRepository;
import org.yansou.ci.storage.service.dict.ProductTypeDictService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-06-24 12:29
 */
@Service("productTypeDictService")
@Transactional
public class ProductTypeDictServiceImpl extends GeneralServiceImpl<ProductTypeDict, Long> implements ProductTypeDictService {

	private ProductTypeDictRepository productTypeDictRepository;

	@Autowired
	@Qualifier("productTypeDictRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<ProductTypeDict, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.productTypeDictRepository = (ProductTypeDictRepository) generalRepository;
	}

	@Override
	public ProductTypeDict findByCode(Integer code) throws DaoException {
		return productTypeDictRepository.findProductTypeDictByCode(code);
	}

	@Override
	public Map<Integer, String> findAllByMap() throws DaoException {
		List<ProductTypeDict> productTypeDictList = productTypeDictRepository.findAll();
		if (CollectionUtils.isEmpty(productTypeDictList)) {
			return null;
		}

		Map<Integer, String> codeMap = new HashMap<>();

		productTypeDictList.forEach(productTypeDict -> codeMap.put(productTypeDict.getCode(), productTypeDict.getName()));

		return codeMap;
	}
}
