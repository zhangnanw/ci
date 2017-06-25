package org.yansou.ci.storage.repository.dict;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.dict.ProductTypeDict;
import org.yansou.ci.storage.common.repository.GeneralRepository;

/**
 * @author liutiejun
 * @create 2017-06-24 12:27
 */
@Repository("productTypeDictRepository")
public interface ProductTypeDictRepository extends GeneralRepository<ProductTypeDict, Long> {

	ProductTypeDict findProductTypeDictByCode(Integer code);

}
