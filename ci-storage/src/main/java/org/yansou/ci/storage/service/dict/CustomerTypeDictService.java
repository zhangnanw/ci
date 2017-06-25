package org.yansou.ci.storage.service.dict;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.dict.CustomerTypeDict;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-06-24 12:28
 */
public interface CustomerTypeDictService extends GeneralService<CustomerTypeDict, Long> {

	CustomerTypeDict findByCode(Integer code) throws DaoException;

	/**
	 * 查找所有的数据，以Map的方式返回，key为code，value为name
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	Map<Integer, String> findAllByMap() throws DaoException;

}
