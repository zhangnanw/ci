package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.web.business.GeneralBusiness;

/**
 * @author liutiejun
 * @create 2017-05-14 0:41
 */
public interface BiddingDataBusiness extends GeneralBusiness<BiddingData, Long> {

	BiddingData[] findByProjectIdentifie(String projectIdentifie);

}
