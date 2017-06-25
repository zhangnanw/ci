package org.yansou.ci.storage.service.dict.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.dict.CustomerTypeDict;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.dict.CustomerTypeDictRepository;
import org.yansou.ci.storage.service.dict.CustomerTypeDictService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-06-24 12:29
 */
@Service("customerTypeDictService")
@Transactional
public class CustomerTypeDictServiceImpl extends GeneralServiceImpl<CustomerTypeDict, Long> implements CustomerTypeDictService {

	private CustomerTypeDictRepository customerTypeDictRepository;

	@Autowired
	@Qualifier("customerTypeDictRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<CustomerTypeDict, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.customerTypeDictRepository = (CustomerTypeDictRepository) generalRepository;
	}

	@Override
	public CustomerTypeDict findByCode(Integer code) throws DaoException {
		return customerTypeDictRepository.findCustomerTypeDictByCode(code);
	}

	@Override
	public Map<Integer, String> findAllByMap() throws DaoException {
		List<CustomerTypeDict> customerTypeDictList = customerTypeDictRepository.findAll();
		if (CollectionUtils.isEmpty(customerTypeDictList)) {
			return null;
		}

		Map<Integer, String> codeMap = new HashMap<>();

		customerTypeDictList.forEach(customerTypeDict -> codeMap.put(customerTypeDict.getCode(), customerTypeDict.getName()));

		return codeMap;
	}
}
