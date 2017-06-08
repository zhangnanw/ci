package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.model.project.WinCompany;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.web.business.GeneralBusiness;

/**
 * @author liutiejun
 * @create 2017-05-14 0:41
 */
public interface WinCompanyBusiness extends GeneralBusiness<WinCompany, Long> {

	IdResponse save(Long biddingDataId, WinCompany entity);

	IdResponse update(Long biddingDataId, WinCompany entity);

}
