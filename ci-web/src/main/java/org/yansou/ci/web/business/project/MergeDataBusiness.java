package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.web.business.GeneralBusiness;

/**
 * @author liutiejun
 * @create 2017-05-13 22:52
 */
public interface MergeDataBusiness extends GeneralBusiness<MergeData, Long> {

	MergeData[] findByProjectIdentifie(String projectIdentifie);

	CountResponse updateChecked(String projectIdentifie, Long[] ids);

}
